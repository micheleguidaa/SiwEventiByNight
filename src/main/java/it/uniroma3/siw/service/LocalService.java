package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.repository.LocalRepository;

public class LocalService {
		@Autowired
		private LocalRepository localRepository;
		
		// Trova tutti i locali
		public Iterable<Local> findAll() {
			return localRepository.findAll();
		}
}
