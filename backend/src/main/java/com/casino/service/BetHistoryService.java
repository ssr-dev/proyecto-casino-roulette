package com.casino.service;

import com.casino.model.Bet;
import com.casino.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

@Service
public class BetHistoryService {
    
    @Autowired
    private BetRepository betRepository;
    
    private final Deque<Long> betHistoryIds = new ArrayDeque<>();
    private static final int MAX_HISTORY = 20;

    public void addBet(Bet bet) {
        if (betHistoryIds.size() >= MAX_HISTORY) {
            betHistoryIds.removeFirst();
        }
        betHistoryIds.addLast(bet.getId());
    }

    public List<Bet> getLastBets(int count) {
        return betHistoryIds.stream()
                .skip(Math.max(0, betHistoryIds.size() - count))
                .limit(count)
                .map(id -> betRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    public void clearHistory() {
        betHistoryIds.clear();
    }
}