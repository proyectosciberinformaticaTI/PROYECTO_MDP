package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.tb_cliente;
import com.example.demo.model.tb_producto;
import com.example.demo.repo.IClienteRepo;
import com.example.demo.service.IClienteService;
import com.example.demo.service.IProductoService;


@Service
public class ClienteServiceImpl extends CRUDImpl<tb_cliente, String> implements IClienteService {

	@Autowired
	private IClienteRepo repo;

	@Override
	protected ReactiveMongoRepository<tb_cliente, String> getRepo() {		
		return repo;
	}
	
	
}
