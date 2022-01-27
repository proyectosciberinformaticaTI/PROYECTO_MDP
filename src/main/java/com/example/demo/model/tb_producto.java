package com.example.demo.model;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
public class tb_producto {
	@Id
	private String id;

	@NotNull
	@NotEmpty
	@Size(min = 3)
	@Field(name = "nombre")
	private String nombre;

	@NotNull
	@Min(1)
	@Field(name = "precio")
	private Double precio;

	@NotNull
	@Field(name = "estado")
	private Boolean estado;

	@NotNull
	@Field(name = "categoria")
	private String categoria;

	
	
	
	

	public tb_producto() {
		super();
	}






	public String getId() {
		return id;
	}






	public void setId(String id) {
		this.id = id;
	}






	public String getNombre() {
		return nombre;
	}






	public void setNombre(String nombre) {
		this.nombre = nombre;
	}






	public Double getPrecio() {
		return precio;
	}






	public void setPrecio(Double precio) {
		this.precio = precio;
	}






	public Boolean getEstado() {
		return estado;
	}






	public void setEstado(Boolean estado) {
		this.estado = estado;
	}






	public String getCategoria() {
		return categoria;
	}






	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	
	

}
