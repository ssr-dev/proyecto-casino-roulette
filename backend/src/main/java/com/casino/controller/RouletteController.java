package com.casino.controller;

import org.springframework.web.bind.annotation.*;

import com.casino.Model.Bet;
import com.casino.Model.User;
import com.casino.dtos.SpinRequest;
import com.casino.dtos.SpinResult;
import com.casino.dtos.StatsDto;
import com.casino.service.BetService;
import com.casino.service.RouletteService;
import com.casino.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roulette")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RouletteController {

    private final RouletteService rouletteService;
    private final UserService userService;
    private final BetService betService;

    @PostMapping("/spin")
    public SpinResult spinWithBet(@RequestBody SpinRequest request) {
        User user = userService.getUser(request.getUserId());
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Bet bet = betService.buildBet(request, user);

        // ✅ USAR NÚMERO DEL FRONTEND SI ESTÁ DISPONIBLE
        int winningNumber;
        if (request.getFrontendNumber() != null) {
            winningNumber = request.getFrontendNumber();
            System.out.println("🎯 Usando número del frontend: " + winningNumber);
        } else {
            winningNumber = rouletteService.spinOnce();
            System.out.println("🎰 Generando número en backend: " + winningNumber);
        }

        // Cálculo de color corregido
        String winningColor;
        if (winningNumber == 0) {
            winningColor = "green";
        } else {
            int[] rojos = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
            boolean esRojo = false;
            for (int rojo : rojos) {
                if (winningNumber == rojo) {
                    esRojo = true;
                    break;
                }
            }
            winningColor = esRojo ? "red" : "black";
        }

        double payout = betService.resolvePayout(bet, winningNumber);

        if (payout > 0) {
            user.updateBalance(payout);
        }
        userService.updateUser(user);

        return new SpinResult(
            winningNumber,
            winningColor,
            payout,
            user.getBalance()
        );
    }

    @GetMapping("/last")
    public Map<String, Object> last() {
        return Map.of(
            "lastNumber", rouletteService.getLastNumber(),
            "last6", rouletteService.getLastK(6)
        );
    }

    @GetMapping("/history")
    public List<Integer> history() {
        return rouletteService.getHistory();
    }

    @GetMapping("/stats/hot")
    public List<Integer> hot(@RequestParam(defaultValue = "5") int top) {
        return rouletteService.getHot(top).stream()
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @GetMapping("/stats/cold")
    public List<Integer> cold(@RequestParam(defaultValue = "5") int top) {
        return rouletteService.getCold(top).stream()
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    @GetMapping("/stats")
    public StatsDto stats(@RequestParam(defaultValue = "5") int top) {
        var freqs = rouletteService.getFrequencies();
        var last6 = rouletteService.getLastK(6);
        var hot = rouletteService.getHot(top).stream().map(Map.Entry::getKey).collect(Collectors.toList());
        var cold = rouletteService.getCold(top).stream().map(Map.Entry::getKey).collect(Collectors.toList());
        int totalSpins = rouletteService.getHistory().size();
        return new StatsDto(totalSpins, freqs, last6, hot, cold);
    }
}