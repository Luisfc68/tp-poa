package com.poa.tp.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poa.tp.datos.Dao;
import com.poa.tp.entidades.Producto;
import com.poa.tp.excepciones.CrudException;

@RestController
@RequestMapping(value="/productos")
public class ProductoControlador {
	
	@Autowired
	private Dao<Producto> productoDao;
	
	@GetMapping("/")
	public ResponseEntity<List<Producto>> getProductos() {
		List<Producto> productos = productoDao.getAll(0);
		return (productos.size() != 0) ? ResponseEntity.ok(productos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{offset}")
	public ResponseEntity<List<Producto>> getProductos(@PathVariable int offset) {
		List<Producto> productos = productoDao.getAll(offset);
		return (productos.size() != 0) ? ResponseEntity.ok(productos) : ResponseEntity.noContent().build();
	}
	
	@GetMapping(params="id")
	public ResponseEntity<Producto> getProducto(@RequestParam int id) {
		Producto producto;
		try {
			producto = productoDao.get(id);
			return ResponseEntity.ok(producto);
		} catch (CrudException e) {
			System.err.println(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
	
}
