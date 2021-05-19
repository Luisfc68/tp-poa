package com.poa.tp.datos;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.poa.tp.excepciones.CrudException;

@Repository
public interface Dao<T> {
	
	static final int LIMITE_PAGINA = 5;
	
	T insert(T o) throws CrudException;
	T update(T o) throws CrudException;
	T delete(int id) throws CrudException;
	T get(int id) throws CrudException;
	//el offset no es inclusivo, si el offset es 2, trae a partir del 3
	List<T> getAll(int offset);
}
