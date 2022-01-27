package com.example.demo.model;



import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "tb_cliente")
public class tb_cliente {
	@Id
	private String id;
	
	@NotNull
	@Size(min = 3)	
	@Field(name = "nombres")
	private String nombres;
	
	@NotNull
	@Size(min = 3)
	@Field(name = "apellidos")
	private String apellidos;
		
	@NotNull
	@Field(name = "fechaNac")
	private LocalDate fechaNac;	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(LocalDate fechaNac) {
		this.fechaNac = fechaNac;
	}

}
