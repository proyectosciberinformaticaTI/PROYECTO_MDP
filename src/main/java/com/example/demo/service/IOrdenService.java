package com.example.demo.service;

import com.example.demo.model.filtroDTO;
import com.example.demo.model.tb_orden;

import reactor.core.publisher.Flux;

public interface IOrdenService extends ICRUD<tb_orden, String>{

	
	
	Flux<tb_orden> obtenerFacturasPorFiltro(filtroDTO filtro);
}
