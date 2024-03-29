package com.ekan.backend.domain.repository;

import java.util.List;

import com.ekan.backend.domain.model.Documento;

public interface DocumentoRepository {
	
	List<Documento> listar();
	Documento buscar(Long id);
	Documento salvar(Documento documento);
	void remover(Long id);

}
