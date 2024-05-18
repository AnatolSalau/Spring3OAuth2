package com.example.authorizationserverisonspringinitializr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class OauthConfig {
      @Bean
      InMemoryUserDetailsManager inMemoryUserDetailsManager () {
            UserDetails admin = User.withDefaultPasswordEncoder()
                  .username("admin")
                  .password("admin")
                  .roles("ADMIN")
                  .authorities("user.read", "user.write")
                  .build();
            UserDetails user = User.withDefaultPasswordEncoder()
                  .username("user")
                  .password("user")
                  .roles("USER")
                  .authorities("user.read")
                  .build();
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(
                  admin, user
            );

            return inMemoryUserDetailsManager;
      }
}
