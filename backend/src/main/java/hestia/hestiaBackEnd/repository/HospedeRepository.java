package hestia.hestiaBackEnd.repository;

import java.util.Optional;
import hestia.hestiaBackEnd.entity.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HospedeRepository extends JpaRepository<Hospede, UUID> {
    Optional<Hospede> findByCpf(String cpf);
}
