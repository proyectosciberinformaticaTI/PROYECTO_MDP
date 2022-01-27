package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "tb_orden")
public class tb_orden {

	
	@Id
	private String id;

	@Field(name = "descripcion")
	private String descripcion;

	@Field(name = "observacion")
	private String observacion;
	
	
	
	@Field(name = "cantidad")
	private Integer cantidad;

	@Field(name = "estado")
	private String estado;
	
	@Field(name = "cliente")
	private tb_cliente cliente;

	@Field(name = "items")
	private List<tb_ordenitem> items;

	private LocalDateTime creadoEn = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public tb_cliente getCliente() {
		return cliente;
	}

	public void setCliente(tb_cliente cliente) {
		this.cliente = cliente;
	}

	public List<tb_ordenitem> getItems() {
		return items;
	}

	public void setItems(List<tb_ordenitem> items) {
		this.items = items;
	}

	public LocalDateTime getCreadoEn() {
		return creadoEn;
	}

	public void setCreadoEn(LocalDateTime creadoEn) {
		this.creadoEn = creadoEn;
	}


	
	
	
	
	
	
	
	
}
