package com.poa.tp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.poa.tp.datos.Dao;
import com.poa.tp.entidades.Producto;
import com.poa.tp.excepciones.CrudException;

@SpringBootTest
public class TestDaos {
	
	@Autowired
	private Dao<Producto> productoDao;

	@Test
	@Transactional
	@Rollback(true)
	public void testProducto() {
		
		Producto ps5 = new Producto();
		ps5.setCosto(500);
		ps5.setStock(true);
		ps5.setDescripcion("PS5");
		
		assertEquals(ps5.getId(),0);
		
		assertAll(()->{
			
			Producto insertado = productoDao.insert(ps5);
			assertNotEquals(insertado.getId(),ps5);
			
			insertado.setCosto(600);
			productoDao.update(insertado);
			Producto leido = productoDao.get(insertado.getId());
			assertEquals(leido.getCosto(),600);
			assertEquals(leido,insertado);
			
			Producto borrado = productoDao.delete(insertado.getId());
			assertEquals(ps5,borrado);
			assertThrows(CrudException.class,()->productoDao.get(borrado.getId()));
			
		});
		
		
		assertThrows(CrudException.class,()->{
			Producto ps52 = new Producto();
			ps5.setCosto(700);
			ps5.setStock(false);
			ps5.setDescripcion("PS5");
			productoDao.insert(ps52);
		});
		
	}
}
