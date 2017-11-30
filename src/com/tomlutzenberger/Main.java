package com.tomlutzenberger;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        boolean exit = false;
        int nanosec, sec, min, hour;
        long wait;
        LocalDateTime now;

        System.out.println("Binary Clock");
        while (!exit) {
            now = LocalDateTime.now();
            nanosec = now.getNano();
            sec = now.getSecond();
            min = now.getMinute();
            hour = now.getHour();

            System.out.print("\r");
            printBits( getBinaryArrayFromInt(hour, (byte)5) );
            System.out.print(" : ");
            printBits( getBinaryArrayFromInt(min, (byte)6) );
            System.out.print(" : ");
            printBits( getBinaryArrayFromInt(sec, (byte)6) );

            //calculate to nano difference between now an the loop start to effectively only wait 1 sec
            wait = TimeUnit.SECONDS.toNanos(1) - (now.getNano() - nanosec);
            TimeUnit.NANOSECONDS.sleep(wait);
        }
    }

    /**
     * Get all bits from an Integer as a boolean array
     *
     * @param integer The integer to read the bits from
     * @param size The needed array size to hold all bits
     * @return The boolean array
     */
    private static boolean[] getBinaryArrayFromInt(Integer integer, byte size)
    {
        boolean[] bits = new boolean[size];
        for (int i = 0; i < size; i++) {
            bits[i] = (integer & (1 << i)) != 0;
        }

        return bits;
    }

    /**
     * Prints all bits of a given array.
     * Depending on the bit's value the output will be either a filled or empty circle (true/false).
     * Every 4 bits (from right) an additional spacing will be printed.
     *
     * @param bits An array of booleans representing the bits of a number right-to-left
     */
    private static void printBits(boolean[] bits)
    {
        for(int i = bits.length - 1; i >= 0; i--) {
            if ((i+1) % 4 == 0) System.out.print(" ");
            System.out.print((bits[i] ? "\u25CF" : "\u25CB"));
        }
    }

}
