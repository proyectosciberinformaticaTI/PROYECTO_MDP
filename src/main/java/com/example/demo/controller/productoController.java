package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.tb_producto;
import com.example.demo.pagination.PageSupport;
import com.example.demo.service.IProductoService;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@RestController
@RequestMapping("/producto")
public class productoController {

	
	private static final Logger log = LoggerFactory.getLogger(productoController.class);

	
	
	@Autowired
	private IProductoService service;
	
	
	
	
	
	
	
	
	
	
	@GetMapping
	public Mono<ResponseEntity<Flux<tb_producto>>> listar(){
	
		Flux<tb_producto> fxPlatos = service.listar();
		
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fxPlatos)
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<tb_producto>> listarPorId(@PathVariable("id") String id){
		return service.listarPorId(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
						)
				.defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public Mono<ResponseEntity<tb_producto>> registrar(@Valid @RequestBody tb_producto producto, final ServerHttpRequest req){

		return service.registrar(producto)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
				);
	}
	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<tb_producto>> modificar(@PathVariable("id") String id, @Valid @RequestBody tb_producto producto){
		
		Mono<tb_producto> monoBody = Mono.just(producto);
		Mono<tb_producto> monoBD = service.listarPorId(id);
		
		return monoBD
				.zipWith(monoBody, (bd, pl) -> {
					bd.setId(id);
					bd.setNombre(pl.getNombre());
					bd.setPrecio(pl.getPrecio());
					bd.setEstado(pl.getEstado());
					bd.setCategoria(pl.getCategoria());
					return bd;
				})
				.flatMap(service::modificar)
				.map(pl -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pl))
				.defaultIfEmpty(new ResponseEntity<tb_producto>(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
		return service.listarPorId(id)
				.flatMap(p -> {
					return service.eliminar(p.getId())
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));					
				})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	

	
	
	
	@GetMapping("/pageable")
	public Mono<ResponseEntity<PageSupport<tb_producto>>> listarPagebale(
			@RequestParam(name = "page", defaultValue = "0") int page,
		    @RequestParam(name = "size", defaultValue = "10") int size
			){
	
		Pageable pageRequest = PageRequest.of(page, size);
		
		return service.listarPage(pageRequest)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)	
						)
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
	
	
	
}
