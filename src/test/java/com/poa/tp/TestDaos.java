package com.poa.tp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.poa.tp.datos.Dao;
import com.poa.tp.datos.ICanjeDao;
import com.poa.tp.datos.IUsuarioDao;
import com.poa.tp.entidades.Canje;
import com.poa.tp.entidades.Item;
import com.poa.tp.entidades.Producto;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;

@SpringBootTest
public class TestDaos {
	
	@Autowired
	private Dao<Producto> productoDao;
	@Autowired 
	private IUsuarioDao usuarioDao;
	@Autowired
	private ICanjeDao canjeDao;

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
			
			productoDao.insert(ps5);
			Producto leido = productoDao.get(ps5.getId());
			assertEquals(leido.getId(),ps5.getId());

			leido.setCosto(600);
			productoDao.update(leido);
			Producto leido2 = productoDao.get(leido.getId());
			assertNotEquals(ps5.getCosto(),600);
			assertEquals(leido2.getCosto(),600);
			
			Producto borrado = productoDao.delete(leido.getId());
			assertEquals(leido,borrado);
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
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUsuario() {
		
		Usuario u = new Usuario();
		u.setNombre("isa");
		u.setContrasena("789");
		u.setCorreo("@@@");
		u.setPuntos(400);
		
		assertEquals(u.getId(),0);
		assertAll(()->{
			
			usuarioDao.insert(u);
			Usuario leido = usuarioDao.getByName("isa");
			assertEquals(leido.getId(),u.getId());
			
			leido.setPuntos(1);
			usuarioDao.update(leido);
			Usuario leido2 = usuarioDao.get(leido.getId());
			assertEquals(leido2.getPuntos(),1);
			
			Usuario borrado = usuarioDao.delete(leido.getId());
			assertEquals(borrado.getCorreo(),leido.getCorreo());
			assertEquals(borrado.getNombre(),leido.getNombre());
			
			assertThrows(CrudException.class,()->usuarioDao.get(borrado.getId()));
		});
		
		assertThrows(CrudException.class,()->{
			Usuario u2 = new Usuario();
			u2.setNombre("luis");
			u2.setCorreo("a");
			u2.setContrasena("123");
			u2.setPuntos(1);
			usuarioDao.insert(u2);
		});
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCanje() {
		
		assertAll(()->{
			
			Usuario luis = usuarioDao.getByName("luis");
			List<Canje> canjesLuis = new ArrayList<Canje>(luis.getCanjes());
			List<Canje> canjes = canjeDao.getCanjesByUsuario(luis.getId(), 0); 
			
			Collections.sort(canjesLuis,new Comparator<Canje>() { //lo ordeno porque el set puede venir desordenado 
				public int compare(Canje o1, Canje o2) {
					return o1.getId()-o2.getId();
				}
			});
			
			for(int i = 0; i<Dao.LIMITE_PAGINA; i++) {
				
				List<Item> itemsLuis = new ArrayList<Item>(canjesLuis.get(i).getItems());
				List<Item> items = new ArrayList<Item>(canjes.get(i).getItems());
				
				for(int j = 0; j<itemsLuis.size(); j++)
					assertEquals(itemsLuis.get(j).getId(),items.get(j).getId());
				
			}
		});
		
		assertAll(()->{
			Usuario max = usuarioDao.getByName("max");
			Producto prod = productoDao.getAll(0).get(0);
			int valor = prod.getCosto();
			Item item = new Item(prod, 2); //cualquier producto sirve para probar
			max.setPuntos(max.getPuntos()+valor*2); //le regalo al usuario para hacer el canje
			HashSet<Item> items = new HashSet<Item>();
			items.add(item);
			Canje canje = new Canje(max, items, Date.valueOf(LocalDate.now()));
			canje.canjear();
			Canje insertado = canjeDao.insert(canje); //en este caso el objeto insertado si es distinto al de antes de insertar (por el merge)
			assertNotEquals(0,insertado.getId());
			Canje borrado = canjeDao.delete(insertado.getId());
			assertThrows(CrudException.class,()->canjeDao.get(borrado.getId()));
			
		});
		
	}
	
}
