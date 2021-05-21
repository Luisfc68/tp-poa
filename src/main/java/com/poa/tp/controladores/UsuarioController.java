package com.poa.tp.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.IUsuarioDao;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;
import com.poa.tp.seguridad.ConstantesSeguridad;
import com.poa.tp.servicios.CustomErrorService;
import com.poa.tp.servicios.TokenService;


@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired 
	private CustomErrorService errorService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping(path="/login")
	public ResponseEntity<Object> login(@RequestParam("usuario") String nombre, @RequestParam("clave") String clave){
		try {
			Usuario usuario = usuarioDao.getByName(nombre);
			
			if(!usuario.getContrasena().equals(clave) || !usuario.getNombre().equals(nombre))
				return errorService.send(HttpStatus.FORBIDDEN, "Usuario o clave incorrectos");
			
			String token = tokenService.crearToken(usuario);
			
			return ResponseEntity.ok(token);
			
		} catch (CrudException e) {
			e.getMessage();
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> getUsuario(HttpServletRequest request){
		try {
			return ResponseEntity.ok(usuarioDao.getByName(getUsuarioDelToken(request)));
		} catch (CrudException e) {
			e.printStackTrace();
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUsuario(@PathVariable int id){
		try {
			return ResponseEntity.ok(usuarioDao.get(id));
		} catch (CrudException e) {
			e.printStackTrace();
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	private String getUsuarioDelToken(HttpServletRequest request) {
		if(tokenService.tieneToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request))
			return tokenService.parsearToken(ConstantesSeguridad.TOKEN_HEADER.getValor(), request).getSubject();
		return null;
	}
	
}
