package it.uniroma3.siw.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Reservation;
import it.uniroma3.siw.model.User;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	public List<Reservation> findByUser(User user);
	public boolean existsByUserAndEventId(User user,Long eventId);
	public boolean existsByEvent_Local_Owner_Id(Long ownerId);
}
