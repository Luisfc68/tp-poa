package com.poa.tp.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class IdItem implements Serializable,Cloneable{

	@Column(name="id_producto",updatable=false)
	private int idProducto;
	
	@Column(name="id_canje",updatable=false)
	private int idCanje;
	
	
	//Se sobrescriben equals y hashcode porque como esta clase actua como id hibernate necesita una forma de compararla bien definida
	
	public boolean equals(Object o) {
		if(o instanceof IdItem) {
			IdItem idComparado = (IdItem)o;
			if(idComparado.idCanje == this.idCanje && idComparado.idProducto == this.idProducto)
				return true;
		}
		return false;
	}
	
	//Receta para el hashcode sacada del libro effective java
	public int hashCode() {
		int resultado;
		
		resultado = Integer.hashCode(idProducto);
		resultado += 31*resultado + Integer.hashCode(idCanje);
		
		return resultado;
	}
	
	public String toString() {
		return "canje = "+idCanje+" prod = "+idProducto;
	}
	
	public IdItem clone() {
		IdItem copia = new IdItem();
		copia.setIdCanje(this.idCanje);
		copia.setIdProducto(this.idProducto);
		return copia;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getIdCanje() {
		return idCanje;
	}

	public void setIdCanje(int idCanje) {
		this.idCanje = idCanje;
	}
	
}
