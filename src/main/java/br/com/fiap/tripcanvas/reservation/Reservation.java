package br.com.fiap.tripcanvas.reservation;

import br.com.fiap.tripcanvas.restaurant.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    private String restaurant;

    private int numberOfPeople;

    private LocalDateTime reservationDateTime;

    private String confirmationCode;
}
