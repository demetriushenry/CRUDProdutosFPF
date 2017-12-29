package br.com.fpf.testecrudprodutos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fpf.testecrudprodutos.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
