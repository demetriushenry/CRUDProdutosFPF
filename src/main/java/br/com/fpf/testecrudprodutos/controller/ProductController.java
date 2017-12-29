package br.com.fpf.testecrudprodutos.controller;

import java.net.URI;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fpf.testecrudprodutos.exception.ProductNotFoundException;
import br.com.fpf.testecrudprodutos.facade.Facade;
import br.com.fpf.testecrudprodutos.model.Product;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final Facade facade;

	@Autowired
	public ProductController(Facade facade) {
		this.facade = facade;
	}

	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<?> create(@RequestBody @Valid Product product) {
		Product newProduct = new Product(product.getDescription(), product.getImage(), product.getPrice(),
				product.getOrigin(), product.getCategory());
		Product result = facade.saveProduct(newProduct);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
				.toUri();
		if (location != null)
			return ResponseEntity.created(location).build();
		else
			return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/readall", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Map<String, Collection<Product>>> readAll() {
		Map<String, Collection<Product>> result = new HashMap<>(1);
		result.put("Product-list", facade.findAllProduct());

		if (result.get("Product-list").isEmpty())
			throw new ProductNotFoundException();

		return new ResponseEntity<Map<String, Collection<Product>>>(result, HttpStatus.OK);
	}

	@GetMapping(value = "/readone/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Map<String, Product>> readAOne(@PathVariable int id) {
		Map<String, Product> result = new HashMap<>(1);
		result.put("product", facade.findOneProduct(id));
		if (result.get("product") != null)
			return new ResponseEntity<Map<String, Product>>(result, HttpStatus.OK);
		else
			throw new ProductNotFoundException(id);
	}

	@PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Map<String, Product>> update(@PathVariable int id, @RequestBody @Valid Product product) {
		Product pd = facade.findOneProduct(id);
		if (pd == null)
			throw new ProductNotFoundException(id);

		pd.setDescription(product.getDescription());
		pd.setPurchaseDate(new Date());
		pd.setImage(product.getImage());
		pd.setPrice(product.getPrice());
		pd.setOrigin(product.getOrigin());
		pd.setCategory(product.getCategory());

		Map<String, Product> result = new HashMap<>(1);
		result.put("updated-product", facade.updateProduct(pd));
		return new ResponseEntity<Map<String, Product>>(result, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<?> delete(@PathVariable int id) {
		if (!facade.existsProduct(id))
			throw new ProductNotFoundException(id);

		facade.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}

}
