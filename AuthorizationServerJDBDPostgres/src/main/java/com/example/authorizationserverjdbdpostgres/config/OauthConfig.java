package com.example.authorizationserverjdbdpostgres.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class OauthConfig {
      @Bean
      UserDetailsService inMemoryUserDetailsManager () {
            UserDetails admin = User.builder()
                  .username("admin")
                  .password("{bcrypt}$2a$12$so9qB.qDqWAGssMch9sJ2eHIHiTxdxa80972OIbQnMJ2Uixq6dySS")
                  .roles("ADMIN", "USER")
                  .authorities("user.read", "user.write")
                  .build();
            UserDetails user = User.builder()
                  .username("user")
                  .password("{bcrypt}$2a$12$bm813MmPwY0EpF5U4r1nIOYXSRHmNz.gQY.BX2YCvOuDITPNel3yi")
                  .roles("USER")
                  .authorities("user.read", "user.write")
                  .build();
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(
                  admin, user
            );

            return inMemoryUserDetailsManager;
      }
}
