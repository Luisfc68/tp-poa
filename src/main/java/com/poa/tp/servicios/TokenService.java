package com.poa.tp.servicios;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.poa.tp.entidades.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	private static TokenService instance;
	private final SecretKey tokenKey;
	private static final long DURACION_MS = 60000;
	private final String prefijo;
	
	private TokenService() {
		this.tokenKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		this.prefijo = "Bearer ";
	}
	
	public static TokenService getInstance() {
		if(instance == null)
			instance = new TokenService();
		return instance;
	}
	
	public SecretKey getTokenKey() {
		return tokenKey;
	}
	
	public String getPrefijo() {
		return prefijo;
	}
	
	public String crearToken(Usuario u) {
		
		String token = Jwts.builder()
						.setId(Integer.toString(u.getId()))
						.setSubject(u.getNombre())
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + DURACION_MS))
						.signWith(tokenKey)
						.compact();

		return prefijo + token;
	}
	
	public Claims parsearToken(String nombreHeader,HttpServletRequest request) {
		String token = request.getHeader(nombreHeader).replace(prefijo, "");
		return Jwts.parserBuilder()
				.setSigningKey(tokenKey)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean tieneToken(String nombreHeader,HttpServletRequest request) {
		String header = request.getHeader(nombreHeader);
		if (header != null && header.startsWith(prefijo))
			return true;
		return false;
	}
	
}
