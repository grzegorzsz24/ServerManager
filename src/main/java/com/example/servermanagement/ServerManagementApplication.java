package com.example.servermanagement;

import com.example.servermanagement.enumeration.Status;
import com.example.servermanagement.model.Server;
import com.example.servermanagement.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ServerRepository serverRepository) {
        return args -> {
            serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu linux", "16 GB", "Personal PC",
                    "http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
        };
    }

}
