package com.example.authorizationservercodeandclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
      InMemoryUserDetailsManager inMemoryUserDetailsManager () {
            UserDetails admin = User.withDefaultPasswordEncoder()
                  .username("admin")
                  .password("admin")
                  .roles("ADMIN")
                  .build();
            UserDetails user = User.withDefaultPasswordEncoder()
                  .username("user")
                  .password("user")
                  .roles("USER")
                  .build();
            InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(
                  admin, user
            );

            return inMemoryUserDetailsManager;
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
