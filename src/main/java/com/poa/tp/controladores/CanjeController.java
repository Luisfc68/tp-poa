package com.poa.tp.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.ICanjeDao;
import com.poa.tp.entidades.Canje;
import com.poa.tp.excepciones.CrudException;
import com.poa.tp.servicios.CustomErrorService;
import com.poa.tp.servicios.TokenService;

@RestController
@RequestMapping(value="/canje")
public class CanjeController {
	
	@Autowired
	private ICanjeDao canjeDao;
	
	@Autowired
	private CustomErrorService errorService;
	
	@Autowired 
	private TokenService tokenService;

	@GetMapping
	public ResponseEntity<Object> getCanjesUsuario(HttpServletRequest request,@RequestParam(value="offset",defaultValue="0")int offset){
			
		Integer id = tokenService.extraerIdToken(request);
		if(id!=null)
			return ResponseEntity.ok(canjeDao.getCanjesByUsuario(id, offset));
		else
			return errorService.send(HttpStatus.NOT_FOUND, "El usuario no existe");
		
	}
	
	@GetMapping("/{idCanje}")
	public ResponseEntity<Object> getCanjeUsuario(HttpServletRequest request, @PathVariable int idCanje){
		try {
			Integer idUsuario = tokenService.extraerIdToken(request);
			
			if(idUsuario!=null) {
				Canje canje = canjeDao.getCanjeByUsuario(idUsuario,idCanje);
				return ResponseEntity.ok(canje);
			}else {
				return errorService.send(HttpStatus.NOT_FOUND, "El usuario no existe");
			}
		}catch(CrudException e) {
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
		
	}
	
}
