package br.com.fpf.testecrudprodutos.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fpf.testecrudprodutos.facade.Facade;
import br.com.fpf.testecrudprodutos.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private final Facade facade;
	
	@Autowired
	public ProductController(Facade facade) {
		this.facade = facade;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Map<String, Collection<Product>>> readAll() {
		Map<String, Collection<Product>> result = new HashMap<>(1);
		result.put("Product-list", facade.findAllProduct());
		return new ResponseEntity<Map<String, Collection<Product>>>(result, HttpStatus.OK);
	}

}
