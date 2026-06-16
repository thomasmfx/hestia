package hestia.hestiaBackEnd.service;

import hestia.hestiaBackEnd.dto.CadastrarHospedeRequest;
import hestia.hestiaBackEnd.dto.EnderecoResponse;
import hestia.hestiaBackEnd.dto.HospedeResponse;
import hestia.hestiaBackEnd.dto.TelefoneResponse;
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

    public HospedeService(HospedeRepository hospedeRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.hospedeRepository = hospedeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
}
