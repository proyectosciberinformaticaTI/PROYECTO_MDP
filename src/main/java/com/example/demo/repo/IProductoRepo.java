package com.example.demo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.tb_producto;

public interface IProductoRepo extends ReactiveMongoRepository<tb_producto, String> {

	
	
}
