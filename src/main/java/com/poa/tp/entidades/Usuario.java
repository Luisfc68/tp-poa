package com.poa.tp.entidades;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private int id;
	private String nombre;
	private String contrasena;
	private int puntos;
	private String correo;
	private List<Canje> canjes;
	
	public Usuario(){
		this.setCanjes(new ArrayList<Canje>());
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

	public List<Canje> getCanjes() {
		return canjes;
	}

	public void setCanjes(List<Canje> canjes) {
		this.canjes = canjes;
	}
	
}
