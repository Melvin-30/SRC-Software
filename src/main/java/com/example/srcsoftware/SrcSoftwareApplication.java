package com.example.srcsoftware;

import com.example.srcsoftware.Service.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SrcSoftwareApplication {

    @Autowired
    private DatabaseInitializer initializer;

    public static void main(String[] args) {
        SpringApplication.run(SrcSoftwareApplication.class, args);
    }

    @PostConstruct
    public void init() {
        initializer.init();
    }
}
