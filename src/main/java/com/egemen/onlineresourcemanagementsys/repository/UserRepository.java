package com.egemen.onlineresourcemanagementsys.repository;

import com.egemen.onlineresourcemanagementsys.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User, Integer> {
    User findByUsername(String username);
}
