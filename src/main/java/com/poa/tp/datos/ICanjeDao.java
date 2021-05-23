package com.poa.tp.datos;

import java.util.List;

import com.poa.tp.entidades.Canje;
import com.poa.tp.excepciones.CrudException;

public interface ICanjeDao extends Dao<Canje>{
	List<Canje> getCanjesByUsuario(int id,int offset);
	Canje getCanjeByUsuario(int idUsuario,int idCanje) throws CrudException;
}
