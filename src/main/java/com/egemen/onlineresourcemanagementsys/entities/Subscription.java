package com.egemen.onlineresourcemanagementsys.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscriptions")
public class Subscription extends Resource {
    @Column(nullable = false)
    private String subscriptionType;

    public Subscription() {}

    public Subscription(String name, String username, String password, String subscriptionType) {
        super(name, username, password);
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public void displayInfo() {
        System.out.println("Subscription: " + name + " (Type: " + subscriptionType + " username: " + username + " password: "+ password +")");
    }
}
