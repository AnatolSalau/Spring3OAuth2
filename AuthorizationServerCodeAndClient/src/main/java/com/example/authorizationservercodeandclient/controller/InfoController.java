package com.example.authorizationservercodeandclient.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class InfoController {

      @GetMapping("/info")
      Map<String,String> getInfo(@AuthenticationPrincipal Jwt jwt, Authentication authentication) {
            Map<String, String> result = new HashMap<>( Map.of(
                  "hello", "from sayHello",
                  "message", "Hello : " + jwt.getSubject(),
                  "authority", "Authorities :  " + authentication.getAuthorities()
            ));

            return result;
      }
}
