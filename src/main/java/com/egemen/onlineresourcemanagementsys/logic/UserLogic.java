package com.egemen.onlineresourcemanagementsys.logic;

import com.egemen.onlineresourcemanagementsys.entities.User;
import com.egemen.onlineresourcemanagementsys.repository.UserRepository;
import com.egemen.onlineresourcemanagementsys.utils.CipherUtil;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

/**
 * Service class handling user-related operations such as registration and deletion.
 */
@Service
public class UserLogic {
    private final UserRepository userRepository;
    private final SecretKey secretKey;

    /**
     * Constructor for injecting dependencies.
     *
     * @param userRepository Repository interface for User entity operations.
     * @param secretKey      SecretKey used for encrypting user passwords.
     */
    public UserLogic(UserRepository userRepository, SecretKey secretKey) {
        this.userRepository = userRepository;
        this.secretKey = secretKey;
    }

    /**
     * Registers a new user with the provided username and password.
     *
     * @param username The desired username for the new user.
     * @param password The plain-text password for the new user.
     * @return The saved User entity.
     * @throws IllegalArgumentException If the username already exists.
     */
    public User registerUser(String username, String password) {
        // Check if the username already exists in the repository
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("❌ Username already exists. Please log in instead.");
        }

        // Encrypt the user's password using the provided SecretKey
        byte[] encryptedPassword = CipherUtil.encryptPassword(password, secretKey);
        if (encryptedPassword == null) {
            throw new IllegalStateException("❌ Failed to encrypt the password. Registration aborted.");
        }

        // Create a new User entity with the encrypted password
        User user = new User(username, encryptedPassword);

        // Save the new user to the repository
        User savedUser = userRepository.save(user);

        // Provide feedback upon successful registration
        System.out.println("🎉 Registration successful! Welcome, " + savedUser.getUsername() + "!");

        return savedUser;
    }

    /**
     * Deletes an existing user by their unique identifier.
     *
     * @param userId The unique identifier of the user to be deleted.
     */
    public void deleteUser(int userId) {
        // Check if the user exists before attempting deletion
        if (!userRepository.existsById(userId)) {
            System.out.println("⚠️ User with ID " + userId + " does not exist.");
            return;
        }

        // Delete the user from the repository
        userRepository.deleteById(userId);

        // Provide feedback upon successful deletion
        System.out.println("🗑️ User with ID " + userId + " has been deleted successfully.");
    }
}
