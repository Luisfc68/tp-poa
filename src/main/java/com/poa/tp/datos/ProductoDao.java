package com.poa.tp.datos;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poa.tp.entidades.Producto;
import com.poa.tp.excepciones.CrudException;

@Repository
public class ProductoDao implements Dao<Producto>{
	
	@Autowired
	private SessionFactory sf;
	
	public Producto insert(Producto o) throws CrudException{
		Session s = null;
		try {
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			s.save(o);
			t.commit();
			return o;
		}catch(RollbackException e) {
			throw new CrudException("El producto no cumple con las caracteristicas para ser registrado");
		}finally {
			if(s!=null)
				s.close();
		}
	}

	public Producto update(Producto o) throws CrudException{
		Session s = null;
		try {
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			s.update(o);
			t.commit();
			return o;
		}catch(RollbackException e) {
			throw new CrudException("La actualizacion no cumple con las reglas requeridas para ser realizada");
		}finally {
			if(s!=null)
				s.close();
		}
	}

	public Producto delete(int id) throws CrudException {
		Session s = null;
		try {
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			Producto original = s.load(Producto.class, id); 
			Producto borrado = original.clone(); //esto porque despues el delete me borra el original y no tengo como devolverlo
			s.delete(original);
			t.commit();
			return borrado;
		}catch(EntityNotFoundException e) {
			throw new CrudException("El producto que se ha intentado borrar no existe");
		}catch(RollbackException e) { 
			throw new CrudException("No es posible borrar este producto");
		}finally{
			if(s!=null)
				s.close();
		}
		
	}

	public Producto get(int id) throws CrudException {
		Session s = sf.openSession();
		Producto prod = s.get(Producto.class, id);
		s.close();
		if(prod!=null)
			return prod;
		else
			throw new CrudException("El producto con id "+id+" no existe");
	}

	public List<Producto> getAll(int offset) {
		
		Session s = sf.openSession();
		//poner solo from es equivalente al select * en sql
		Query<Producto> q = s.createQuery("from producto",Producto.class);
		q.setFirstResult(offset);
		q.setMaxResults(LIMITE_PAGINA);
		List<Producto> prods = q.list();
		s.close();
		return prods;
		
	}


}
