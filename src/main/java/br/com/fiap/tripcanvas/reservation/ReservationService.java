package br.com.fiap.tripcanvas.reservation;

import br.com.fiap.tripcanvas.restaurant.RestaurantService;
import br.com.fiap.tripcanvas.restaurant.RestaurantTools;
import com.openai.core.http.AsyncStreamResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantService restaurantService;
    private ChatClient chatClient;

    public ReservationService(ChatClient.Builder chatClient, ReservationRepository reservationRepository, RestaurantService restaurantService) {
        this.chatClient = chatClient
                .defaultSystem("""
                        Você é um assistente de reservas de restaurantes. 
                        Você pode fazer reservas usando a ferramenta createReservation.
                        Se o usuário deixar de fornecer algum campo obrigatório, você deve pedir para ele fornecer.
                        Se possível sugira restaurantes usando a ferramenta findRestaurantsFromDestination, mas não é obrigatório.
                        Quando a reserva for criada, você deve informar o código de confirmação da reserva para o usuário.
                        """)
                .build();
        this.reservationRepository = reservationRepository;
        this.restaurantService = restaurantService;
    }

    public Flux<String> sendMessageToAI(String messageText) {
        return chatClient.prompt()
                .user(messageText)
                .tools(
                        new ReservationTools(this),
                        new RestaurantTools(restaurantService)
                )
                .stream()
                .content();
    }

    public Reservation createReservation(Reservation reservation) {
        reservation.setId(null);
        // campos obrigatorio
        if (reservation.getClientName() == null || reservation.getClientName().isEmpty()) {
            throw new IllegalArgumentException("Para fazer uma reserva, informe seu nome");
        }

        if(reservation.getNumberOfPeople() < 0 || reservation.getNumberOfPeople() > 12) {
            throw new IllegalArgumentException("O número de pessoas deve ser entre 1 e 12");
        }

        if (reservation.getReservationDateTime() == null) {
            throw new IllegalArgumentException("Para fazer uma reserva, informe a data da reserva");
        }

        if (reservation.getReservationDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("A data da reserva não pode ser no passado");
        }

        reservation.setConfirmationCode("RES-"+ UUID.randomUUID().toString().substring(0, 4).toUpperCase());

        return reservationRepository.save(reservation);
    }
}
