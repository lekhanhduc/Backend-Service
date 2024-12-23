package vn.khanhduc.backendservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendServiceApplication {

    @Value("${jwt.secretKey}")
    private String jwt;
    public static void main(String[] args) {
        SpringApplication.run(BackendServiceApplication.class, args);
    }

    @PostConstruct
    public void printJwt() {
        System.out.println("jwt --> " +jwt);
    }

}
