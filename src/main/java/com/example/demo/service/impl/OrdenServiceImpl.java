package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.filtroDTO;
import com.example.demo.model.tb_orden;
import com.example.demo.repo.IOrdenRepo;
import com.example.demo.service.IOrdenService;

import reactor.core.publisher.Flux;

@Service
public class OrdenServiceImpl extends CRUDImpl<tb_orden, String> implements IOrdenService{

	
	@Autowired
	private IOrdenRepo repo;
	

	@Override
	protected ReactiveMongoRepository<tb_orden, String> getRepo() {		
		return repo;
	}
	
	
	
	@Override
	public Flux<tb_orden> obtenerFacturasPorFiltro(filtroDTO filtro) {
		String criterio = filtro.getIdCliente() != null ? "C" : "O";
		
		if(criterio.equalsIgnoreCase("C")) {
			return repo.obtenerFacturasPorCliente(filtro.getIdCliente());
		}else {
			return repo.obtenerFacturasPorFecha(filtro.getFechaInicio(), filtro.getFechaFin());
		}
	}
}
