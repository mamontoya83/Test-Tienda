package com.test.backend.apirest.models.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.backend.apirest.models.dao.iArticuloDao;
import com.test.backend.apirest.models.entity.Articulo;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticuloServiceImp implements iArticuloService{
	
	@Autowired
	private iArticuloDao articuloDao;

	@Override
	@Transactional(readOnly=true)
	public List<Articulo> findAll() {
		return (List<Articulo>) articuloDao.findAll();
	}

	@Override
	public Articulo findById(Long id) {
		return articuloDao.findById(id).orElse(null);
	}

	@Override
	public Articulo save(Articulo articulo) {
		return articuloDao.save(articulo);
	}

	@Override
	public void delete(Long id) {
		articuloDao.deleteById(id);
	}


	
}
