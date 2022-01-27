package com.example.demo.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.tb_menu;
import com.example.demo.service.IMenuService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/menus")
public class menuController {
	
	@Autowired	
	private IMenuService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<tb_menu>>> listar(){
		return ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.map(Authentication::getAuthorities)
				.map(roles -> {
					String rolesString = roles.stream().map(Object::toString).collect(Collectors.joining(",")); // "ADMIN,USER"
					String[] strings = rolesString.split(",");
					return service.obtenerMenus(strings);
				})
				.flatMap(fx -> {
					return Mono.just(ResponseEntity.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(fx));
				});	
	}
}
