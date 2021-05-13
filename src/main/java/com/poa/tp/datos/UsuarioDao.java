package com.poa.tp.datos;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;

@Repository
public class UsuarioDao implements IUsuarioDao{
	
	@Autowired
	private SessionFactory sf;

	public Usuario insert(Usuario o) throws CrudException {
		Session s = null;
		try {
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			s.save(o);
			t.commit();
			return o;
		}catch(RollbackException e) {
			throw new CrudException("El usuario no cumple con las caracteristicas para ser registrado");
		}finally {
			if(s!=null)
				s.close();
		}
	}

	public Usuario update(Usuario o) throws CrudException {
		Session s = null;
		try {
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			s.update(o);
			t.commit();
			return o;
		}catch(RollbackException e) {
			throw new CrudException("La actualizacion del usuario no cumple con las reglas requeridas");
		}finally {
			if(s!=null)
				s.close();
		}
	}

	public Usuario delete(int id) throws CrudException {
		Session s = null;
		try {
			s = sf.openSession();
			Transaction t = s.beginTransaction();
			Usuario original = s.load(Usuario.class, id);
			Usuario borrado = original.clone();
			s.delete(original);
			t.commit();
			return borrado;
		}catch(EntityNotFoundException e) {
			throw new CrudException("El usuario que ha intentado borrar no existe");
		}catch(RollbackException e) {
			throw new CrudException("No es posible borrar el usuario");
		}finally {
			if(s!=null)
				s.close();
		}
	}

	public Usuario get(int id) throws CrudException {
		Session s = sf.openSession();
		Usuario usuario = s.get(Usuario.class, id);
		s.close();
		if(usuario!=null)
			return usuario;
		else
			throw new CrudException("El usuario con id "+id+" no existe");
	}

	public List<Usuario> getAll(int offset) {
		
		Session s = sf.openSession();
		Query<Usuario> q = s.createQuery("from usuario", Usuario.class);
		q.setFirstResult(offset);
		q.setMaxResults(LIMITE_PAGINA);
		List<Usuario> usuarios = q.list();
		s.close();
		return usuarios;
		
	}

	public Usuario getByName(String nombre) throws CrudException {
		Session s = null;
		try {
			
			s = sf.openSession();
			Query<Usuario> q = s.createQuery("from usuario where nombre='"+nombre+"'", Usuario.class);
			Usuario usuario = q.getSingleResult();
			return usuario;
			
		}catch(NoResultException e) {
			throw new CrudException("El usuario "+nombre+" no existe");
		}finally {
			if(s!=null)
				s.close();
		}
	}

}
