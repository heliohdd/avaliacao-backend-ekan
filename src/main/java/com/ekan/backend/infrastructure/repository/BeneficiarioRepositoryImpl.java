package com.ekan.backend.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ekan.backend.domain.model.Beneficiario;
import com.ekan.backend.domain.repository.BeneficiarioRepository;

@Component
public class BeneficiarioRepositoryImpl implements BeneficiarioRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Beneficiario> listar() {
		return manager.createQuery("from Beneficiario", Beneficiario.class)
				.getResultList();
	}

	@Override
	public Beneficiario buscar(Long id) {
		return manager.find(Beneficiario.class, id);
	}

	@Transactional
	@Override
	public Beneficiario salvar(Beneficiario beneficiario) {
		return manager.merge(beneficiario);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Beneficiario beneficiario = buscar(id);
		
		if (beneficiario == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(beneficiario);
	}

}
