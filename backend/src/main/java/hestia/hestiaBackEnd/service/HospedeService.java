package hestia.hestiaBackEnd.service;

import hestia.hestiaBackEnd.dto.*;
import hestia.hestiaBackEnd.entity.Hospede;
import hestia.hestiaBackEnd.entity.StatusHospede;
import hestia.hestiaBackEnd.repository.HospedeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class HospedeService {
    private final HospedeRepository hospedeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public HospedeService(HospedeRepository hospedeRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService) {
        this.hospedeRepository = hospedeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    public HospedeResponse cadastrar(CadastrarHospedeRequest cadastrarHospedeRequest){
        if (hospedeRepository.findByCpf(cadastrarHospedeRequest.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado no sistema.");
        }

        if (!Boolean.TRUE.equals(cadastrarHospedeRequest.getAceitouTermos())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "É necessário aceitar os termos de uso.");
        }

        String senhaCriptografada = bCryptPasswordEncoder.encode(cadastrarHospedeRequest.getSenha());

        Hospede hospede = new Hospede();

        hospede.setNome(cadastrarHospedeRequest.getNome());
        hospede.setCpf(cadastrarHospedeRequest.getCpf());
        hospede.setDataNascimento(cadastrarHospedeRequest.getDtNascimento());
        hospede.setEmail(cadastrarHospedeRequest.getEmail());
        hospede.setSenha(senhaCriptografada);
        hospede.setDdd(cadastrarHospedeRequest.getTelefone().getDdd());
        hospede.setTelefone(cadastrarHospedeRequest.getTelefone().getNumero());
        hospede.setLogradouro(cadastrarHospedeRequest.getEndereco().getLogradouro());
        hospede.setNumero(cadastrarHospedeRequest.getEndereco().getNumero());
        hospede.setComplemento(cadastrarHospedeRequest.getEndereco().getComplemento());
        hospede.setBairro(cadastrarHospedeRequest.getEndereco().getBairro());
        hospede.setCep(cadastrarHospedeRequest.getEndereco().getCep());
        hospede.setCidade(cadastrarHospedeRequest.getEndereco().getCidade());
        hospede.setEstado(cadastrarHospedeRequest.getEndereco().getEstado());
        hospede.setAceiteTermos(cadastrarHospedeRequest.getAceitouTermos());
        hospede.setDataAceiteTermos(LocalDateTime.now());
        hospede.setStatusAtividade(StatusHospede.ATIVO);

        Hospede hospedeSalvo = hospedeRepository.save(hospede);

        TelefoneResponse telefoneResponse = new TelefoneResponse();
        telefoneResponse.setDdd(hospedeSalvo.getDdd());
        telefoneResponse.setNumero(hospedeSalvo.getTelefone());

        EnderecoResponse enderecoResponse = new EnderecoResponse();
        enderecoResponse.setLogradouro(hospedeSalvo.getLogradouro());
        enderecoResponse.setNumero(hospedeSalvo.getNumero());
        enderecoResponse.setComplemento(hospedeSalvo.getComplemento());
        enderecoResponse.setBairro(hospedeSalvo.getBairro());
        enderecoResponse.setCep(hospedeSalvo.getCep());
        enderecoResponse.setCidade(hospedeSalvo.getCidade());
        enderecoResponse.setEstado(hospedeSalvo.getEstado());

        return new HospedeResponse(
                hospedeSalvo.getId(),
                hospedeSalvo.getNome(),
                hospedeSalvo.getCpf(),
                hospedeSalvo.getDataNascimento(),
                hospedeSalvo.getEmail(),
                telefoneResponse,
                enderecoResponse
        );
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Hospede hospede = hospedeRepository.findByCpf(loginRequest.getCpf())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos."));

        if (!bCryptPasswordEncoder.matches(loginRequest.getSenha(), hospede.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos.");
        }

        String token = jwtService.gerarToken(hospede);
        return new LoginResponse(token, "Bearer");
    }

    public HospedeResponse buscarPorId(java.util.UUID id) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado."));

        TelefoneResponse telefoneResponse = new TelefoneResponse();
        telefoneResponse.setDdd(hospede.getDdd());
        telefoneResponse.setNumero(hospede.getTelefone());

        EnderecoResponse enderecoResponse = new EnderecoResponse();
        enderecoResponse.setLogradouro(hospede.getLogradouro());
        enderecoResponse.setNumero(hospede.getNumero());
        enderecoResponse.setComplemento(hospede.getComplemento());
        enderecoResponse.setBairro(hospede.getBairro());
        enderecoResponse.setCep(hospede.getCep());
        enderecoResponse.setCidade(hospede.getCidade());
        enderecoResponse.setEstado(hospede.getEstado());

        return new HospedeResponse(
                hospede.getId(),
                hospede.getNome(),
                hospede.getCpf(),
                hospede.getDataNascimento(),
                hospede.getEmail(),
                telefoneResponse,
                enderecoResponse
        );
    }

    public HospedeResponse atualizar(java.util.UUID id, AtualizarHospedeRequest request) {
        Hospede hospede = hospedeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóspede não encontrado."));

        hospede.setNome(request.getNome());
        hospede.setDataNascimento(request.getDtNascimento());
        hospede.setEmail(request.getEmail());
        hospede.setDdd(request.getTelefone().getDdd());
        hospede.setTelefone(request.getTelefone().getNumero());
        hospede.setLogradouro(request.getEndereco().getLogradouro());
        hospede.setNumero(request.getEndereco().getNumero());
        hospede.setComplemento(request.getEndereco().getComplemento());
        hospede.setBairro(request.getEndereco().getBairro());
        hospede.setCep(request.getEndereco().getCep());
        hospede.setCidade(request.getEndereco().getCidade());
        hospede.setEstado(request.getEndereco().getEstado());

        Hospede hospedeAtualizado = hospedeRepository.save(hospede);

        TelefoneResponse telefoneResponse = new TelefoneResponse();
        telefoneResponse.setDdd(hospedeAtualizado.getDdd());
        telefoneResponse.setNumero(hospedeAtualizado.getTelefone());

        EnderecoResponse enderecoResponse = new EnderecoResponse();
        enderecoResponse.setLogradouro(hospedeAtualizado.getLogradouro());
        enderecoResponse.setNumero(hospedeAtualizado.getNumero());
        enderecoResponse.setComplemento(hospedeAtualizado.getComplemento());
        enderecoResponse.setBairro(hospedeAtualizado.getBairro());
        enderecoResponse.setCep(hospedeAtualizado.getCep());
        enderecoResponse.setCidade(hospedeAtualizado.getCidade());
        enderecoResponse.setEstado(hospedeAtualizado.getEstado());

        return new HospedeResponse(
                hospedeAtualizado.getId(),
                hospedeAtualizado.getNome(),
                hospedeAtualizado.getCpf(),
                hospedeAtualizado.getDataNascimento(),
                hospedeAtualizado.getEmail(),
                telefoneResponse,
                enderecoResponse
        );
    }
}
