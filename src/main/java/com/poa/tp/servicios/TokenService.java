package com.poa.tp.servicios;

import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.poa.tp.entidades.Usuario;
import com.poa.tp.seguridad.ConstantesSeguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	
	private static TokenService instance;
	private final SecretKey tokenKey;
	private static final long DURACION_MS = 1000 * 60 * 5;// ms * s * m para cambiar los minutos solo hay que tocar el ultimo numero
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
	
	public String extraerSujetoToken(HttpServletRequest request) {
		if(tieneToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request))
			return parsearToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request).getSubject();
		return null;
	}
	
	public Integer extraerIdToken(HttpServletRequest request) {
		if(tieneToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request)) {
			Integer id = Integer.parseInt(parsearToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request).getId());
			return id;
		}
		return null;
	}
	
}
