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

import com.poa.tp.entidades.Canje;
import com.poa.tp.excepciones.CrudException;

@Repository
public class CanjeDao implements Dao<Canje>{
	
	@Autowired
	private SessionFactory sf;

	public Canje insert(Canje o) throws CrudException {
		Session s = null;
		try {
			if(o.getUsuario() == null)
				throw new CrudException("Todos los canjes deben tener un usuario al ser registrados");
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			o = (Canje)s.merge(o);
			s.save(o);
			t.commit();
			return o;
		}catch(RollbackException e) {
			throw new CrudException("El canje no cumple con las caracteristicas para ser registrado");
		}finally {
			if(s!=null)
				s.close();
		}
		
	}

	public Canje update(Canje o) throws CrudException {
		/*
		 Decidi no permitir la actualizacion de canjes porque no tiene mucho
		 sentido con la logica de negocio, puesto que los canjes ya estan pagados
		 y en teoria el usuario ya tiene los productos asi que no tiene sentido 
		 si un usuario quiere tener mas de un nuevo producto, deberia volver a canjear
		*/
		throw new CrudException("No esta permitida la actualizacion de canjes");
	}

	public Canje delete(int id) throws CrudException {
		
		Session s = null;
		try {
			
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			Canje original = s.load(Canje.class, id);
			Canje borrado = original.clone();
			s.delete(original);
			t.commit();
			return borrado;
			
		}catch(EntityNotFoundException e) {
			throw new CrudException("El canje que se ha intentado borrar no existe");
		}catch(RollbackException e) {
			throw new CrudException("No es posible borrar este canje");
		}finally {
			if(s!=null)
				s.close();
		}
		
	}

	public Canje get(int id) throws CrudException {
		
		Session s = sf.openSession();
		Canje canje = s.get(Canje.class, id);
		s.close();
		if(canje!=null)
			return canje;
		else 
			throw new CrudException("El canje con id "+id+" no existe");
		
	}

	public List<Canje> getAll(int offset) {
		
		Session s = sf.openSession();
		Query<Canje> q = s.createQuery("from canje", Canje.class);
		q.setFirstResult(offset);
		q.setMaxResults(LIMITE_PAGINA);
		List<Canje> canjes = q.list();
		s.close();
		
		return canjes;
	}

}
