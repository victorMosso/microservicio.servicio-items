package com.udemy.ms.springboot.app.items.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.udemy.ms.springboot.app.items.models.Item;
import com.udemy.ms.springboot.model.commons.entity.Producto;

@Service("restTemplateService")
public class ItemServiceRestImpl implements ItemService {
	private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	private RestTemplate restTemplateClient;
	
	@Override
	public List<Item> getAllItems() {
		log.info("Utilizando: "+this.getClass().getSimpleName()); 
		List<Producto> listProducto = 
				 Arrays.asList(restTemplateClient.getForObject("http://servicio-productos/prd/listar", Producto[].class));
		 return listProducto
				 .stream()
				 .map(producto -> new Item(producto,1))
				 .collect(Collectors.toList());
	}

	@Override
	public Item getItemDetail(Long id, Integer cantidad) {
		log.info("Utilizando: "+this.getClass().getSimpleName());
		Map<String, String> pathVariables = new HashMap<String,String>();
		pathVariables.put("id", id.toString());
	
		Producto prd = restTemplateClient.getForObject("http://servicio-productos/prd/ver/{id}", Producto.class, pathVariables);
		return new Item(prd,cantidad);
	}

	@Override
	public Producto createProduct(Producto entity) {
		HttpEntity<Producto> body = new HttpEntity<Producto>(entity);
		ResponseEntity<Producto> response = restTemplateClient.
				exchange("http://servicio-productos/prd/crear", HttpMethod.POST, body, Producto.class);
		Producto producto = response.getBody();
		return producto;
	}

	@Override
	public Producto editProduct(Producto entity, Long id) {
		Map<String,String> pathVariables = new HashMap<String,String>();
		pathVariables.put("id", id.toString());
		
		HttpEntity<Producto> body = new HttpEntity<Producto>(entity);
		ResponseEntity<Producto> response = restTemplateClient.
				exchange("http://servicio-productos/prd/editar/{id}", HttpMethod.PUT, body, Producto.class, pathVariables);
		return response.getBody();
	}

	@Override
	public void deleteProduct(Long id) {
		Map<String,String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		restTemplateClient.delete("http://servicio-productos/prd/eliminar/{id}", pathVariables);
	}
	
	
	
}
