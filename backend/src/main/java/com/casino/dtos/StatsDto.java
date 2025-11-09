package com.casino.dtos;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class StatsDto {
    private int totalSpins;
    private Map<Integer,Integer> frequencies;   // 0..36 -> count
    private List<Integer> last6;                // últimos 6
    private List<Integer> hot;                  // top 5 por defecto
    private List<Integer> cold;                 // bottom 5 por defecto
}
