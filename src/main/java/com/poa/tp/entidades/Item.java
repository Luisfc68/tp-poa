package com.poa.tp.entidades;

public class Item {
	
	private int cantidad;
	private Canje canje;
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
	
}
