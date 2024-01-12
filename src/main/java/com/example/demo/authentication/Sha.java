package com.example.demo.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Sha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the string to hash: ");
        String input = scanner.nextLine();

        try {
            String sha512Hash = generateSHA512Hash(input);
            System.out.println("SHA-512 Hash: " + sha512Hash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-512 algorithm not available.");
            e.printStackTrace();
        }
    }

    private static String generateSHA512Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedBytes = md.digest(input.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexStringBuilder = new StringBuilder(2 * hashedBytes.length);
        for (byte b : hashedBytes) {
            hexStringBuilder.append(String.format("%02x", b & 0xff));
        }

        return hexStringBuilder.toString();
    }
}
