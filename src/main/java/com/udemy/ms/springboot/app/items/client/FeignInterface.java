package com.udemy.ms.springboot.app.items.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.udemy.ms.springboot.model.commons.entity.Producto;

@FeignClient(name = "servicio-productos",path = "/prd")
public interface FeignInterface {

	@GetMapping("/listar")
	public List<Producto> getAllProducts();

	@GetMapping("/ver/{id}")
	public Producto getProductById(@PathVariable Long id);
	
	@PostMapping("/crear")
	public Producto crearProducto(@RequestBody Producto entity);
	
	@PutMapping("/editar/{idProducto}")
	public Producto modificarProducto(@RequestBody Producto entity, @PathVariable(name = "idProducto") Long id);
	
	@DeleteMapping("/eliminar/{idProducto}")
	public void eliminarProducto(@PathVariable(value = "idProducto") Long id);
}
