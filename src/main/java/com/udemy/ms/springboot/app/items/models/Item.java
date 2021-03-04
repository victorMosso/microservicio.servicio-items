package com.udemy.ms.springboot.app.items.models;

import com.udemy.ms.springboot.model.commons.entity.Producto;
public class Item {

	private Producto producto;
	private Integer cantidad;

	public Item(Producto producto, Integer cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Item() {
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getTotal() {
		return this.producto.getPrecio() * cantidad.doubleValue();
	}

}
