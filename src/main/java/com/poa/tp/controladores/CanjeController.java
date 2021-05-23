package com.poa.tp.controladores;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.Dao;
import com.poa.tp.datos.ICanjeDao;
import com.poa.tp.datos.IUsuarioDao;
import com.poa.tp.entidades.Canje;
import com.poa.tp.entidades.Item;
import com.poa.tp.entidades.Producto;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;
import com.poa.tp.excepciones.NoStockException;
import com.poa.tp.excepciones.UsuarioPobreException;
import com.poa.tp.servicios.CustomErrorService;
import com.poa.tp.servicios.TokenService;

@RestController
@RequestMapping(value="/canje")
public class CanjeController {
	
	@Autowired
	private ICanjeDao canjeDao;
	
	@Autowired
	private Dao<Producto> productoDao;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
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
	
	@PostMapping
	public ResponseEntity<Object> intentarCanje(HttpServletRequest request,@RequestBody Item[] pedido){
		
		try {
			
			HashSet<Item> items = new HashSet<Item>();
			
			for(Item i : pedido) 
				items.add(new Item(productoDao.get(i.getId().getIdProducto()),i.getCantidad()));
			
			Usuario usuario = usuarioDao.getByName(tokenService.extraerSujetoToken(request));
			Canje canje = new Canje(usuario,items,Date.valueOf(LocalDate.now()));
			
			canje.canjear();
			canje = canjeDao.insert(canje);
			usuarioDao.update(usuario);
			
			return ResponseEntity.created(new URI("/canje/"+canje.getId())).body(canje);
			
		} catch (CrudException e) {
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (NoStockException e) {
			return errorService.send(HttpStatus.CONFLICT, e.getMessage());
		} catch (UsuarioPobreException e) {
			return errorService.send(HttpStatus.CONFLICT, e.getMessage());
		} catch (URISyntaxException e) {
			e.printStackTrace(); //Nunca deberia ocurrir porque el uri esta hardcodeado
			return null;
		}
		
	}
	
}
