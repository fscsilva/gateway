package com.tenantvaluation.trainning.gateway.adapter.user;

import com.tenantvaluation.trainning.gateway.domain.port.service.UserService;
import com.tenantvaluation.trainning.gateway.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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

        return CompletableFuture.supplyAsync(() -> restTemplate.getForObject(queryUrl, User.class))
            .thenApply(user -> User.builder()
                .userId(user.getUserId())
                .statusActive(user.getStatusActive())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .authorities(user.getAuthoritiesList().stream()
                    .map(customGrantedAuthority -> new SimpleGrantedAuthority(customGrantedAuthority.getAuthority()))
                    .collect(Collectors.toList()))
                .build());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        var user = getUserByUsername(username).join();
        return Mono.just(user);
    }
}