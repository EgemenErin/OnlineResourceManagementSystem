package com.egemen.onlineresourcemanagementsys.entities;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "resources")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Resource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Relationship with User

    public Resource() {}

    public Resource(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void displayInfo();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
