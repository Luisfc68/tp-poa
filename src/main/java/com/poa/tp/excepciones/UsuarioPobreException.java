package com.poa.tp.excepciones;

import com.poa.tp.entidades.Usuario;

@SuppressWarnings("serial")
public class UsuarioPobreException extends Exception{
	
	public UsuarioPobreException(Usuario u) {
		super("El usuario "+u.getNombre()+" no tiene suficientes puntos para realizar el canje.");
	}
	
}
