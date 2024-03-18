package com.tenantvaluation.trainning.gateway.adapter.login.controller;

import com.tenantvaluation.trainning.gateway.adapter.config.security.JwtService;
import com.tenantvaluation.trainning.gateway.adapter.login.request.AuthRequest;
import com.tenantvaluation.trainning.gateway.domain.port.api.LoginAPI;
import com.tenantvaluation.trainning.gateway.domain.port.service.UserService;
import com.tenantvaluation.trainning.gateway.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class LoginController implements LoginAPI {

    private final JwtService jwtService;
    private final UserDetailsRepositoryReactiveAuthenticationManager authenticationManager;
    private final UserService userService;

    public ResponseEntity<User> login (@RequestBody AuthRequest authRequest) {
                var authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()))
            .block();

        Optional.ofNullable(authentication).orElseThrow(() -> new UsernameNotFoundException("invalid user request !"));

        if (authentication.isAuthenticated()) {
            var user = (User) userService.loadUserByUsername(authRequest.getUsername());
            var authToken = jwtService.generateToken(user);
            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .body(user);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public ResponseEntity<String> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("redirect:/home");
    }

}
