package com.ekan.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ekan.backend.domain.exception.EntidadeEmUsoException;
import com.ekan.backend.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.backend.domain.model.Beneficiario;
import com.ekan.backend.domain.repository.BeneficiarioRepository;

@Service
public class CadastroBeneficiarioService {

	@Autowired
	private BeneficiarioRepository beneficiarioRepository;
	
	public Beneficiario salvar(Beneficiario beneficiario) {
		return beneficiarioRepository.save(beneficiario);
	}
	
	public void excluir(Long beneficiarioId) {
		try {
			beneficiarioRepository.deleteById(beneficiarioId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de beneficiário com código %d", beneficiarioId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Beneficiário de código %d não pode ser removida, pois está em uso", beneficiarioId));
		}
	}
}
