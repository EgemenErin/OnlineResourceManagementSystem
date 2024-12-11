package com.egemen.onlineresourcemanagementsys.utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;

public class KeyUtil {
    /**
     * Saves the provided SecretKey to a specified file.
     *
     * @param secretKey The SecretKey to be saved.
     * @param fileName  The name of the file where the key will be stored.
     */
    public static void saveSecretKey(SecretKey secretKey, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(secretKey);
            System.out.println("🔑 Secret key has been successfully saved to '" + fileName + "'.");
        } catch (Exception e) {
            System.err.println("❌ Failed to save the secret key: " + e.getMessage());
        }
    }

    /**
     * Loads a SecretKey from the specified file.
     *
     * @param fileName The name of the file from which the key will be loaded.
     * @return The loaded SecretKey, or null if loading fails.
     */
    public static SecretKey loadSecretKey(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            SecretKey secretKey = (SecretKey) ois.readObject();
            System.out.println("🔑 Secret key successfully loaded from '" + fileName + "'.");
            return secretKey;
        } catch (FileNotFoundException e) {
            System.err.println("⚠️ Secret key file '" + fileName + "' not found.");
            return null;
        } catch (Exception e) {
            System.err.println("❌ Error loading the secret key: " + e.getMessage());
            return null;
        }
    }

    /**
     * Generates a new SecretKey for AES encryption.
     *
     * @return A newly generated SecretKey.
     * @throws Exception If the key generation fails.
     */
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128); // For stronger encryption, consider using 256 bits
        SecretKey secretKey = keyGen.generateKey();
        System.out.println("🔄 A new AES SecretKey has been generated.");
        return secretKey;
    }
}
