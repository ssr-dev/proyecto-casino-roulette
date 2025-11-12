package com.casino.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.casino.dtos.BetResults;
import com.casino.dtos.SpinRequest;
import com.casino.dtos.SpinResult;
import com.casino.model.Bet;
import com.casino.model.Roulette;
import com.casino.model.User;

@Service
public class RouletteService {
    
    private static final int HISTORY_LIMIT = 100;
    private static final int WHEEL_SIZE = 37;
    
    private final Deque<Integer> history = new ArrayDeque<>(HISTORY_LIMIT);
    private final Object lock = new Object();
    
    private Roulette r = new Roulette();
    private final AtomicInteger lastNumber = new AtomicInteger(-1);
    int winningNumber = 0;
    
    @Autowired
    private UserService userService;
    
    public int spinOnce() {
        winningNumber = r.spin();
        System.out.println("Spin automático generado: " + winningNumber);
        appendToHistory(winningNumber);
        lastNumber.set(winningNumber);
        return winningNumber;
    }

    public SpinResult autoSpin() {
        return new SpinResult(spinOnce(), true);
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

    @Autowired
    private BetService betService;

    public BetResults placeBets(Long userId, List<SpinRequest> bets) {
        double totalWinnig = 0;
        User user = userService.findById(userId);
        if (user == null)
            throw new IllegalArgumentException("Usuario no encontrado");

        double totalBetAmount = bets.stream()
                .mapToDouble(SpinRequest::getAmount)
                .sum();

        if (user.getBalance() < totalBetAmount) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        user.setBalance(user.getBalance() - totalBetAmount);

        for (SpinRequest req : bets) {
            Bet bet = betService.buildBet(req, user);
            totalWinnig += betService.resolvePayout(bet, winningNumber);
        }

        return new BetResults(totalBetAmount, totalWinnig); 
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