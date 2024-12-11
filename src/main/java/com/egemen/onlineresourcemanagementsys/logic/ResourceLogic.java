package com.egemen.onlineresourcemanagementsys.logic;

import com.egemen.onlineresourcemanagementsys.entities.Resource;
import com.egemen.onlineresourcemanagementsys.entities.User;
import com.egemen.onlineresourcemanagementsys.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class handling resource-related operations such as adding, deleting, finding, and grouping resources.
 */
@Service
public class ResourceLogic {
    private final ResourceRepository resourceRepository;
    private static final Logger logger = LoggerFactory.getLogger(ResourceLogic.class);

    /**
     * Constructor for injecting dependencies.
     *
     * @param resourceRepository Repository interface for Resource entity operations.
     */
    public ResourceLogic(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Adds a new resource and associates it with the specified user.
     *
     * @param resource The resource to be added.
     * @param user     The user to whom the resource will be associated.
     */
    public void addResource(Resource resource, User user) {
        try {
            resource.setUser(user);
            resourceRepository.save(resource);
            logger.info("🆕 Resource '{}' added for user '{}'.", resource.getName(), user.getUsername());
        } catch (Exception e) {
            logger.error("❌ Failed to add resource '{}' for user '{}': {}", resource.getName(), user.getUsername(), e.getMessage());
            throw e; // Rethrow or handle accordingly
        }
    }

    /**
     * Deletes an existing resource by its unique identifier.
     *
     * @param id The unique identifier of the resource to be deleted.
     */
    public void deleteResource(int id) {
        try {
            if (resourceRepository.existsById(id)) {
                resourceRepository.deleteById(id);
                logger.info("🗑️ Resource with ID {} has been deleted.", id);
            } else {
                logger.warn("⚠️ Attempted to delete non-existent resource with ID {}.", id);
            }
        } catch (Exception e) {
            logger.error("❌ Failed to delete resource with ID {}: {}", id, e.getMessage());
            throw e; // Rethrow or handle accordingly
        }
    }

    /**
     * Finds a resource by its name and associated user.
     *
     * @param resourceName The name of the resource to find.
     * @param user         The user associated with the resource.
     * @return The found Resource object, or null if not found.
     */
    public Resource findResourceByNameAndUser(String resourceName, User user) {
        try {
            Resource resource = resourceRepository.findResourceByNameAndUser(resourceName, user);
            if (resource != null) {
                logger.info("🔍 Resource '{}' found for user '{}'.", resourceName, user.getUsername());
            } else {
                logger.warn("⚠️ Resource '{}' not found for user '{}'.", resourceName, user.getUsername());
            }
            return resource;
        } catch (Exception e) {
            logger.error("❌ Error finding resource '{}' for user '{}': {}", resourceName, user.getUsername(), e.getMessage());
            throw e; // Rethrow or handle accordingly
        }
    }

    /**
     * Saves or updates an existing resource.
     *
     * @param resource The resource to be saved or updated.
     */
    public void saveResource(Resource resource) {
        try {
            resourceRepository.save(resource);
            logger.info("🔄 Resource '{}' has been updated.", resource.getName());
        } catch (Exception e) {
            logger.error("❌ Failed to update resource '{}': {}", resource.getName(), e.getMessage());
            throw e; // Rethrow or handle accordingly
        }
    }

    /**
     * Retrieves all resources associated with a user, grouping them by their type.
     *
     * @param userId The unique identifier of the user.
     * @return A map grouping resources by their class type (e.g., GameAccount, Subscription).
     */
    public Map<String, List<Resource>> groupResourcesByType(int userId) {
        try {
            List<Resource> resources = resourceRepository.findAllByUserId(userId);
            Map<String, List<Resource>> groupedResources = resources.stream()
                    .collect(Collectors.groupingBy(resource -> resource.getClass().getSimpleName()));
            logger.info("📂 Resources for user ID {} have been grouped by type.", userId);
            return groupedResources;
        } catch (Exception e) {
            logger.error("❌ Failed to group resources for user ID {}: {}", userId, e.getMessage());
            throw e; // Rethrow or handle accordingly
        }
    }
}
