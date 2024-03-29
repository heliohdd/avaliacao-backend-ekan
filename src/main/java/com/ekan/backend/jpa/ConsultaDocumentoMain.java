package com.ekan.backend.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.ekan.backend.BackendEkanApiApplication;
import com.ekan.backend.domain.model.Documento;
import com.ekan.backend.domain.repository.DocumentoRepository;

public class ConsultaDocumentoMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = 
				new SpringApplicationBuilder(BackendEkanApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		DocumentoRepository documentoRepository = applicationContext.getBean(DocumentoRepository.class);
		
		List<Documento> todosDocumentos = documentoRepository.listar();
		
		for (Documento documento : todosDocumentos) {
			System.out.printf("%s - %s\n", documento.getTipoDocumento(), documento.getDescricao());
		}
	}
}
