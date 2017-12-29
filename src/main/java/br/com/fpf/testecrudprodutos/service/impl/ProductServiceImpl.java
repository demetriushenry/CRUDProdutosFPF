package br.com.fpf.testecrudprodutos.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fpf.testecrudprodutos.model.Product;
import br.com.fpf.testecrudprodutos.repository.ProductRepository;
import br.com.fpf.testecrudprodutos.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository repository;

	@Autowired
	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public Collection<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public Product findOne(int id) {
		return repository.findOne(id);
	}

	@Override
	public Product save(Product object) {
		return repository.save(object);
	}

	@Override
	public Product update(Product object) {
		return repository.saveAndFlush(object);
	}

	@Override
	public void delete(Product object) {
		repository.delete(object);
	}

	@Override
	public void delete(int id) {
		repository.delete(id);
	}

	@Override
	public boolean exists(int id) {
		return repository.exists(id);
	}

}
