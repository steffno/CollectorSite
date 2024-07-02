package org.univaq.swa.css.cssrest.data;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author MastroGibbs
 */
public class Utils {
    
    
    public static LocalDate getRandomDate()
    {
        LocalDate startDate = LocalDate.of(1970, 1, 1);
        long start = startDate.toEpochDay();


        LocalDate endDate = LocalDate.now();
        long end = endDate.toEpochDay();


        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay);
    }
    
    
    public static int randInt(int limit)
    {
        Random rand = new Random();
        
        return rand.nextInt(limit);
    }
    
    public static float randFloat(float multiplier)
    {
        Random rand = new Random();
        
        return rand.nextFloat() * multiplier;
    }
    
}
