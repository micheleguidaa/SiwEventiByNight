package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
	public List<Owner> findAllByOrderByNameAsc();
}
