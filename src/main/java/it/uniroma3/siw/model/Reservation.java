package it.uniroma3.siw.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Reservation {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	private LocalDateTime reservationDateTime;
	
	@ManyToOne 
	private User users;
	@OneToOne
	private Event event;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getReservationDateTime() {
		return reservationDateTime;
	}

	public void setReservationDateTime(LocalDateTime reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	} 
}