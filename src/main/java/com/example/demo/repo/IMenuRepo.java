package com.example.demo.repo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.tb_menu;
import reactor.core.publisher.Flux;


public interface IMenuRepo extends ReactiveMongoRepository<tb_menu, String>{

	
	@Query("{'roles' : { $in: ?0 }}")
	Flux<tb_menu> obtenerMenus(String[] roles);
	
}
