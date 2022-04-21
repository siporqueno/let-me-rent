package ru.letmerent.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.letmerent.core.dto.AuthRequest;
import ru.letmerent.core.dto.AuthResponse;
import ru.letmerent.core.exceptions.ApplicationError;
import ru.letmerent.core.services.impl.UserService;
import ru.letmerent.core.utils.TokenUtil;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "API для работы с сервисом аутентификации")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper;

    @Operation(summary = "Аутентификация")
    @PostMapping
    @ApiResponse(
            responseCode = "200",
            description = "Аутентификая прошла успешно.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class)
            ))
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            String token = tokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(mapper.valueToTree(ApplicationError.builder()
                            .errorCode(HttpStatus.UNAUTHORIZED.value())
                            .userMessage(e.getMessage())
                            .date(new Date())
                            .build()));
        }
    }
}
