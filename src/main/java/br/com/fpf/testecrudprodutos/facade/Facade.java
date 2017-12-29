package br.com.fpf.testecrudprodutos.facade;

import java.util.Collection;

import br.com.fpf.testecrudprodutos.model.Product;

public interface Facade {
	
	Collection<Product> findAllProduct();

	Product findOneProduct(int id);

	Product saveProduct(Product object);

	Product updateProduct(Product object);

	void deleteProduct(Product object);

	void deleteProduct(int id);

	boolean existsProduct(int id);

}
