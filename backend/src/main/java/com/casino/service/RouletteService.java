package com.casino.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.casino.dtos.SpinResult;
import com.casino.model.Bet;
import com.casino.model.BetType;
import com.casino.model.User;

@Service
public class RouletteService {

    private static final int HISTORY_LIMIT = 100;
    private static final int WHEEL_SIZE = 37;

    private static final Set<Integer> ROJOS = Set.of(1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36);
    private static final Set<Integer> NEGROS = Set.of(2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35);
    
    private final Deque<Integer> history = new ArrayDeque<>(HISTORY_LIMIT);
    private final Object lock = new Object();

    private final AtomicInteger lastNumber = new AtomicInteger(-1);

    @Autowired
    private UserService userService; 

    private double calculateWinnings(Bet bet, int winningNumber) {
        if (bet.getType() == BetType.COLOR) {
            String colorApostado = bet.getColor();
            if ("ROJO".equalsIgnoreCase(colorApostado) && ROJOS.contains(winningNumber)) {
                return bet.getAmount() * 2.0;
            }
            if ("NEGRO".equalsIgnoreCase(colorApostado) && NEGROS.contains(winningNumber)) {
                return bet.getAmount() * 2.0;
            }
        }
        return 0.0;
    }
   
    public SpinResult spinAndProcessBet(Long userId, String typeStr, String valueStr, double amount) {
        
        User user = userService.findById(userId);
        
        if (user == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + userId);
        }

        BetType type = BetType.valueOf(typeStr.toUpperCase());
        Bet bet = null;
        
        switch (type) {
            case NUMBER:
                bet = user.placeNumberBet(Integer.parseInt(valueStr), amount);
                break;
            case COLOR:
                bet = user.placeColorBet(valueStr, amount);
                break;
            case DOZEN:
                bet = user.placeTercioBet(Integer.parseInt(valueStr), amount);
                break;
            case COLUMN:
                bet = user.placeColumnaBet(Integer.parseInt(valueStr), amount);
                break;
            case PARITY:
                bet = user.placeParImparBet(valueStr, amount);
                break;
            case RANGE:
                bet = user.placeAltoBajoBet(valueStr, amount);
                break;
            default:
                throw new IllegalArgumentException("Tipo de apuesta inválido: " + typeStr);
        }

        Random random = new Random();
        int winningNumber = random.nextInt(WHEEL_SIZE); 
        appendToHistory(winningNumber);
        lastNumber.set(winningNumber);

        double totalWinnings = calculateWinnings(bet, winningNumber); 
        boolean isWinner = totalWinnings > 0;

        user.updateBalance(totalWinnings); 
        userService.updateUser(user); 

        return new SpinResult(
            user.getId(), 
            user.getBalance(),
            winningNumber,
            isWinner
        );
    }

    public int spinOnce() {
        Random random = new Random();
        int n = random.nextInt(WHEEL_SIZE);
        appendToHistory(n);
        lastNumber.set(n);
        return n;
    }

    @Scheduled(fixedRate = 90_000L, initialDelay = 0L)
    public SpinResult autoSpin() {
        return new SpinResult(null, 0.0, spinOnce(), false); 
    }

    public int getLastNumber() {
        return lastNumber.get();
    }

    public List<Integer> getLastK(int k) {
        synchronized (lock) {
            return history.stream()
                    .skip(Math.max(0, history.size() - k))
                    .collect(Collectors.toList());
        }
    }

    public List<Integer> getHistory() {
        synchronized (lock) {
            return new ArrayList<>(history);
        }
    }

    public List<Map.Entry<Integer, Integer>> getHot(int topN) {
        Map<Integer, Integer> freq = getFrequencies();
        return freq.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue()); 
                    if (cmp == 0)
                        return Integer.compare(a.getKey(), b.getKey()); 
                    return cmp;
                })
                .limit(topN)
                .collect(Collectors.toList());
    }

    public List<Map.Entry<Integer, Integer>> getCold(int topN) {
        Map<Integer, Integer> freq = getFrequencies();
        return freq.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(a.getValue(), b.getValue()); 
                    if (cmp == 0)
                        return Integer.compare(a.getKey(), b.getKey());
                    return cmp;
                })
                .limit(topN)
                .collect(Collectors.toList());
    }

    public Map<Integer, Integer> getFrequencies() {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < WHEEL_SIZE; i++)
            map.put(i, 0);
        synchronized (lock) {
            for (int n : history) {
                map.computeIfPresent(n, (k, v) -> v + 1);
            }
        }
        return map;
    }

    private void appendToHistory(int n) {
        synchronized (lock) {
            if (history.size() == HISTORY_LIMIT) {
                history.removeFirst();
            }
            history.addLast(n);
        }
    }
}