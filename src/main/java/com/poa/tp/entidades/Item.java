package com.poa.tp.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name="item")
@Table(name="item")
public class Item {
	
	@JsonIgnore
	@EmbeddedId
	private IdItem id;
	
	@Column(name="cantidad")
	private int cantidad;
	
	@JsonIgnore
	@ManyToOne(optional=false,cascade=CascadeType.ALL)
	@MapsId("id_canje")
	@JoinColumn(name="id_canje")
	private Canje canje;
	
	@JsonIgnoreProperties({"stock","costo"})
	@ManyToOne(optional=false,cascade=CascadeType.ALL)
	@MapsId("id_producto")
	@JoinColumn(name="id_producto")
	private Producto producto;
	
	public Item() {}
	
	public Item(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public int getValor() {
		return producto.getCosto()*cantidad;
	}
	
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Canje getCanje() {
		return canje;
	}

	public void setCanje(Canje canje) {
		this.canje = canje;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	
	public IdItem getId() {
		return id;
	}
	
	public void setId(IdItem id) {
		this.id = id;
	}
	
	public String toString() {
		return "Item: "+producto.toString()+" - "+cantidad;
	}
	
}
