package com.herojeff;

import java.io.IOException;
import java.io.DataInputStream;
import java.math.BigInteger;
import java.util.Random;

public class Main {

    public static void main (String[] args) throws IOException
    {
        BigInteger p,q,N,phi,e,d;
        int bitlength = 1024;
        Random r;
        String inputString;

        DataInputStream in=new DataInputStream(System.in);

        System.out.print("Enter text : ");

        inputString=in.readLine();

        p = BigInteger.probablePrime(bitlength, new Random()).abs();
        q = BigInteger.probablePrime(bitlength, new Random()).abs();
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength-1, new Random());

        while ((phi.gcd(e).compareTo(BigInteger.ONE) > 0) && (e.compareTo(phi) < 0))
            e.add(BigInteger.ONE);

        d = e.modInverse(phi);


        System.out.println("N : "+p.multiply(q).intValue());
        System.out.println("e : "+e.intValue());
        System.out.println("d : "+d.intValue());


        byte[] encrypted = encrypt(inputString.getBytes(),e,N);
        byte[] decrypted = decrypt(encrypted,d,N);


        System.out.println("c : " + bytesToString(encrypted));
        System.out.println("Result : " + new String(decrypted));
    }

    private static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }

    public static byte[] encrypt(byte[] c, BigInteger e, BigInteger N) {
        return (new BigInteger(c)).modPow(e, N).toByteArray();
    }

    public static byte[] decrypt(byte[] c, BigInteger d, BigInteger N) {
        return (new BigInteger(c)).modPow(d, N).toByteArray();
    }


}