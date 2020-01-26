package com.mosl;

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException {

         long a = Integer.MAX_VALUE + 1L;
         long b = 0L;

         System.out.println( (int)(a - b));
    }
}
