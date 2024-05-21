package com.example.authorizationservercodeandclient;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

@SpringBootApplication
public class AuthorizationServerCodeAndClientApplication {

      public static void main(String[] args) {
            SpringApplication.run(AuthorizationServerCodeAndClientApplication.class, args);
      }

}
