package com.ekan.backend.domain.repository;

import java.util.List;

import com.ekan.backend.domain.model.Beneficiario;

public interface BeneficiarioRepository {
	
	List<Beneficiario> listar();
	Beneficiario buscar(Long id);
	Beneficiario salvar(Beneficiario beneficiario);
	void remover(Long id);

}
