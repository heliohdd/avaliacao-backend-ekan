package com.ekan.backend.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ekan.backend.domain.model.Beneficiario;

@Repository
public interface BeneficiarioRepository extends JpaRepository<Beneficiario, Long> {

	List<Beneficiario> findTodasByNome(String nome);

	Optional<Beneficiario> findByNome(String nome);
}
