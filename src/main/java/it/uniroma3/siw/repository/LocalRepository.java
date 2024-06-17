package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Local;

public interface LocalRepository extends CrudRepository<Local, Long>{
	List<Local> findByNameStartingWithIgnoreCase(String name);

}