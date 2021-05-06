package com.poa.tp.excepciones;

import com.poa.tp.entidades.Canje;

@SuppressWarnings("serial")
public class NoStockException extends Exception {
	
	public NoStockException(Canje c){
		super("No se puede realizar el canje "+c.toString()+" porque uno o varios de sus productos no tienen stock.");
	}
}
