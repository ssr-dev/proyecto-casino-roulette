package com.casino.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.casino.Model.User;
import com.casino.Model.Bet;
import com.casino.dtos.SpinRequest;
import com.casino.dtos.SpinResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouletteService {

    private final UserService userService;
    private final BetService betService;

    private final List<Integer> history = new ArrayList<>();
    private final AtomicInteger lastNumber = new AtomicInteger(-1);

    /** 
     * 🔄 Gira automáticamente cada 60 segundos (1 minuto)
     * fixedRate = 60_000 -> cada 60s 
     * initialDelay = 0 -> empieza inmediatamente al iniciar el servidor
     */
    @Scheduled(fixedRate = 60_000L, initialDelay = 0L)
    public void autoSpin() {
        int number = spin();
        lastNumber.set(number);
        history.add(number);
        System.out.println("🎰 Spin automático generado: " + number);
    }

    /** Giro manual desde un endpoint */
    public SpinResult spinWithBet(SpinRequest request) {
        User user = userService.getUser(request.getUserId());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Bet bet = betService.buildBet(request, user);

        int winningNumber = (request.getFrontendNumber() != null)
                ? request.getFrontendNumber()
                : spin();

        String color = getColor(winningNumber);
        double payout = betService.resolvePayout(bet, winningNumber);

        if (payout > 0) {
            user.updateBalance(payout);
            userService.updateUser(user);
        }

        lastNumber.set(winningNumber);
        history.add(winningNumber);

        return new SpinResult(true, winningNumber);
    }

    /** Genera un número aleatorio entre 0 y 36 */
    public int spin() {
        return (int) (Math.random() * 37);
    }

    /** Determina el color del número ganador */
    private String getColor(int number) {
        if (number == 0) return "green";
        int[] reds = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
        for (int r : reds) if (r == number) return "red";
        return "black";
    }

    public int getLastNumber() {
        return lastNumber.get();
    }

    public List<Integer> getHistory() {
        return new ArrayList<>(history);
    }

    public Map<String, Object> getLastSpins() {
        int size = history.size();
        List<Integer> last6 = history.stream()
                .skip(Math.max(0, size - 6))
                .collect(Collectors.toList());
        return Map.of(
                "lastNumber", size > 0 ? history.get(size - 1) : null,
                "last6", last6
        );
    }

    public List<Map.Entry<Integer, Long>> getHotNumbers(int top) {
        return history.stream()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .limit(top)
                .collect(Collectors.toList());
    }

    public List<Map.Entry<Integer, Long>> getColdNumbers(int top) {
        return history.stream()
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(top)
                .collect(Collectors.toList());
    }
}
