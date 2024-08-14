package com.wavemaker;

import java.util.Random;
import java.util.Scanner;

public class NumberGuess {
    public static void main(String[] args) {
        Random r = new Random();

        int num = r.nextInt(1000);

        while(true) {
            System.out.println("Enter a value : ");
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            if(num == n) {
                System.out.println("You guessed it correct");
                break;
            }

            if(num < n) {
                System.out.println("Your guessed number is greater");
            }

            if(num > n) {
                System.out.println("Your guessed number is lesser");
            }
        }
    }
}
