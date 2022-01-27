package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Service;

import com.example.demo.model.tb_menu;
import com.example.demo.repo.IMenuRepo;
import com.example.demo.service.IMenuService;

import reactor.core.publisher.Flux;


@Service
public class MenuServiceImpl extends CRUDImpl<tb_menu, String> implements IMenuService{

	@Autowired
	private IMenuRepo repo;

	@Override
	protected ReactiveMongoRepository<tb_menu, String> getRepo() {		
		return repo; 
	}
	
	@Override
	public Flux<tb_menu> obtenerMenus(String[] roles) {
		return repo.obtenerMenus(roles);
	}
}
