package com.poa.tp.datos;

import com.poa.tp.entidades.Usuario;
import com.poa.tp.excepciones.CrudException;

public interface IUsuarioDao extends Dao<Usuario>{
	Usuario getByName(String nombre) throws CrudException;
}
