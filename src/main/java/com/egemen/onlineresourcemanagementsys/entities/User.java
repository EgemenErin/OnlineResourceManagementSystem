package com.egemen.onlineresourcemanagementsys.entities;

import com.egemen.onlineresourcemanagementsys.utils.CipherUtil;
import jakarta.persistence.*;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private byte[] encryptedPassword;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Resource> resources = new ArrayList<>();

    public User() {}


    public User(String username, byte[] encryptedPassword) {
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
    }

    public void addResource(Resource resource) {
        resources.add(resource);
    }


    public Resource getResource(String resourceName) {
        return resources.stream().filter(resource -> resource.getName().equals(resourceName)).findFirst().orElse(null);
    }

    public boolean verifyPassword(String inputPassword, SecretKey key) {
        return CipherUtil.verifyPassword(encryptedPassword, inputPassword, key);
    }
}
