package com.poa.tp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.Dao;
import com.poa.tp.entidades.Producto;
import com.poa.tp.excepciones.CrudException;
import com.poa.tp.servicios.CustomErrorService;

@RestController
@RequestMapping(value="/productos")
public class ProductoController {
	
	@Autowired
	private Dao<Producto> productoDao;
	
	@Autowired
	private CustomErrorService errorService;
	
	@GetMapping(params="offset")
	public ResponseEntity<List<Producto>> getProductos(@RequestParam(defaultValue="0") int offset) {
		List<Producto> productos = productoDao.getAll(offset);
		return ResponseEntity.ok(productos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getProducto(@PathVariable int id) {
		Producto producto;
		try {
			producto = productoDao.get(id);
			return ResponseEntity.ok(producto);
		} catch (CrudException e) {
			System.err.println(e.getMessage());
			return errorService.send(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
