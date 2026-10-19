package br.com.fiap.tripcanvas.activity;

import br.com.fiap.tripcanvas.restaurant.RestaurantService;
import br.com.fiap.tripcanvas.restaurant.RestaurantTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    private final RestaurantService restaurantService;
    private ChatClient chatClient;

    private static final PromptTemplate promptTemplate = new PromptTemplate("""
               Crie pelo menos 5 sugestões de atividades para fazer em {destination}.
               Para cada atividade, forneça o título da atividade, descrição, tipo e duração em minutos.
               Os tipos válidos são: MUSEUM, PARK, RESTAURANT, SHOPPING, HOTEL.
               Sugira restaurantes que estão na lista obtida da ferramenta findRestaurantsFromDestination.
               Se não existirem restaurantes na lista, pode sugerir outros restaurantes.
            """);

    public ActivityService(ChatClient.Builder builder, RestaurantService restaurantService) {
        this.chatClient = builder.build();
        this.restaurantService = restaurantService;
    }

    public List<Activity> generateActivityForDestination(String destination){
        return chatClient
                .prompt(promptTemplate.create(Map.of("destination", destination)))
                .tools(new RestaurantTools(restaurantService))
                .call()
                .entity(
                        new ParameterizedTypeReference<List<Activity>>() {},
                        spec -> spec.validateSchema()
                );
    }

}
