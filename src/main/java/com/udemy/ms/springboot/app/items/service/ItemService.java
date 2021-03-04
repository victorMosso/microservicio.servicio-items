package com.udemy.ms.springboot.app.items.service;

import java.util.List;

import com.udemy.ms.springboot.app.items.models.Item;
import com.udemy.ms.springboot.model.commons.entity.Producto;

public interface ItemService {
	
	public List<Item> getAllItems();
	
	public Item getItemDetail(Long id, Integer cantidad);
	
	public Producto createProduct(Producto entity);
	
	public Producto editProduct(Producto entity, Long id);
	
	public void deleteProduct(Long id);

}
