package com.poa.tp.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="producto")
@Table(name="producto")
public class Producto implements Cloneable{
	
	@Id
	@GeneratedValue
	@Column(name="id_producto")
	private int id;
	
	@Column(name="costo")
	private int costo;
	
	@Column(name="descripcion")
	private String descripcion;
	
	@Column(name="stock")
	private boolean stock;
	
	public Producto() {}

	public Producto(int id, int costo, String descripcion, boolean stock) {
		this.id = id;
		setCosto(costo);
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
		if(costo<0)
			throw new IllegalArgumentException();
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
	
	public Producto clone() {
		Producto copia = new Producto();
		copia.setCosto(this.costo);
		copia.setId(this.id);
		copia.setDescripcion(this.descripcion);
		copia.setStock(this.stock);
		return copia;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Producto) {
			Producto p = (Producto)o;
			if(p.getCosto() == costo && p.isStock() == stock && p.getDescripcion().equals(descripcion) && p.getId() == id)
				return true;
		}
		return false;
	}
	
	public String toString() {
		return getDescripcion();
	}
	
	public int hashCode() {
		return id;
	}
	
}
