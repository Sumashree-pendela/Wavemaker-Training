package com.wavemaker;

import java.math.BigInteger;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.println("Enter two values ");
    BigInteger a = sc.nextBigInteger();
    BigInteger b = sc.nextBigInteger();
    System.out.print("Enter operation to perform: ");
    String op = sc.next();
    sc.close();

    BigInteger result = null;

    switch(op) {
        case "+":
            result = a.add(b);
            break;

        case "-":
            result = a.subtract(b);
            break;

        case "*":
            result = a.multiply(b);
            break;

        case "/":
            result = a.divide(b);
            break;
    }

        System.out.println("Value: " +result);

    }
}
