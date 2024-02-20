package com.tenantvaluation.trainning.adapter.config.security;

import com.tenantvaluation.trainning.domain.port.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter  implements WebFilter {

   private  final JwtService jwtUtilities ;
   private final UserService customerUserDetailsService ;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        var token = jwtUtilities.getToken(exchange.getRequest());

        if (token != null && jwtUtilities.validateToken(token))
        {
            String username = jwtUtilities.extractUsername(token);

            UserDetails userDetails = customerUserDetailsService.loadUserByUsername(username);
            Optional.ofNullable(userDetails)
                .filter(userDetails1 -> userDetails1.getUsername().equals(username))
                .ifPresent(userDetails1 ->  SecurityContextHolder
                    .getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername() ,null , userDetails.getAuthorities())));
        }

        return chain.filter(exchange);
    }
}
