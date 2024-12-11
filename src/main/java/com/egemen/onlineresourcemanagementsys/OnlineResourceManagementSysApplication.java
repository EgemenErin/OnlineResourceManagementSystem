package com.egemen.onlineresourcemanagementsys;

import com.egemen.onlineresourcemanagementsys.menu.MainMenu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineResourceManagementSysApplication {
    public static void main(String[] args) {

        SpringApplication.run(OnlineResourceManagementSysApplication.class, args);

    }

    @Bean
    public CommandLineRunner run(MainMenu mainMenu) {
        return args -> {
            try {
                mainMenu.display();
            } catch (Exception e) {
                System.err.println("An error occurred while running the application: " + e.getMessage());
            }
        };
    }

}
