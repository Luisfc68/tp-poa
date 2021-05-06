package com.poa.tp.entidades;

import java.util.Date;

import com.poa.tp.excepciones.NoStockException;
import com.poa.tp.excepciones.UsuarioPobreException;

public class Canje {
	
	private int id;
	private Date fecha;
	private Usuario usuario;
	private Item[] items;
	
	public Canje() {}
	
	public Canje(Usuario usuario, Item[] items) {
		this.usuario = usuario;
		this.items = items;
		for(Item i : items)
			i.setCanje(this);
	}
	
	public int getTotal() {
		int total = 0;
		for(Item i : items)
			total+= i.getValor();
		return total;
	}
	
	public boolean hayStock() {
		for(Item i : items)
			if(!i.getProducto().isStock())
				return false;
		return true;
	}
	
	public boolean canjear() throws NoStockException, UsuarioPobreException {
		if(!hayStock())
			throw new NoStockException(this);
		else if(getTotal()>usuario.getPuntos())
			throw new UsuarioPobreException(usuario);
		
		usuario.setPuntos(usuario.getPuntos()-getTotal());
		usuario.addCanje(this);
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}
	
	public String toString() {
		String msg = "\nCanje: \n";
		for(Item i : items)
			msg+= i.toString()+"\n";
		return msg;
	}
	
}
