package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Local;

public interface LocalRepository extends CrudRepository<Local, Long>{
	public List<Local> findByNameStartingWithIgnoreCase(String name);
	public List<Local> findAllByOrderByNameAsc();
	public boolean existsByName(String name);
}