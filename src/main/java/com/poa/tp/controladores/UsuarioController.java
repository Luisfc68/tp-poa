package com.poa.tp.controladores;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.IUsuarioDao;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;
import com.poa.tp.servicios.CustomErrorService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@RestController
@RequestMapping(value="/usuarios")
public class UsuarioController {
	
	public static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired 
	private CustomErrorService errorService;
	
	@PostMapping(path="/login")
	public ResponseEntity<Object> login(@RequestParam("usuario") String nombre, @RequestParam("clave") String clave){
		try {
			Usuario usuario = usuarioDao.getByName(nombre);
			
			if(!usuario.getContrasena().equals(clave) || !usuario.getNombre().equals(nombre))
				return errorService.send(HttpStatus.FORBIDDEN, "Usuario o clave incorrectos");
			
			String token = getJWTToken(usuario);
			
			return ResponseEntity.ok(token);
			
		} catch (CrudException e) {
			e.getMessage();
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
	
	}
	
	private String getJWTToken(Usuario u) {
		
		String token = Jwts
				.builder()
				.setId(Integer.toString(u.getId()))
				.setSubject(u.getNombre())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(key)
				.compact();

		return "Bearer " + token;
	}
	
	
}
