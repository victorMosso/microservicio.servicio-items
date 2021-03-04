package com.udemy.ms.springboot.app.items.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.udemy.ms.springboot.app.items.client.FeignInterface;
import com.udemy.ms.springboot.app.items.models.Item;
import com.udemy.ms.springboot.model.commons.entity.Producto;

@Service("feignClientService")
//@Primary
public class ItemServiceFeingImpl implements ItemService {
	private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	private FeignInterface clientFeing;
	
	@Override
	public List<Item> getAllItems() {
		log.info("Utilizando : ".concat(this.getClass().getSimpleName()));
		return clientFeing.getAllProducts()
		.stream()
		.map(producto -> new Item(producto,1))
		.collect(Collectors.toList());			
	}

	@Override
	public Item getItemDetail(Long id, Integer cantidad) {
		log.info("Utilizando : ".concat(this.getClass().getSimpleName()));
		return new Item(clientFeing.getProductById(id),cantidad);
	}

	@Override
	public Producto createProduct(Producto entity) {
		
		return clientFeing.crearProducto(entity);
	}

	@Override
	public Producto editProduct(Producto entity, Long id) {
		
		return clientFeing.modificarProducto(entity, id);
	}

	@Override
	public void deleteProduct(Long id) {
		
		clientFeing.eliminarProducto(id);
	}

}
