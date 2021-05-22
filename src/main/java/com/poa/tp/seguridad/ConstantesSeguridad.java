package com.poa.tp.seguridad;

import java.util.Base64;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


public enum ConstantesSeguridad {
	
	RUTA_LOGIN("/usuario/login"),
	TOKEN_HEADER("Authorization"),
	TOKEN_KEY(Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded())),
	TOKEN_PREFIX("Bearer ");
	
	private String valor;
	
	private ConstantesSeguridad(String valor){
		this.valor = valor;
	}
	
	public String getValor() {
		return valor;
	}

}
