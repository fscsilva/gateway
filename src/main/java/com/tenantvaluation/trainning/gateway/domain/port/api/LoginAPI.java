package com.tenantvaluation.trainning.gateway.domain.port.api;


import com.tenantvaluation.trainning.gateway.adapter.login.request.AuthRequest;
import com.tenantvaluation.trainning.gateway.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public interface LoginAPI {

    @Operation(summary = "Login with username and password")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logged in", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to login"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<User> login(@RequestBody AuthRequest authRequest);

    @Operation(summary = "Logout")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logged out", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)) }),
        @ApiResponse(responseCode = "500", description = "There was an error trying to logout"),
        @ApiResponse(responseCode = "400", description = "Malformed request")})
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> logout() ;

}
