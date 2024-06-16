package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.repository.LocalRepository;

@Service
public class LocalService {
		@Autowired
		private LocalRepository localRepository;
		
		// Trova tutti i locali
		public Iterable<Local> findAll() {
			return localRepository.findAll();
		}
		
		public Local findById(Long id) {
			return localRepository.findById(id).orElse(null);
		}

		public void deleteById(Long id) {
			localRepository.deleteById(id);
		}

		public void save(Local local) {
			localRepository.save(local);
		}
}
