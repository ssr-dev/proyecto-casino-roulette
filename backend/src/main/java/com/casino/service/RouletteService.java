package com.casino.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.casino.Model.Rulet;

@Service
public class RouletteService {

    private static final int HISTORY_LIMIT = 100;
    private static final int WHEEL_SIZE = 37;

    private final Deque<Integer> history = new ArrayDeque<>(HISTORY_LIMIT);
    private final Rulet rulet = new Rulet(); // usa tu clase existente
    private final Object lock = new Object();

    private final AtomicInteger lastNumber = new AtomicInteger(-1);

    /** Spin manual (para endpoint /roulette/spin) */
    public int spinOnce() {
        int n = rulet.spin();
        appendToHistory(n);
        lastNumber.set(n);
        return n;
    }

    /** Auto-spin cada 1:30 min (90,000 ms). initialDelay opcional. */
    @Scheduled(fixedRate = 90_000L, initialDelay = 0L)
    public void autoSpin() {
        spinOnce();
    }

    /** Último número (o -1 si aún no hay) */
    public int getLastNumber() {
        return lastNumber.get();
    }

    /** Últimos k números (más reciente al final) */
    public List<Integer> getLastK(int k) {
        synchronized (lock) {
            return history.stream()
                          .skip(Math.max(0, history.size() - k))
                          .collect(Collectors.toList());
        }
    }

    /** Historial completo (máx 100) */
    public List<Integer> getHistory() {
        synchronized (lock) {
            return new ArrayList<>(history);
        }
    }

    /** Top N (hot) por frecuencia en el historial */
    public List<Map.Entry<Integer,Integer>> getHot(int topN) {
        Map<Integer,Integer> freq = getFrequencies();
        return freq.entrySet().stream()
                .sorted((a,b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue()); // desc por frecuencia
                    if (cmp == 0) return Integer.compare(a.getKey(), b.getKey()); // desempate por número
                    return cmp;
                })
                .limit(topN)
                .collect(Collectors.toList());
    }

    /** Bottom N (cold) por frecuencia en el historial */
    public List<Map.Entry<Integer,Integer>> getCold(int topN) {
        Map<Integer,Integer> freq = getFrequencies();
        return freq.entrySet().stream()
                .sorted((a,b) -> {
                    int cmp = Integer.compare(a.getValue(), b.getValue()); // asc por frecuencia
                    if (cmp == 0) return Integer.compare(a.getKey(), b.getKey());
                    return cmp;
                })
                .limit(topN)
                .collect(Collectors.toList());
    }

    /** Frecuencias de 0..36 en el historial */
    public Map<Integer,Integer> getFrequencies() {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < WHEEL_SIZE; i++) map.put(i, 0);
        synchronized (lock) {
            for (int n : history) {
                map.computeIfPresent(n, (k,v) -> v + 1);
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
