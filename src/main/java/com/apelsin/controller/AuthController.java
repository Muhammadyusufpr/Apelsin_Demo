package com.apelsin.controller;


import com.apelsin.dto.RegistrationDto;
import com.apelsin.dto.SmsDTO;
import com.apelsin.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@Api(tags = "For Authorization")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "registration", notes = "Method for registration", nickname = "Mazgi")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDto dto) {
        log.info("registration: {}", dto);
        authService.registration(dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "activation", notes = "Method for activation", nickname = "Mazgi")
    @PostMapping("/activation")
    public ResponseEntity<?> activisation(@RequestBody @Valid SmsDTO dto) {
        log.info("registration: {}", dto);
        return ResponseEntity.ok(authService.activation(dto));
    }

    @ApiOperation(value = "activation", notes = "Method for activation", nickname = "Mazgi")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid RegistrationDto dto) {
        log.warn("login: {}", dto);
        return ResponseEntity.ok(authService.login(dto));
    }
}
