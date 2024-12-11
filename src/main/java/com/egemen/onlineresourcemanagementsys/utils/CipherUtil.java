package com.egemen.onlineresourcemanagementsys.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CipherUtil {

    /**
     * Encrypts the provided password using the given SecretKey.
     *
     * @param password The plain-text password to encrypt.
     * @param key      The SecretKey used for encryption.
     * @return The encrypted password as a byte array, or null if encryption fails.
     */
    public static byte[] encryptPassword(String password, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            System.out.println("🔒 Password encrypted successfully: " + Arrays.toString(encrypted)); // Debug log
            return encrypted;
        } catch (Exception e) {
            System.err.println("❌ Error encrypting password: " + e.getMessage());
            return null;
        }
    }

    /**
     * Verifies the input password by comparing it with the encrypted password.
     *
     * @param encryptedPassword The encrypted password stored for comparison.
     * @param inputPassword     The plain-text password input to verify.
     * @param key               The SecretKey used for decryption.
     * @return True if the passwords match; false otherwise.
     */
    public static boolean verifyPassword(byte[] encryptedPassword, String inputPassword, SecretKey key) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(encryptedPassword);
            System.out.println("🔓 Password decrypted successfully: " + new String(decrypted)); // Debug log
            boolean isMatch = Arrays.equals(decrypted, inputPassword.getBytes(StandardCharsets.UTF_8));
            if (isMatch) {
                System.out.println("✅ Password verification successful. The passwords match.");
            } else {
                System.out.println("⚠️ Password verification failed. The passwords do not match.");
            }
            return isMatch;
        } catch (Exception e) {
            System.err.println("❌ Error decrypting password: " + e.getMessage());
            return false;
        }
    }
}
