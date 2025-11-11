package com.casino.service;

import org.springframework.stereotype.Service;

import com.casino.dtos.SpinRequest;
import com.casino.model.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BetService {

    public Bet buildBet(SpinRequest req, User user) {
        double amount = req.getAmount();
        String type = req.getType();
        String value = req.getValue();

        switch (type.toLowerCase()) {

            case "number":
                return user.placeNumberBet(Integer.parseInt(value), amount);

            case "color":
                return user.placeColorBet(value, amount);

            case "parity":
                return user.placeParImparBet(
                    value.equalsIgnoreCase("even") ? "PAR" : "IMPAR",
                    amount
                );

            case "range":
                return user.placeAltoBajoBet(
                    value.equals("1to18") ? "BAJO" : "ALTO",
                    amount
                );

            case "tercio":
                int tercio =
                    value.equals("1st12") ? 1 :
                    value.equals("2nd12") ? 2 : 3;
                return user.placeTercioBet(tercio, amount);

            case "column":
                int col =
                    value.equals("col1") ? 1 :
                    value.equals("col2") ? 2 : 3;
                return user.placeColumnaBet(col, amount);
        }

        throw new IllegalArgumentException("Tipo de apuesta no soportado: " + type);
    }

    public double resolvePayout(Bet bet, int winningNumber) {
        return bet.calculatePayout(winningNumber);
    }
}
