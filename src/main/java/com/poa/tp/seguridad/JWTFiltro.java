package com.poa.tp.seguridad;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.poa.tp.servicios.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JWTFiltro extends OncePerRequestFilter {
	
	private TokenService tokenService;
	
	public JWTFiltro(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			
			if (tokenService.tieneToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request)) {
				
				Claims claims = tokenService.parsearToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request);
				
				if (claims.getSubject() != null) {
					
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,new ArrayList<SimpleGrantedAuthority>());
					SecurityContextHolder.getContext().setAuthentication(auth);
					
				} else {
					SecurityContextHolder.clearContext();
				}
				
			} else {
					SecurityContextHolder.clearContext();
			}
			
			chain.doFilter(request, response);
			
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			
			System.err.println(e.getMessage());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.getWriter().write(e.getMessage());
			response.flushBuffer();
			
		}
	}

}