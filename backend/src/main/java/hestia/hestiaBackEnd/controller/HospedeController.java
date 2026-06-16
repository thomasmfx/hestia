package hestia.hestiaBackEnd.controller;

import hestia.hestiaBackEnd.dto.CadastrarHospedeRequest;
import hestia.hestiaBackEnd.service.HospedeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import hestia.hestiaBackEnd.dto.HospedeResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/hospedes")
public class HospedeController {
    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService){
        this.hospedeService = hospedeService;
    }

    @PostMapping
    ResponseEntity<HospedeResponse> cadastrar(@Valid @RequestBody CadastrarHospedeRequest cadastrarHospedeRequest){
        HospedeResponse response = hospedeService.cadastrar(cadastrarHospedeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
