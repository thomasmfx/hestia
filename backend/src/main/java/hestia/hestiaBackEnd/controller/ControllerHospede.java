package hestia.hestiaBackEnd.controller;

import hestia.hestiaBackEnd.domain.Endereco;
import hestia.hestiaBackEnd.domain.EntidadeDominio;
import hestia.hestiaBackEnd.domain.Hospede;
import hestia.hestiaBackEnd.domain.Telefone;
import hestia.hestiaBackEnd.dto.AlterarHospedeRequest;
import hestia.hestiaBackEnd.dto.CadastrarHospedeRequest;
import hestia.hestiaBackEnd.dto.EnderecoRequest;
import hestia.hestiaBackEnd.dto.EnderecoResponse;
import hestia.hestiaBackEnd.dto.ErroResponse;
import hestia.hestiaBackEnd.dto.ErroValidacaoResponse;
import hestia.hestiaBackEnd.dto.HospedeResponse;
import hestia.hestiaBackEnd.dto.TelefoneRequest;
import hestia.hestiaBackEnd.dto.TelefoneResponse;
import hestia.hestiaBackEnd.fachada.IFachada;
import hestia.hestiaBackEnd.strategy.ValidarCPF;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/hospedes")
public class ControllerHospede {

    private final IFachada fachada;

    public ControllerHospede(IFachada fachada) {
        this.fachada = fachada;
    }

    @PostMapping
    public ResponseEntity<?> definirHospede(@Valid @RequestBody CadastrarHospedeRequest request) {
        Hospede hospede = new Hospede();
        hospede.setAtivo(true);
        hospede.setNome(request.getNome());
        hospede.setCpf(request.getCpf());
        hospede.setDtNascimento(request.getDtNascimento());
        hospede.setEmail(request.getEmail());
        hospede.setSenha(request.getSenha());
        hospede.setAceitouTermos(Boolean.TRUE.equals(request.getAceitouTermos()));
        hospede.setEndereco(montarEndereco(new Endereco(), request.getEndereco()));
        hospede.setTelefone(montarTelefone(new Telefone(), request.getTelefone()));

        String erro = fachada.salvar(hospede);
        if (erro != null) {
            return mapearErro(erro);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(hospede));
    }

    @GetMapping("/me")
    public ResponseEntity<HospedeResponse> buscarPerfil(Authentication authentication) {
        Hospede hospede = carregarAutenticado(authentication);
        return ResponseEntity.ok(toResponse(hospede));
    }

    @PutMapping("/me")
    public ResponseEntity<?> alterar(Authentication authentication,
                                     @Valid @RequestBody AlterarHospedeRequest request) {
        Hospede hospede = carregarAutenticado(authentication);
        hospede.setNome(request.getNome());
        hospede.setDtNascimento(request.getDtNascimento());
        hospede.setEmail(request.getEmail());
        montarEndereco(hospede.getEndereco(), request.getEndereco());
        montarTelefone(hospede.getTelefone(), request.getTelefone());

        String erro = fachada.alterar(hospede);
        if (erro != null) {
            return mapearErro(erro);
        }
        return ResponseEntity.ok(toResponse(hospede));
    }

    @PatchMapping("/me/inativar")
    public ResponseEntity<Void> inativar(Authentication authentication) {
        Hospede hospede = carregarAutenticado(authentication);
        fachada.excluir(hospede);
        return ResponseEntity.noContent().build();
    }

    // ----- helpers -----

    private Hospede carregarAutenticado(Authentication authentication) {
        Integer id = Integer.parseInt(authentication.getName());
        Hospede filtro = new Hospede();
        filtro.setId(id);
        EntidadeDominio[] encontrados = fachada.consultar(filtro);
        if (encontrados.length == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado.");
        }
        return (Hospede) encontrados[0];
    }

    private Endereco montarEndereco(Endereco endereco, EnderecoRequest request) {
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCep(apenasDigitos(request.getCep()));
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        return endereco;
    }

    private Telefone montarTelefone(Telefone telefone, TelefoneRequest request) {
        telefone.setDdd(apenasDigitos(request.getDdd()));
        telefone.setNumero(apenasDigitos(request.getNumero()));
        return telefone;
    }

    private String apenasDigitos(String valor) {
        return valor == null ? null : valor.replaceAll("\\D", "");
    }

    private HospedeResponse toResponse(Hospede hospede) {
        TelefoneResponse telefoneResponse = new TelefoneResponse();
        telefoneResponse.setDdd(hospede.getTelefone().getDdd());
        telefoneResponse.setNumero(hospede.getTelefone().getNumero());

        Endereco endereco = hospede.getEndereco();
        EnderecoResponse enderecoResponse = new EnderecoResponse();
        enderecoResponse.setLogradouro(endereco.getLogradouro());
        enderecoResponse.setNumero(endereco.getNumero());
        enderecoResponse.setComplemento(endereco.getComplemento());
        enderecoResponse.setBairro(endereco.getBairro());
        enderecoResponse.setCep(endereco.getCep());
        enderecoResponse.setCidade(endereco.getCidade());
        enderecoResponse.setEstado(endereco.getEstado());

        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome(),
                hospede.getCpf(),
                hospede.getDtNascimento(),
                hospede.getEmail(),
                hospede.isAtivo(),
                hospede.isAceitouTermos(),
                telefoneResponse,
                enderecoResponse,
                hospede.getDtCadastro(),
                hospede.getDtAlteracao()
        );
    }

    private ResponseEntity<?> mapearErro(String mensagem) {
        if (ValidarCPF.CPF_JA_CADASTRADO.equals(mensagem)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErroResponse(HttpStatus.CONFLICT.value(), "Conflito", mensagem));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErroValidacaoResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "Dados inválidos",
                        mensagem,
                        List.of()));
    }
}
