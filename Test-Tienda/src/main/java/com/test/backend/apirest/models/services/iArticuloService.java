package com.test.backend.apirest.models.services;

import java.util.List;

import com.test.backend.apirest.models.entity.Articulo;

public interface iArticuloService {
	
	public List<Articulo> findAll();
	
	public Articulo findById(Long id);
	
	public Articulo save(Articulo articulo);
	
	public void delete(Long id);
	
}
