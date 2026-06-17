package hestia.hestiaBackEnd.controller;

import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import hestia.hestiaBackEnd.dto.LoginRequest;
import hestia.hestiaBackEnd.dto.LoginResponse;
import hestia.hestiaBackEnd.fachada.IFachada;
import hestia.hestiaBackEnd.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IFachada fachada;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(IFachada fachada, BCryptPasswordEncoder encoder, JwtService jwtService) {
        this.fachada = fachada;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Hospede filtro = new Hospede();
        filtro.setCpf(loginRequest.getCpf());
        EntidadeDominio[] encontrados = fachada.consultar(filtro);

        if (encontrados.length == 0) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos.");
        }

        Hospede hospede = (Hospede) encontrados[0];
        if (!hospede.isAtivo() || !encoder.matches(loginRequest.getSenha(), hospede.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos.");
        }

        String token = jwtService.gerarToken(hospede);
        return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
    }
}
