package com.ekan.backend.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ekan.backend.domain.model.Documento;
import com.ekan.backend.domain.repository.DocumentoRepository;

@Component
public class DocumentoRepositoryImpl implements DocumentoRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Documento> listar() {
		return manager.createQuery("from Documento", Documento.class)
				.getResultList();
	}

	@Override
	public Documento buscar(Long id) {
		return manager.find(Documento.class, id);
	}

	@Transactional
	@Override
	public Documento salvar(Documento documento) {
		return manager.merge(documento);
	}

	@Transactional
	@Override
	public void remover(Long id) {
		Documento documento = buscar(id);
		if (documento == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(documento);
	}

}
