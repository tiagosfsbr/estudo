package br.com.simplifiqueponto.movie_catalog_service.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.simplifiqueponto.movie_catalog_service.models.CatalogItem;
import br.com.simplifiqueponto.movie_catalog_service.models.Movie;
import br.com.simplifiqueponto.movie_catalog_service.models.Rating;
import br.com.simplifiqueponto.movie_catalog_service.models.UserRating;

/**
 * Testes unitários para a classe MovieCatalogResource
 * 
 * Testa o comportamento do endpoint /catalog/{userId} que retorna
 * uma lista de filmes com ratings para um usuário específico.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MovieCatalogResource Tests")
class MovieCatalogResourceTest {

    // Injeta um mock do RestTemplate para simular chamadas HTTP
    @Mock
    private RestTemplate restTemplate;

    // Injeta a classe a ser testada, usando o mock do RestTemplate
    @InjectMocks
    private MovieCatalogResource movieCatalogResource;

    private String testUserId;
    private UserRating mockUserRating;
    private List<Rating> mockRatings;

    /**
     * Configuração antes de cada teste
     * Inicializa as variáveis de teste com dados simulados
     */
    @BeforeEach
    void setUp() {
        testUserId = "user123";

        // Criar ratings simulados
        mockRatings = Arrays.asList(
            new Rating("movie1", 5),
            new Rating("movie2", 4),
            new Rating("movie3", 3)
        );

        // Criar UserRating simulado com os ratings
        mockUserRating = new UserRating();
        mockUserRating.setUserRatings(mockRatings);
    }

    /**
     * Teste de sucesso: Retorna a lista de filmes com ratings corretamente
     * 
     * Cenário: Um usuário com 3 filmes e seus respectivos ratings
     * Resultado esperado: Lista com 3 CatalogItems
     */
    @Test
    @DisplayName("Deve retornar lista de filmes quando getCatalog é chamado com userId válido")
    void testGetCatalogSuccess() {
        // Arrange (Preparar)
        // Simular a resposta do serviço de ratings
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenReturn(mockUserRating);

        // Simular as respostas do serviço de filmes
        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        )).thenReturn(new Movie("movie1", "Filme Um"));

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie2",
            Movie.class
        )).thenReturn(new Movie("movie2", "Filme Dois"));

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie3",
            Movie.class
        )).thenReturn(new Movie("movie3", "Filme Três"));

        // Act (Agir)
        List<CatalogItem> result = movieCatalogResource.getCatalog(testUserId);

        // Assert (Afirmar)
        // Verificar que obtemos exatamente 3 itens
        assertEquals(3, result.size(), "A lista deve conter 3 filmes");

        // Verificar o primeiro filme
        assertEquals("Filme Um", result.get(0).getName(), "Primeiro filme deve ser 'Filme Um'");
        assertEquals(5, result.get(0).getRating(), "Rating do primeiro filme deve ser 5");

        // Verificar o segundo filme
        assertEquals("Filme Dois", result.get(1).getName(), "Segundo filme deve ser 'Filme Dois'");
        assertEquals(4, result.get(1).getRating(), "Rating do segundo filme deve ser 4");

        // Verificar o terceiro filme
        assertEquals("Filme Três", result.get(2).getName(), "Terceiro filme deve ser 'Filme Três'");
        assertEquals(3, result.get(2).getRating(), "Rating do terceiro filme deve ser 3");

        // Verificar que os serviços foram chamados corretamente (número de vezes esperado)
        verify(restTemplate, times(1)).getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        );
        verify(restTemplate, times(3)).getForObject(
            anyString(),
            eq(Movie.class)
        );
    }

    /**
     * Teste: Usuário sem ratings deve retornar lista vazia
     * 
     * Cenário: Um usuário que não tem filmes avaliados
     * Resultado esperado: Lista vazia
     */
    @Test
    @DisplayName("Deve retornar lista vazia quando usuário não tem ratings")
    void testGetCatalogEmptyRatings() {
        // Arrange
        UserRating emptyUserRating = new UserRating();
        emptyUserRating.setUserRatings(new ArrayList<>());

        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenReturn(emptyUserRating);

        // Act
        List<CatalogItem> result = movieCatalogResource.getCatalog(testUserId);

        // Assert
        assertNotNull(result, "O resultado não deve ser nulo");
        assertTrue(result.isEmpty(), "A lista deve estar vazia");
        assertEquals(0, result.size(), "O tamanho da lista deve ser 0");
    }

    /**
     * Teste de erro: Falha ao buscar ratings do usuário
     * 
     * Cenário: Serviço de ratings não está disponível
     * Resultado esperado: Lança exceção
     */
    @Test
    @DisplayName("Deve lançar exceção quando serviço de ratings falha")
    void testGetCatalogRatingsServiceError() {
        // Arrange
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenThrow(new RestClientException("Serviço de ratings não disponível"));

        // Act & Assert
        assertThrows(RestClientException.class, () -> {
            movieCatalogResource.getCatalog(testUserId);
        }, "Deve lançar RestClientException quando serviço de ratings falha");
    }

    /**
     * Teste de erro: Falha ao buscar dados de um filme
     * 
     * Cenário: Serviço de filmes não consegue retornar um filme específico
     * Resultado esperado: Lança exceção durante o processamento
     */
    @Test
    @DisplayName("Deve lançar exceção quando serviço de filmes falha")
    void testGetCatalogMovieServiceError() {
        // Arrange
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenReturn(mockUserRating);

        // Simular falha ao buscar o primeiro filme
        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        )).thenThrow(new RestClientException("Filme não encontrado"));

        // Act & Assert
        assertThrows(RestClientException.class, () -> {
            movieCatalogResource.getCatalog(testUserId);
        }, "Deve lançar exceção quando serviço de filmes falha");
    }

    /**
     * Teste de dados: Verifica se os CatalogItems são criados corretamente
     * 
     * Cenário: Validar a estrutura dos dados retornados
     * Resultado esperado: Cada CatalogItem tem nome, descrição e rating corretos
     */
    @Test
    @DisplayName("Deve criar CatalogItems com dados corretos")
    void testCatalogItemStructure() {
        // Arrange
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenReturn(mockUserRating);

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        )).thenReturn(new Movie("movie1", "Interestellar"));

        // Modificar para retornar lista com apenas um filme
        mockUserRating.setUserRatings(Arrays.asList(new Rating("movie1", 5)));

        // Act
        List<CatalogItem> result = movieCatalogResource.getCatalog(testUserId);

        // Assert
        assertEquals(1, result.size());
        CatalogItem catalogItem = result.get(0);

        assertNotNull(catalogItem, "CatalogItem não deve ser nulo");
        assertEquals("Interestellar", catalogItem.getName(), "Nome do filme incorreto");
        assertEquals("Desc", catalogItem.getDescription(), "Descrição deve ser 'Desc'");
        assertEquals(5, catalogItem.getRating(), "Rating incorreto");
    }

    /**
     * Teste: Múltiplas chamadas devem retornar resultados corretos
     * 
     * Cenário: Testar o método com diferentes usuários
     * Resultado esperado: Cada usuário recebe seus filmes específicos
     */
    @Test
    @DisplayName("Deve retornar filmes diferentes para usuários diferentes")
    void testGetCatalogMultipleUsers() {
        // Arrange
        String userId1 = "user1";
        String userId2 = "user2";

        // Ratings para usuário 1
        UserRating ratings1 = new UserRating();
        ratings1.setUserRatings(Arrays.asList(
            new Rating("movie1", 5),
            new Rating("movie2", 4)
        ));

        // Ratings para usuário 2
        UserRating ratings2 = new UserRating();
        ratings2.setUserRatings(Arrays.asList(
            new Rating("movie3", 3),
            new Rating("movie4", 2)
        ));

        // Mock para usuário 1
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/user1",
            UserRating.class
        )).thenReturn(ratings1);

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        )).thenReturn(new Movie("movie1", "Avatar"));

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie2",
            Movie.class
        )).thenReturn(new Movie("movie2", "Titanic"));

        // Mock para usuário 2
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/user2",
            UserRating.class
        )).thenReturn(ratings2);

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie3",
            Movie.class
        )).thenReturn(new Movie("movie3", "Inception"));

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie4",
            Movie.class
        )).thenReturn(new Movie("movie4", "The Matrix"));

        // Act
        List<CatalogItem> result1 = movieCatalogResource.getCatalog(userId1);
        List<CatalogItem> result2 = movieCatalogResource.getCatalog(userId2);

        // Assert
        assertEquals(2, result1.size(), "Usuário 1 deve ter 2 filmes");
        assertEquals(2, result2.size(), "Usuário 2 deve ter 2 filmes");

        assertEquals("Avatar", result1.get(0).getName(), "Primeiro filme de user1 deve ser Avatar");
        assertEquals("Inception", result2.get(0).getName(), "Primeiro filme de user2 deve ser Inception");
    }

    /**
     * Teste: Validar que o RestTemplate é chamado com os argumentos corretos
     * 
     * Cenário: Verificar que as URLs estão sendo construídas corretamente
     * Resultado esperado: As chamadas HTTP usam as URLs esperadas
     */
    @Test
    @DisplayName("Deve chamar RestTemplate com URLs corretas")
    void testRestTemplateCallsWithCorrectUrls() {
        // Arrange
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenReturn(mockUserRating);

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        )).thenReturn(new Movie("movie1", "Filme"));

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie2",
            Movie.class
        )).thenReturn(new Movie("movie2", "Filme"));

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie3",
            Movie.class
        )).thenReturn(new Movie("movie3", "Filme"));

        // Act
        movieCatalogResource.getCatalog(testUserId);

        // Assert - Verificar que as chamadas HTTP foram feitas com as URLs corretas
        InOrder inOrder = inOrder(restTemplate);

        // Primeiro chama o serviço de ratings
        inOrder.verify(restTemplate).getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        );

        // Depois chama o serviço de filmes para cada movie ID
        inOrder.verify(restTemplate).getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        );
        inOrder.verify(restTemplate).getForObject(
            "http://localhost:8081/movies/movie2",
            Movie.class
        );
        inOrder.verify(restTemplate).getForObject(
            "http://localhost:8081/movies/movie3",
            Movie.class
        );
    }

    /**
     * Teste: Campos do CatalogItem devem estar preenchidos
     * 
     * Cenário: Garantir que nenhum campo é nulo
     * Resultado esperado: Todos os campos têm valores válidos
     */
    @Test
    @DisplayName("Deve garantir que CatalogItems não têm campos nulos")
    void testCatalogItemFieldsNotNull() {
        // Arrange
        when(restTemplate.getForObject(
            "http://localhost:8082/ratingsdata/users/" + testUserId,
            UserRating.class
        )).thenReturn(mockUserRating);

        when(restTemplate.getForObject(
            "http://localhost:8081/movies/movie1",
            Movie.class
        )).thenReturn(new Movie("movie1", "Test Movie"));

        mockUserRating.setUserRatings(Arrays.asList(new Rating("movie1", 4)));

        // Act
        List<CatalogItem> result = movieCatalogResource.getCatalog(testUserId);

        // Assert
        assertFalse(result.isEmpty());
        CatalogItem item = result.get(0);

        assertNotNull(item.getName(), "Nome não deve ser nulo");
        assertNotNull(item.getDescription(), "Descrição não deve ser nulo");
        assertFalse(item.getName().isEmpty(), "Nome não deve ser vazio");
        assertFalse(item.getDescription().isEmpty(), "Descrição não deve ser vazia");
    }
}
