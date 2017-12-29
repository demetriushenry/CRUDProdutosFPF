package br.com.fpf.testecrudprodutos.service;

import java.util.Collection;

public interface GenericService<T> {

	Collection<T> findAll();

	T findOne(int id);

	T save(T object);

	T update(T object);

	void delete(T object);

	void delete(int id);

	boolean exists(int id);

}
