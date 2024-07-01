package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Local;

@Repository
public interface LocalRepository extends CrudRepository<Local, Long>{
	List<Local> findByNameStartingWithIgnoreCase(String name);
	List<Local> findAllSortedByName();
}