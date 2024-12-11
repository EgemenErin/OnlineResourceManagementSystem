package com.egemen.onlineresourcemanagementsys.config;

import com.egemen.onlineresourcemanagementsys.utils.KeyUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.io.File;

@Configuration
public class SecurityConfig {
    @Bean
    public SecretKey secretKey() throws Exception {
        try {
            // Defining the key file path
            String keyFilePath = "data/secretKey.ser";
            File keyFile = new File("data/secretKey.ser");

            // Loading the key if it exists
            if (keyFile.exists()) {
                return KeyUtil.loadSecretKey("data/secretKey.ser");
            } else {
                // Generate and save a new key
                SecretKey secretKey = KeyUtil.generateSecretKey();
                KeyUtil.saveSecretKey(secretKey, "data/secretKey.ser");
                return secretKey;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error managing SecretKey: " + e.getMessage(), e);
        }
    }
}
