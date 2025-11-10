package com.casino.Model;

import lombok.Data;
import java.util.Random;

@Data
public class Rulet {

    private final Random random = new Random();

    public int spin() {
        return random.nextInt(37); // valores entre 0 y 36
    }


    
}
