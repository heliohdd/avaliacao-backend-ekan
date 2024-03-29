package com.ekan.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ekan.backend.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.backend.domain.model.Beneficiario;
import com.ekan.backend.domain.model.Documento;
import com.ekan.backend.domain.repository.BeneficiarioRepository;
import com.ekan.backend.domain.repository.DocumentoRepository;

@Service
public class CadastroBeneficiarioService {

	@Autowired
	private BeneficiarioRepository beneficiarioRepository;
	
	@Autowired
	private DocumentoRepository documentoRepository;

	public Beneficiario salvar(Beneficiario beneficiario) {
		Long documentoId = beneficiario.getDocumentos().get(0).getId();
		Documento documento = documentoRepository.buscar(documentoId);
		
		if (documento == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de documento com código %d", documentoId));
		}
		
		return beneficiarioRepository.salvar(beneficiario);
	}
}
