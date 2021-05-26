package com.poa.tp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.poa.tp.entidades.Canje;
import com.poa.tp.entidades.Item;
import com.poa.tp.entidades.Producto;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.NoStockException;
import com.poa.tp.excepciones.UsuarioPobreException;

@TestInstance(Lifecycle.PER_CLASS)
public class TestEntidades {
	
	private Usuario luis,max;
	private Producto pelota,tijera,mouse;
	
	
	@BeforeAll
	public void crearObjetos() {
		pelota = new Producto(1,5,"Pelota",true);
		tijera = new Producto(1,10,"Tijera",true);
		mouse = new Producto(1,5,"Mouse",false);
		
		luis = new Usuario(1,"Luis","123",20,"Luis@");
		max = new Usuario(2,"Max","456",10,"Max@");
	}
	
	@Test
	public void pruebaPrecios() {
		
		Item pelotaItem = new Item(pelota,3);
		Item mouseItem = new Item(mouse,3);
		Item tijeraItem = new Item(tijera,5);
		
		assertEquals(pelotaItem.getValor(),15);
		assertEquals(mouseItem.getValor(),15);
		assertEquals(tijeraItem.getValor(),50);
	}
	
	@Test
	public void canjeSinStock() {
		Item pelotaItem = new Item(pelota,1);
		Item mouseItem = new Item(mouse,1);
		Item tijeraItem = new Item(tijera,1);
		
		Set<Item> items = new HashSet<Item>();
		
		items.add(tijeraItem);
		items.add(pelotaItem);
		items.add(mouseItem);
		
		Canje canje = new Canje(luis,items,Date.valueOf(LocalDate.now()));
		
		assertThrows(NoStockException.class,()->{
			canje.canjear();
		});
	}
	
	@Test
	public void canjeUsuarioPobre() {
		
		Item pelotaItem = new Item(pelota,50);
		Item tijeraItem = new Item(tijera,1);
		Set<Item> items = new HashSet<Item>();
		
		items.add(pelotaItem);
		items.add(tijeraItem);
		
		Canje canje = new Canje(max,items,Date.valueOf(LocalDate.now()));
		
		assertThrows(UsuarioPobreException.class,()->{
			canje.canjear();
		});
	}
	
	@Test
	public void itemMalConstruido() {
		assertThrows(IllegalArgumentException.class,()->new Item(pelota,-1));
	}
	
	@Test
	public void productoMalConstruido() {
		assertThrows(IllegalArgumentException.class,()->new Producto(1, -7, "PS5", false));
	}
	
	@Test
	public void canjeExitoso() {
		Item pelotaItem = new Item(pelota,2);
		Item tijeraItem = new Item(tijera,1);
		Set<Item> items = new HashSet<Item>();
		
		items.add(pelotaItem);
		items.add(tijeraItem);
		
		Canje canje = new Canje(luis,items,Date.valueOf(LocalDate.now()));
		
		assertEquals(canje.getTotal(),20);
		assertAll(()->canje.canjear());
		assertNotEquals(luis.getCanjes().size(),0);
		assertEquals(luis.getPuntos(),0);
	}
	
}
