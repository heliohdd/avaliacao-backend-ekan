package com.ekan.backend.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ekan.backend.domain.exception.EntidadeEmUsoException;
import com.ekan.backend.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.backend.domain.model.Documento;
import com.ekan.backend.domain.repository.DocumentoRepository;

@Service
public class CadastroDocumentoService {

	@Autowired
	private DocumentoRepository documentoRepository;

	public Documento salvar(Documento documento) {
		Long documentoId = documento.getId();
		
		Documento documentoAtual = documentoRepository.findById(documentoId)
			.orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de documento com código %d", documentoId)));
		
		return documentoRepository.save(documentoAtual);
	}
	
	public void excluir(Long documentoId) {
		try {
			documentoRepository.deleteById(documentoId);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException (
					String.format("Não existe um cadastro de documento com código %d", documentoId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Documento de código %d não pode ser removido pois está em uso", documentoId));
		}
	}
}
