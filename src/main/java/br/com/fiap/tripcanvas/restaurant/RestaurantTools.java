package br.com.fiap.tripcanvas.restaurant;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

public class RestaurantTools {

    private final RestaurantService restaurantService;

    public RestaurantTools(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Tool(
            name = "findRestaurantsFromDestination",
            description = "Ferramenta para descobrir os restaurantes de um destino específico. Use essa lista para fazer as sugestões. O parâmetro 'destination' é o nome do destino que você quer descobrir os restaurantes."
    )
    public List<Restaurant> findRestaurantsFromDestination(@ToolParam(description = "O nome do destino que você quer descobrir os restaurantes.") String destination) {
        return restaurantService.getRestaurantsByLocal(destination);
    }

}
