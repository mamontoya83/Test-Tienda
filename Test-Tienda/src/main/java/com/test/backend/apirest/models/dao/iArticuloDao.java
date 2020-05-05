package com.test.backend.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.test.backend.apirest.models.entity.Articulo;

public interface iArticuloDao extends CrudRepository<Articulo, Long>{
	 
//	List<Articulo> findByIdInventario(String idInventario);
//	
//	List<Articulo> findByCantidadGreaterThan(int cantidad);
//	
//	@Query("FROM INVENTARIO WHERE CREATEAT=?1 ORDER BY NOMBRE")
//	List<Articulo> findByCreateAtSorted(String fecha);
		
}
