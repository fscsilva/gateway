package com.tenantvaluation.trainning.adapter.user;

import com.tenantvaluation.trainning.domain.port.services.UserService;
import com.tenantvaluation.trainning.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class UserClient implements UserService {

    @Qualifier("user-rest")
    private final RestTemplate restTemplate;

    @Value("${user-service.getUserByUsername}")
    private String getUserByUsernameURL;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(getUserByUsername(username).join())
            .orElseThrow(() -> new UserNotFoundException("User not found " + username));
    }

    public static String getPrincipal() {
        return String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public CompletableFuture<User> getUserByUsername(String username) {
        var queryUrl = UriComponentsBuilder
            .fromHttpUrl(getUserByUsernameURL)
            .buildAndExpand(username)
            .toUriString();

        var encoder = new BCryptPasswordEncoder();
        return CompletableFuture.completedFuture(User.builder()
                .username("username")
                .password(encoder.encode("password"))
            .authorities(Collections.singletonList(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "ADMIN";
                }
            }))
            .statusActive(true).build());

        /*return CompletableFuture.completedFuture(restTemplate.getForObject(queryUrl, User.class))
            .thenApply(response -> Optional.ofNullable(response)
                .filter(title -> response.getStatusActive())
                .orElse(null));*/
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.just(getUserByUsername(username).join());
    }
}