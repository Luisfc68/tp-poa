package com.poa.tp;

import com.poa.tp.entidades.Canje;
import com.poa.tp.entidades.Item;
import com.poa.tp.entidades.Producto;
import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.NoStockException;
import com.poa.tp.excepciones.UsuarioPobreException;

public class Test {

	public static void main(String[] args) {
		Producto pelota = new Producto(1,5,"Pelota",true);
		Producto tijera = new Producto(1,10,"Tijera",true);
		Producto mouse = new Producto(1,5,"Mouse",false);
		
		Usuario luis = new Usuario(1,"Luis","123",15,"Luis@");
		Usuario max = new Usuario(2,"Max","456",10,"Max@");
		
		//Canje a
		Item a1 = new Item(mouse,1);
		Item a2 = new Item(tijera,1);
		Item a3 = new Item(pelota,1);
		
		Item[] itemsA = {a1,a2,a3};
		
		//Canje b 
		Item b1 = new Item(tijera,1);
		Item b2 = new Item(pelota,1);
		
		Item[] itemsB = {b1,b2};
		
		//Canje c 
		Item c1 = new Item(tijera,3);
		
		Item[] itemsC = {c1};
		
		//Canjes
		
		Canje a = new Canje(luis,itemsA);
		Canje b = new Canje(luis,itemsB);
		Canje c = new Canje(max,itemsC);
		
		try {
			a.canjear();
		} catch (NoStockException | UsuarioPobreException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			b.canjear();
		} catch (NoStockException | UsuarioPobreException e) {
			System.err.println(e.getMessage());
		}
		
		try {
			c.canjear();
		} catch (NoStockException | UsuarioPobreException e) {
			System.err.println(e.getMessage());
		}
		
		
		System.out.println(luis.toString());
		System.out.println(max.toString());
		
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		
		System.out.println("----Canjes de usuarios----");
		System.out.println("Canjes de luis");
		for(Canje canje : luis.getCanjes())
			System.out.println(canje.toString());
		
		System.out.println("Canjes de max");
		for(Canje canje : max.getCanjes())
			System.out.println(canje.toString());
		
		System.out.println(pelota);
		System.out.println(tijera);
		System.out.println(mouse);
		
		
	}

}
