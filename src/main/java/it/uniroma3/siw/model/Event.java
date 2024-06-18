package it.uniroma3.siw.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 16, message = "Il nome deve essere compreso tra 2 e 16 caratteri")
    private String name;

    @NotBlank(message = "Il tema è obbligatorio")
    private String theme;

    @NotNull(message = "La data e ora di inizio è obbligatoria")
    @FutureOrPresent(message = "La data e ora di inizio deve essere nel futuro o presente")
    private LocalDateTime startDateTime;

    @NotNull(message = "La data e ora di fine è obbligatoria")
    @FutureOrPresent(message = "La data e ora di fine deve essere nel futuro o presente")
    private LocalDateTime endDateTime;

    @NotNull(message = "Il costo è obbligatorio")
    @Positive(message = "Il costo deve essere positivo")
    private Long cost;

    @NotNull(message = "Il numero massimo di partecipanti è obbligatorio")
    @Positive(message = "Il numero massimo di partecipanti deve essere positivo")
    private Long nMaxParticipants;

    private String urlImage;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Reservation> reservation;

    @ManyToOne
    private Local local;

    @AssertTrue(message = "L'evento non può finire prima dell'inizio!")
    public boolean getEndDateTimeAfterStartDateTime() {
        if (startDateTime != null && endDateTime != null) {
            return endDateTime.isAfter(startDateTime);
        }
        return true; // Non validiamo se una delle date è null
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getnMaxParticipants() {
        return nMaxParticipants;
    }

    public void setnMaxParticipants(Long nMaxParticipants) {
        this.nMaxParticipants = nMaxParticipants;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public void setReservation(List<Reservation> reservation) {
        this.reservation = reservation;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
