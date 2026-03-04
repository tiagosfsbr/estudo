package br.com.simplifiqueponto.movie_catalog_service.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.simplifiqueponto.movie_catalog_service.models.CatalogItem;
import br.com.simplifiqueponto.movie_catalog_service.models.Movie;
import br.com.simplifiqueponto.movie_catalog_service.models.UserRating;

// Anotação que marca a classe como um controlador REST, capaz de processar requisições HTTP
@RestController
@CrossOrigin(origins = "http://localhost:4200")
// Define o caminho base para todas as requisições deste controlador (/catalog)
@RequestMapping("/catalog")
public class MovieCatalogResource {

    // Injeta automaticamente uma instância de RestTemplate para fazer chamadas HTTP
    @Autowired
    RestTemplate  restTemplate;

    // Mapeia requisições GET para o endpoint /catalog/{userId}
    @GetMapping("/{userId}")
    // Método que retorna uma lista de filmes catalogados com ratings do usuário
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        // Faz uma chamada HTTP ao serviço de ratings para obter os ratings do usuário
        UserRating ratings = restTemplate.getForObject("http://localhost:8082/ratingsdata/users/"+userId, UserRating.class);

        // Processa a lista de ratings: para cada rating, busca os dados do filme e cria um CatalogItem
        return ratings.getUserRatings().stream() // Converte a lista em stream para processamento
            .map(rating -> { // Para cada rating, executa esta transformação
                // Faz uma chamada HTTP ao serviço de filmes para obter os dados do filme específico
                Movie movie = restTemplate.getForObject("http://localhost:8081/movies/"+ rating.getMovieId(), Movie.class);
                // Cria um novo CatalogItem combinando nome do filme, descrição e rating
                return new CatalogItem(movie.getName(), "Desc", rating.getRating());
            })
            .collect(Collectors.toList()); // Coleta todos os CatalogItems em uma lista
    }
}
