package hestia.hestiaBackEnd.controller;

import hestia.hestiaBackEnd.dto.LoginRequest;
import hestia.hestiaBackEnd.dto.LoginResponse;
import hestia.hestiaBackEnd.service.HospedeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final HospedeService hospedeService;

    public AuthController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = hospedeService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}