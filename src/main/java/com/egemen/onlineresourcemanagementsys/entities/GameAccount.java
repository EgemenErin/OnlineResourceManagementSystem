package com.egemen.onlineresourcemanagementsys.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_accounts")
public class GameAccount extends Resource {

    @Column(nullable = false)
    private String gamePlatform;

    public GameAccount() {}

    public String getGamePlatform() {
        return gamePlatform;
    }
    public GameAccount(String name, String username, String password,String gamePlatform) {
        super(name, username, password);
        this.gamePlatform = gamePlatform;
    }

    public void setGamePlatform(String gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public String getGamePlatformName() {
        return gamePlatform;
    }


    @Override
    public void displayInfo() {
        System.out.println("Game Account: " + name + " (Platform: " + gamePlatform + " username: " + username + " password: "+ password +")");
    }
}
