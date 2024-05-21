package com.example.authorizationservercodeandclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class OauthConfig {

      @Bean
      public UserDetailsService userDetailsService () {
            UserDetails admin = User
                  .withUsername("admin")
                  .password("{noop}admin")
                  .roles("ADMIN")
                  .build();
            UserDetails user = User
                  .withUsername("user")
                  .password("{noop}user")
                  .roles("USER")
                  .build();
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(
                  admin, user
            );

            return inMemoryUserDetailsManager;
      }


      @Bean
      public PasswordEncoder passwordEncoder() {

            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
      }

      @Bean
      public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(userDetailsService);
            return new ProviderManager(provider);
      }

      @Bean
      public DaoAuthenticationProvider inMemoryDaoAuthenticationProvider() {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(userDetailsService());
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            return daoAuthenticationProvider;
      }

      @Bean
      public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
            return (context) -> {
                  if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                        context.getClaims().claims((claims) -> {
                              Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities());
                              roles
                                    .stream()
                                    .map(c -> c.replaceFirst("^ROLE_", ""))
                                    .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                              claims.put("roles", roles);
                        });
                  }
            };
      }
}
