package com.example.demo.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.tb_cliente;

public interface IClienteRepo extends ReactiveMongoRepository<tb_cliente,String>{

	
	
	
	
}
