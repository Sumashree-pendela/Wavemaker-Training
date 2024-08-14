package com.wavemaker;

public class Datatype {
    public static void main(String[] args) {
         String name = "Suma";
         int n = 10;
         char ch = 'c';
         float f = 10.3f;
         double d = 10.0;
         boolean bool = false;
         short s = 10;
         long l = 100;
         byte b = 20;

         int[] arr = {1, 2, 3, 4, 5};

         System.out.println("Name: " +name);
         System.out.println("n: " + n);
         System.out.println("ch: "+ ch);
         System.out.println("Float :" + f);
         System.out.println("double :" + d);
         System.out.println("boolean :" + bool);
         System.out.println("short :" + s);
         System.out.println("long :" + l);
         System.out.println("byte :" + b);

         System.out.print("Array Values:");
         for(int i=0;i< arr.length;i++) {
              System.out.print(arr[i] + ", ");
         }
    }
}
