package com.example.demo.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class VerifySaltedSHA512Hash {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Simulating a stored salt and hash (you would retrieve these from your storage)
        String storedSaltHex = "e214b5c6cb7072e59b4913e7e7199f44";
        String storedHash = "089f8950c1ffb87a378c98dc50a04e82aecae7fd10898aca2d5a179f614ea7d66a2d7064423583df27af232f27994b65863e1a8f494775cd45fd396c87e6bb8c";

        System.out.print("Enter the password to check: ");
        String passwordToCheck = scanner.nextLine();

        try {
            // Convert the stored salt from hex to byte array
            byte[] storedSalt = hexToBytes(storedSaltHex);

            // Hash the user-provided password with the stored salt
            String hashedPasswordToCheck = generateSaltedSHA512Hash(passwordToCheck, storedSalt);

            // Compare the generated hash with the stored hash
            if (hashedPasswordToCheck.equals(storedHash)) {
                System.out.println("Password is correct.");
            } else {
                System.out.println("Password is incorrect.");
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-512 algorithm not available.");
            e.printStackTrace();
        }
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

    private static byte[] hexToBytes(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
