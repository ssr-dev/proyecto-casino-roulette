package com.casino.service;

import com.casino.model.Bet;
import org.springframework.stereotype.Service;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

@Service
public class BetHistoryService {
    private final Deque<Bet> betHistory = new ArrayDeque<>();
    private static final int MAX_HISTORY = 20;

    public void addBet(Bet bet) {
        if (betHistory.size() >= MAX_HISTORY) {
            betHistory.removeFirst();
        }
        betHistory.addLast(bet);
    }

    public Deque<Bet> getBetHistory() {
        return new ArrayDeque<>(betHistory);
    }

    public List<Bet> getLastBets(int count) {
        return betHistory.stream()
                .skip(Math.max(0, betHistory.size() - count))
                .limit(count)
                .toList();
    }

    public void clearHistory() {
        betHistory.clear();
    }
}
