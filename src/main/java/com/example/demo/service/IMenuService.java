package com.example.demo.service;

import com.example.demo.model.tb_menu;

import reactor.core.publisher.Flux;

public interface IMenuService extends ICRUD<tb_menu, String>{
	
	Flux<tb_menu> obtenerMenus(String[] rol);
}
