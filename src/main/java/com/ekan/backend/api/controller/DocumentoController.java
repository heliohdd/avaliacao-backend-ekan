package com.ekan.backend.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.backend.domain.exception.EntidadeEmUsoException;
import com.ekan.backend.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.backend.domain.model.Documento;
import com.ekan.backend.domain.repository.DocumentoRepository;
import com.ekan.backend.domain.service.CadastroDocumentoService;

@RestController
@RequestMapping(value = "/documentos")
public class DocumentoController {

	@Autowired
	private DocumentoRepository documentoRepository;

	@Autowired
	private CadastroDocumentoService cadastroDocumento;

	@GetMapping
	public List<Documento> listar() {
		return documentoRepository.findAll();
	}

	@GetMapping(value = "/{documentoId}")
	public ResponseEntity<Documento> buscar(@PathVariable Long documentoId) {
		Optional<Documento> documento = documentoRepository.findById(documentoId);

		if (documento.isPresent()) {
			return ResponseEntity.ok(documento.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Documento documento) {
		try {
			documento = cadastroDocumento.salvar(documento);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(documento);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

	@PutMapping("/{documentoId}")
	public ResponseEntity<Documento> atualizar(@PathVariable Long documentoId, 
			@RequestBody Documento documento) {
		Optional<Documento> documentoAtual = documentoRepository.findById(documentoId);

		if (documentoAtual.isPresent()) {
			BeanUtils.copyProperties(documento, documentoAtual.get(), "id");

			Documento documentoSalvo = cadastroDocumento.salvar(documentoAtual.get());
			return ResponseEntity.ok(documentoSalvo);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{documentoId}")
	public ResponseEntity<Documento> remover(@PathVariable Long documentoId) {
		try {
			cadastroDocumento.excluir(documentoId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
