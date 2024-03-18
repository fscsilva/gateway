package com.tenantvaluation.trainning.gateway.adapter.config.security;

import com.tenantvaluation.trainning.gateway.domain.port.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserService userService;
    private final JwtAuthenticationFilter authFilter;
    private static final String ALL_INCLUDE_PATH = "/**";
    String BASE_PATH = "/v1";
    String BASE_PATH_V2 = "/v2";

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.securityMatcher(
                ServerWebExchangeMatchers.pathMatchers(HttpMethod.GET, BASE_PATH + ALL_INCLUDE_PATH, BASE_PATH_V2 + ALL_INCLUDE_PATH))
            .authorizeExchange( authorizeExchangeSpec ->
                authorizeExchangeSpec.pathMatchers(HttpMethod.GET,buildNotSecuredGetResources())
                    .permitAll()
                    .pathMatchers(HttpMethod.POST, buildNotSecuredGetResources())
                    .permitAll()
                    .pathMatchers(BASE_PATH  + "/admin/**", BASE_PATH_V2  + "/admin/**").hasAuthority("ADMIN")
                    .pathMatchers(BASE_PATH  + "/user/**", BASE_PATH_V2  + "/user/**").hasAnyAuthority("USER", "ADMIN")
                    .pathMatchers(HttpMethod.POST, BASE_PATH + "/movies", BASE_PATH + "/authors").hasAuthority("ADMIN")
                    .pathMatchers(HttpMethod.PUT, BASE_PATH + "/movies", BASE_PATH + "/authors").hasAuthority("ADMIN")
                    .pathMatchers(HttpMethod.PATCH, BASE_PATH + "/movies", BASE_PATH + "/authors").hasAuthority("ADMIN")
                    .pathMatchers(HttpMethod.DELETE, BASE_PATH + "/movies", BASE_PATH + "/authors").hasAuthority("ADMIN")
                    .pathMatchers(HttpMethod.GET, BASE_PATH + "/movies", BASE_PATH + "/authors").hasAnyAuthority("USER", "ADMIN")
                    .anyExchange()
                    .permitAll()
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(ServerHttpSecurity.CorsSpec::disable)
            .exceptionHandling(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .formLogin(form -> form
                .loginPage(BASE_PATH + "/login"))
            .logout(logout -> logout
                .logoutUrl(BASE_PATH + "/logout").disable()
        );


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsRepositoryReactiveAuthenticationManager authenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager manager =
            new UserDetailsRepositoryReactiveAuthenticationManager(userService);
        manager.setPasswordEncoder(passwordEncoder());
        return manager;
    }


    private String[] buildNotSecuredGetResources() {
        return new String[]{"/v3/api-docs", "/swagger-ui/index.html",  "/login", "/logout"};
    }

}
