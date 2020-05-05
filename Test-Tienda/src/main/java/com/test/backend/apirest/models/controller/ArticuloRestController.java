package com.test.backend.apirest.models.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
	import java.util.stream.Collectors;
	
	import javax.validation.Valid;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.backend.apirest.models.entity.Articulo;
import com.test.backend.apirest.models.services.iArticuloService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api")
@RestController
public class ArticuloRestController {
	
	@Autowired
	private iArticuloService articuloService;
	
	@GetMapping("/articulos")
	public List<Articulo> index(){
		return articuloService.findAll();
	}
	
	@GetMapping("/articulos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Articulo articulo = new Articulo();
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			articulo = articuloService.findById(id);
			
			if(articulo==null) {
				response.put("Mensaje", "El articulo con Id=" + id.toString() + " no existe");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
			}
			
			response.put("Articulo", articulo);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al intentar acceder a la base de datos");
			response.put("Error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@PostMapping("/articulos")
	public ResponseEntity<?> create(@Valid @RequestBody Articulo articulo, BindingResult result){
		
		Articulo articuloNvo = null;
		Map<String, Object> response = new HashMap<>();
		
		//Esta linea valida los datos que vienen del post, cuida que tengan los formatos adecuados
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage() )
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			articuloNvo = articuloService.save(articulo);
			
			response.put("Mensaje", "El registro " + articulo.getNombre() + " se ha creado con éxito");
			response.put("Articulo", articuloNvo);
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al intentar acceder a la base de datos");
			response.put("Error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/articulos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Articulo articulo, BindingResult result, @PathVariable Long id){
		
		Articulo articuloNvo = null;
		Map<String, Object> response = new HashMap<>();
		
		//Esta linea valida los datos que vienen del post, cuida que tengan los formatos adecuados
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '"+ err.getField() + "' " + err.getDefaultMessage() )
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			articuloNvo = articuloService.findById(id);
			
			articuloNvo.setNombre(articulo.getNombre());
			articuloNvo.setCantidad(articulo.getCantidad());
			articuloNvo.setIdInventario(articulo.getIdInventario());
			articuloNvo.setCreateAt(articulo.getCreateAt());
			
			articuloService.save(articuloNvo);
			
			response.put("Mensaje", "El registro " + articuloNvo.getNombre() + " se ha actualizado con éxito");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al intentar acceder a la base de datos");
			response.put("Error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/articulos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			articuloService.delete(id);
			response.put("Mensaje", "El registro con Id=" + id + " se ha eliminado con éxito");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "Error al intentar acceder a la base de datos");
			response.put("Error", e.getMessage() + " : " + e.getMostSpecificCause());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
