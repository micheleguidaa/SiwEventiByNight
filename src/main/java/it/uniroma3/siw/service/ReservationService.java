package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Reservation;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.ReservationRepository;
import jakarta.validation.Valid;

@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EventService eventService;

	public Iterable<Reservation> findAll() {
		return reservationRepository.findAll();
	}

	public void registerReservation(@Valid Reservation reservation, Long userId, Long eventId) {
		reservation.setUser(userService.getUser(userId));
		reservation.setEvent(eventService.getEvent(eventId));
		reservationRepository.save(reservation);
	}

	public void deleteById(Long id) {
		reservationRepository.deleteById(id);
	}

	public boolean isUserReservedForEvent(Long userId, Long eventId) {
		return reservationRepository.existsByUserIdAndEventId(userId, eventId);
	}
	
	public List<Reservation> findByUser(User user){
		return reservationRepository.findByUser(user);
	}
	
	public boolean doesReservationExistForOwner(Long ownerId) {
        return reservationRepository.existsByEvent_Local_Owner_Id(ownerId);
    }

}
