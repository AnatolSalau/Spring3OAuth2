package com.example.resourceserverisonspringinitializr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

      @Bean
      public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                  .authorizeHttpRequests(req -> {
                        req.requestMatchers("/admin", "/user", "/" ).authenticated();
                  })
                  .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt((jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(
                                    jwt -> new JwtAuthenticationToken(jwt, getDualJwtAuthenticationConverter().convert(jwt))
                              ))
                        )
                  );
            return http.build();
      }
      private DelegatingJwtGrantedAuthoritiesConverter getDualJwtAuthenticationConverter() {

            JwtGrantedAuthoritiesConverter scope = new JwtGrantedAuthoritiesConverter();
            scope.setAuthorityPrefix("SCOPE_");
            scope.setAuthoritiesClaimName("scope");
            JwtGrantedAuthoritiesConverter roles = new JwtGrantedAuthoritiesConverter();
            roles.setAuthorityPrefix("ROLE_");
            roles.setAuthoritiesClaimName("roles");
            return new DelegatingJwtGrantedAuthoritiesConverter(scope, roles);
      }
}
