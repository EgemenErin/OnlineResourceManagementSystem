package com.egemen.onlineresourcemanagementsys.repository;

import com.egemen.onlineresourcemanagementsys.entities.Resource;
import com.egemen.onlineresourcemanagementsys.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends GenericRepository<Resource, Integer> {
    List<Resource> findAllByUserId(int userId);
    Resource findResourceByNameAndUser(String resourceName, User user);
}
