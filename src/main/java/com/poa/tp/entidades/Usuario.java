package com.poa.tp.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity(name="usuario")
@Table(name="usuario")
public class Usuario implements Cloneable{
	
	@Id
	@GeneratedValue
	@Column(name="id_usuario")
	private int id;
	
	@Column(name="nombre")
	private String nombre;
	
	@JsonIgnore
	@Column(name="contrasena")
	private String contrasena;
	
	@Column(name="puntos")
	private int puntos;
	
	@Column(name="correo")
	private String correo;
	
	@JsonIgnoreProperties({"items"})
	@OneToMany(mappedBy="usuario",fetch=FetchType.EAGER)
	private Set<Canje> canjes;
	
	public Usuario(){
		this.setCanjes(new HashSet<Canje>());
	}

	public Usuario(int id, String nombre, String contrasena, int puntos, String correo) {
		this();
		this.id = id;
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.puntos = puntos;
		this.correo = correo;
	}
	
	public void addCanje(Canje c) {
		this.canjes.add(c);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Set<Canje> getCanjes() {
		return canjes;
	}

	public void setCanjes(Set<Canje> canjes) {
		this.canjes = canjes;
	}
	
	public String toString() {
		return nombre+": "+puntos;
	}
	
	public Usuario clone() {
		Usuario copia = new Usuario();
		copia.setCanjes(this.canjes);
		copia.setContrasena(this.contrasena);
		copia.setCorreo(this.correo);
		copia.setId(this.id);
		copia.setNombre(this.nombre);
		copia.setPuntos(this.puntos);
		return copia;
	}
	
	public int hashCode() {
		return id;
	}
	
}
