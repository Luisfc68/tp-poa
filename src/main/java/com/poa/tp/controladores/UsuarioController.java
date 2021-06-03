package com.poa.tp.controladores;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.IUsuarioDao;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;
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
	public ResponseEntity<Object> login(@RequestBody Usuario enviado){
		try {
			
			if(enviado.getNombre() == null || enviado.getContrasena() == null)
				return errorService.send(HttpStatus.BAD_REQUEST, "Usuario o clave no enviados");
			
			Usuario usuario = usuarioDao.getByName(enviado.getNombre());
			
			if(!usuario.getContrasena().equals(enviado.getContrasena()))
				return errorService.send(HttpStatus.FORBIDDEN, "Usuario o clave incorrectos");
			
			String token = tokenService.crearToken(usuario);
			
			return ResponseEntity.ok(token);
			
		} catch (CrudException e) {
			System.err.println(e.getMessage());
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> getUsuario(HttpServletRequest request){
		try {
			return ResponseEntity.ok(usuarioDao.getByName(tokenService.extraerSujetoToken(request)));
		} catch (CrudException e) {
			System.err.println(e.getMessage());
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
	
	@PostMapping("/signup")
	public ResponseEntity<Object> signUp(@RequestBody Usuario enviado){
		
		try {
			
			if(enviado.getNombre() == null || enviado.getContrasena() == null || enviado.getCorreo() == null)
				return errorService.send(HttpStatus.BAD_REQUEST, "Datos faltantes");
			
			usuarioDao.insert(enviado);
			
			return ResponseEntity.created(new URI("/usuario/"+enviado.getId())).body(enviado);
			
		} catch(CrudException e) {
			return errorService.send(HttpStatus.CONFLICT, e.getMessage());
		}catch (URISyntaxException e) {
			e.printStackTrace(); //no va a ocurrir porque esta hardcodeado
			return null;
		}
	}
	
}
