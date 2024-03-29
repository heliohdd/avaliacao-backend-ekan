package com.ekan.backend.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.backend.domain.exception.EntidadeNaoEncontradaException;
import com.ekan.backend.domain.model.Beneficiario;
import com.ekan.backend.domain.repository.BeneficiarioRepository;
import com.ekan.backend.domain.service.CadastroBeneficiarioService;

@RestController
@RequestMapping(value = "/beneficiarios")
public class BeneficiarioController {

	@Autowired
	private BeneficiarioRepository beneficiarioRepository;

	@Autowired
	private CadastroBeneficiarioService cadastroBeneficiario;

	@GetMapping
	public List<Beneficiario> listar() {
		return beneficiarioRepository.listar();
	}

	@GetMapping(value = "/{beneficiarioId}")
	public ResponseEntity<Beneficiario> buscar(@PathVariable Long beneficiarioId) {
		Beneficiario beneficiario = beneficiarioRepository.buscar(beneficiarioId);

		if (beneficiario != null) {
			return ResponseEntity.ok(beneficiario);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Beneficiario beneficiario){
		try {
			beneficiario = cadastroBeneficiario.salvar(beneficiario);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(beneficiario);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{beneficiarioId}")
	public ResponseEntity<?> atualizar(@PathVariable Long beneficiarioId,
			@RequestBody Beneficiario beneficiario){
		try {
			Beneficiario beneficiarioAtual = beneficiarioRepository.buscar(beneficiarioId);
			
			if (beneficiarioAtual != null) {
				BeanUtils.copyProperties(beneficiario, beneficiarioAtual, "id");
				
				beneficiarioAtual = cadastroBeneficiario.salvar(beneficiarioAtual);
				return ResponseEntity.ok(beneficiarioAtual);
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}

}
