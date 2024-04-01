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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ekan.backend.domain.exception.EntidadeEmUsoException;
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
		return beneficiarioRepository.findAll();
	}

	@GetMapping(value = "/{beneficiarioId}")
	public ResponseEntity<Beneficiario> buscar(@PathVariable Long beneficiarioId) {
		Optional<Beneficiario> beneficiario = beneficiarioRepository.findById(beneficiarioId);

		if (beneficiario.isPresent()) {
			return ResponseEntity.ok(beneficiario.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Beneficiario adicionar(@RequestBody Beneficiario beneficiario) {
		return cadastroBeneficiario.salvar(beneficiario);
	}

	@PutMapping("/{beneficiarioId}")
	public ResponseEntity<Beneficiario> atualizar(@PathVariable Long beneficiarioId,
			@RequestBody Beneficiario beneficiario) {
		Optional<Beneficiario> beneficiarioAtual = beneficiarioRepository.findById(beneficiarioId);

		if (beneficiarioAtual.isPresent()) {
			BeanUtils.copyProperties(beneficiario, beneficiarioAtual.get(), "id");

			Beneficiario beneficiarioSalvo = cadastroBeneficiario.salvar(beneficiarioAtual.get());
			return ResponseEntity.ok(beneficiarioSalvo);
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{beneficiarioId}")
	public ResponseEntity<?> remover(@PathVariable Long beneficiarioId) {
		try {
			cadastroBeneficiario.excluir(beneficiarioId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

}
