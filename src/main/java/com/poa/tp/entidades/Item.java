package com.poa.tp.entidades;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity(name="item")
@Table(name="item")
public class Item implements Cloneable{
	
	@JsonIgnore
	@EmbeddedId
	private IdItem id;
	
	@Column(name="cantidad")
	private int cantidad;
	
	@JsonIgnore
	@ManyToOne(optional=false)
	@MapsId("idCanje")
	@JoinColumn(name="id_canje")
	private Canje canje;
	
	@ManyToOne(optional=false)
	@MapsId("idProducto")
	@JoinColumn(name="id_producto")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private Producto producto;
	
	public Item() {}
	
	public Item(Producto producto, int cantidad) {
		this.id = new IdItem();
		id.setIdProducto(producto.getId());
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
		id.setIdCanje(canje.getId());
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
	
	public int hashCode() {
		return id.hashCode();
	}
	
	public Item clone() {
		Item copia = new Item();
		copia.setProducto(this.producto);
		copia.setId(this.id.clone());
		copia.setCanje(this.canje);
		copia.setCantidad(this.cantidad);
		return copia;
	}
	
}
