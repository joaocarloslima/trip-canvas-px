package br.com.fiap.tripcanvas.reservation;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationTools {

    private final ReservationService reservationService;

    public ReservationTools(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Tool(
            name = "createReservation",
            description = "Ferramenta para criar uma reserva. Use essa ferramenta para criar reservas de restaurantes."
    )
    public Reservation createReservation(
            @ToolParam(description = "Nome do cliente") String clientName,
            @ToolParam(description = "Nome do restaurante") String restaurantName,
            @ToolParam(description = "Data e hora da reserva, não pode ser no passado") LocalDateTime reservationDateTime,
            @ToolParam(description = "Número de pessoas. Deve ser entre 1 e 12") int numberOfPeople
    ) {
        var reservation = new Reservation();
        reservation.setClientName(clientName);
        reservation.setRestaurant(restaurantName);
        reservation.setReservationDateTime(reservationDateTime);
        reservation.setNumberOfPeople(numberOfPeople);

        return reservationService.createReservation(reservation);
    }

}
