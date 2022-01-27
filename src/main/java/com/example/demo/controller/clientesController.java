package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Map;

import javax.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.tb_cliente;
import com.example.demo.service.IClienteService;

@RestController
@RequestMapping("/clientes")
public class clientesController {

	
	@Autowired
	private IClienteService service;

	
	@GetMapping
	public Mono<ResponseEntity<Flux<tb_cliente>>> listar(){
		Flux<tb_cliente> fxClientes = service.listar();
		
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fxClientes)
				);
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<tb_cliente>> listarPorId(@PathVariable("id") String id){
		return service.listarPorId(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
						)
				.defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@PostMapping
	public Mono<ResponseEntity<tb_cliente>> registrar(@Valid @RequestBody tb_cliente cliente, final ServerHttpRequest req){
		return service.registrar(cliente)
				.map(p -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
				);
	}
	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<tb_cliente>> modificar(@PathVariable("id") String id, @Valid @RequestBody tb_cliente cliente){
		
		Mono<tb_cliente> monoBody = Mono.just(cliente);
		Mono<tb_cliente> monoBD = service.listarPorId(id);
		
		return monoBD
				.zipWith(monoBody, (bd, cl) -> {
					bd.setId(id);
					bd.setNombres(cl.getNombres());
					bd.setApellidos(cl.getApellidos());
					bd.setFechaNac(cl.getFechaNac());
					return bd;
				})
				.flatMap(service::modificar)
				.map(pl -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pl))
				.defaultIfEmpty(new ResponseEntity<tb_cliente>(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable("id") String id){
		return service.listarPorId(id)
				.flatMap(p -> {
					return service.eliminar(p.getId()) //Mono<Void>
							.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));					
				})
				.defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
}
