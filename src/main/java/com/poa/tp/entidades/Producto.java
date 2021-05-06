package com.poa.tp.entidades;

public class Producto {
	
	private int id;
	private int costo;
	private String descripcion;
	private boolean stock;
	
	public Producto() {}

	public Producto(int id, int costo, String descripcion, boolean stock) {
		this.id = id;
		this.costo = costo;
		this.descripcion = descripcion;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}
	
	
}
