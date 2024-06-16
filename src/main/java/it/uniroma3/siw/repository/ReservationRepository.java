package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Local;
import it.uniroma3.siw.model.Reservation;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

	public boolean existsByEventIdAndUserId(Long eventId, Long userId);

}
