package com.example.demo.controller;

import org.springframework.stereotype.Controller;

import com.example.demo.model.tb_orden;
import com.example.demo.pagination.PageSupport;
import com.example.demo.service.IOrdenService;

import java.net.URI;

import javax.validation.Valid;

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




@RestController
@RequestMapping("/ordenes")
public class ordenesController {

	
	@Autowired
	private IOrdenService service;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<tb_orden>>> listar(){
		Flux<tb_orden> fxFacturas = service.listar();
		
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fxFacturas)
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<tb_orden>> listarPorId(@PathVariable("id") String id){
		return service.listarPorId(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
						) 
				.defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public Mono<ResponseEntity<tb_orden>> registrar(@Valid @RequestBody tb_orden factura, final ServerHttpRequest req){
		return service.registrar(factura)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
				);
	}
	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<tb_orden>> modificar(@PathVariable("id") String id, @Valid @RequestBody tb_orden factura){
		
		Mono<tb_orden> monoBody = Mono.just(factura);
		Mono<tb_orden> monoBD = service.listarPorId(id);
		
		return monoBD
				.zipWith(monoBody, (bd, f) -> {
					bd.setId(id);
					bd.setCliente(f.getCliente());
					bd.setCantidad(f.getCantidad());
					bd.setEstado(f.getEstado());
					bd.setDescripcion(f.getDescripcion());
					bd.setObservacion(f.getObservacion());
					bd.setItems(f.getItems());
					return bd;
				})
				.flatMap(service::modificar)
				.map(pl -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pl))
				.defaultIfEmpty(new ResponseEntity<tb_orden>(HttpStatus.NOT_FOUND));
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
	public Mono<ResponseEntity<PageSupport<tb_orden>>> listarPagebale(
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
