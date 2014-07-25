package br.com.wancharle.clubedolivro.utils;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T extends Serializable > {
	long retrieveCount();

	List<T> retrieveAll();

	List<T> retrieveSome(int[] interval);

	T retrieveById(Long id);

	T save(T object);

	void delete(T object);
}
