package br.com.fpf.testecrudprodutos.facade.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fpf.testecrudprodutos.facade.Facade;
import br.com.fpf.testecrudprodutos.model.Product;
import br.com.fpf.testecrudprodutos.service.ProductService;

@Service
public class FacadeImpl implements Facade {
	
	private final ProductService service;
	
	@Autowired
	public FacadeImpl(ProductService service) {
		this.service = service;
	}

	@Override
	public Collection<Product> findAllProduct() {
		return service.findAll();
	}

	@Override
	public Product findOneProduct(int id) {
		return service.findOne(id);
	}

	@Override
	public Product saveProduct(Product object) {
		return service.save(object);
	}

	@Override
	public Product updateProduct(Product object) {
		return service.update(object);
	}

	@Override
	public void deleteProduct(Product object) {
		service.delete(object);
	}

	@Override
	public void deleteProduct(int id) {
		service.delete(id);
	}

	@Override
	public boolean existsProduct(int id) {
		return service.exists(id);
	}

}
