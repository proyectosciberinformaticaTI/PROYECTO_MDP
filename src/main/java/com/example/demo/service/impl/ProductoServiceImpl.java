package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.tb_producto;
import com.example.demo.repo.IProductoRepo;
import com.example.demo.service.IProductoService;

@Service
public class ProductoServiceImpl extends CRUDImpl<tb_producto, String> implements IProductoService {

	@Autowired
	private IProductoRepo repo;

	@Override
	protected ReactiveMongoRepository<tb_producto, String> getRepo() {
		return repo;
	}
	
	

}