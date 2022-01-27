package com.example.demo.repo;

import java.time.LocalDate;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.demo.model.tb_orden;
import com.example.demo.model.tb_producto;

import reactor.core.publisher.Flux;

public interface IOrdenRepo extends ReactiveMongoRepository<tb_orden, String> {

	
	@Query("{ 'cliente' : { _id : ?0 }}")
	Flux<tb_orden> obtenerFacturasPorCliente(String idCliente);
	
	@Query("{'creadoEn' : { $gte: ?0, $lt: ?1} }")	
	Flux<tb_orden> obtenerFacturasPorFecha(LocalDate fechaInicio, LocalDate fechaFin);
}
