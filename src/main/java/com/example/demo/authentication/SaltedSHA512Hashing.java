package com.example.demo.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

public class SaltedSHA512Hashing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the password to hash: ");
        String password = scanner.nextLine();

        // Generate a random salt
        byte[] salt = generateSalt();

        try {
            String hashedPassword = generateSaltedSHA512Hash(password, salt);
            System.out.println("Salt: " + bytesToHex(salt));
            System.out.println("Salted SHA-512 Hash: " + hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-512 algorithm not available.");
            e.printStackTrace();
        }
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String generateSaltedSHA512Hash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");

        // Add the salt to the message digest
        md.update(salt);

        // Hash the password along with the salt
        byte[] hashedBytes = md.digest(password.getBytes());

        // Convert the byte array to a hexadecimal string
        StringBuilder hexStringBuilder = new StringBuilder(2 * hashedBytes.length);
        for (byte b : hashedBytes) {
            hexStringBuilder.append(String.format("%02x", b & 0xff));
        }

        return hexStringBuilder.toString();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b & 0xff));
        }
        return hexStringBuilder.toString();
    }
}
