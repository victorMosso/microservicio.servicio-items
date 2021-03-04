package com.udemy.ms.springboot.app.items.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.udemy.ms.springboot.app.items.models.Item;
import com.udemy.ms.springboot.model.commons.entity.Producto;
import com.udemy.ms.springboot.app.items.service.ItemService;

@RefreshScope
@RestController
@RequestMapping(path = "/itm")
public class ItemController {
	
	private static Logger log = LoggerFactory.getLogger(ItemController.class);
	
	@Autowired
	@Qualifier("feignClientService")
	private ItemService itemService; 
	
	@Value("${configuracion.texto}")
	private String texto;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/listar")
	public List<Item> getListItems(){
		return itemService.getAllItems();		
	}
	
	@HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/ver/{id}/cantidad/{cant}")
	public Item getItem(@PathVariable Long id, 
			@PathVariable(value = "cant") Integer cantidad) {
		return itemService.getItemDetail(id, cantidad);
	}
	
	public Item alternativeMethod(Long id, Integer cantidad) {
		Producto prd = new Producto();
		prd.setId(id);
		prd.setNombre("Producto X");
		prd.setPrecio(200.00);
		return new Item(prd,cantidad);
	}
	
	@GetMapping("/consultarConfiguracion")
	public ResponseEntity<?> datosConfig(@Value("${server.port}") String puerto){
		log.info(this.texto);
		Map<String,String> json = new HashMap<String,String>();
		json.put("texto", texto);
		json.put("puerto", puerto);
		if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("usuario", this.env.getProperty("configuracion.user.name"));
			json.put("correo", this.env.getProperty("configuracion.user.email"));
		}
		return new ResponseEntity<Map<String,String>>(json,HttpStatus.OK);	
	}
	
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto agregarProducto(@RequestBody Producto entity) {
		return itemService.createProduct(entity);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto modificarProducto(@RequestBody Producto entity, @PathVariable Long id) {
		return itemService.editProduct(entity, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminarProducto(@PathVariable Long id) {
		itemService.deleteProduct(id);
	}
}
