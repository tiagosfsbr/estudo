# Guia de Testes Unitários - MovieCatalogResource

## Visão Geral

Este documento explica os testes unitários criados para a classe `MovieCatalogResource`. Os testes cobrem todos os cenários principais e utilizam as melhores práticas de teste com **JUnit 5** e **Mockito**.

## Executar os Testes

### Usando Maven

Para executar todos os testes:
```bash
mvn test
```

Para executar apenas os testes da classe `MovieCatalogResource`:
```bash
mvn test -Dtest=MovieCatalogResourceTest
```

Para executar testes com mais detalhes:
```bash
mvn test -X
```

Para ver relatório de cobertura de testes:
```bash
mvn test jacoco:report
```

### Usando IDE (VS Code com Extension Test Explorer)

1. Abra a classe de teste
2. Clique no ícone de "Play" ao lado da classe ou método
3. Os resultados aparecem em tempo real

## Estrutura dos Testes

Todos os testes seguem o padrão **AAA (Arrange-Act-Assert)**:

```
Arrange: Preparar dados e mocks
Act:     Executar o método a ser testado
Assert:  Verificar se o resultado é o esperado
```

## Testes Implementados

### 1. ✅ testGetCatalogSuccess
**Objetivo:** Validar o caso de sucesso com múltiplos filmes

**O que testa:**
- Retorna uma lista com 3 filmes
- Cada filme tem nome, descrição e rating corretos
- Os serviços são chamados o número correto de vezes

**Como funciona:**
- Mock do serviço de ratings retorna 3 ratings
- Mock do serviço de filmes retorna dados de cada filme
- Valida que CatalogItems foram criados corretamente

**Cenário:**
```
Usuário: user123
Filmes:
  - Filme Um (rating: 5)
  - Filme Dois (rating: 4)
  - Filme Três (rating: 3)
```

---

### 2. ✅ testGetCatalogEmptyRatings
**Objetivo:** Testar quando usuário não tem filmes avaliados

**O que testa:**
- Retorna lista vazia quando não há ratings
- Não causa exceção
- A lista não é nula

**Cenário:**
```
Usuário: user123
Ratings: []  (vazio)
```

**Resultado esperado:**
```
List<CatalogItem> = [] (vazio)
```

---

### 3. ❌ testGetCatalogRatingsServiceError
**Objetivo:** Testar quando o serviço de ratings falha

**O que testa:**
- Lança `RestClientException` quando serviço de ratings não responde
- Erro é propagado corretamente

**Cenário:**
```
Serviço de Ratings: ❌ Down
```

**Resultado esperado:**
```
RestClientException: "Serviço de ratings não disponível"
```

---

### 4. ❌ testGetCatalogMovieServiceError
**Objetivo:** Testar quando o serviço de filmes falha

**O que testa:**
- Lança exceção quando um filme não pode ser recuperado
- O erro é detectado durante o processamento do stream

**Cenário:**
```
Serviço de Ratings: ✅ OK, retorna [movie1, movie2, movie3]
Serviço de Filmes: ❌ Falha ao buscar movie1
```

**Resultado esperado:**
```
RestClientException durante o mapeamento
```

---

### 5. ✅ testCatalogItemStructure
**Objetivo:** Validar a estrutura dos dados retornados

**O que testa:**
- CatalogItem tem todos os campos preenchidos
- Nome vem do serviço de filmes
- Rating vem do serviço de ratings
- Descrição é sempre "Desc"

**Validações:**
```
CatalogItem {
  name: "Interestellar"        ✓ Correto
  description: "Desc"          ✓ Correto
  rating: 5                     ✓ Correto
}
```

---

### 6. ✅ testGetCatalogMultipleUsers
**Objetivo:** Testar o comportamento com múltiplos usuários

**O que testa:**
- Diferentes usuários recebem filmes diferentes
- Não há contaminação de dados entre usuários
- O método é stateless

**Cenário:**
```
User1 → [Avatar (5), Titanic (4)]
User2 → [Inception (3), The Matrix (2)]
```

---

### 7. ✅ testRestTemplateCallsWithCorrectUrls
**Objetivo:** Validar que as URLs estão corretas

**O que testa:**
- Verifica as URLs usadas nas chamadas HTTP
- Ordem das chamadas
- Argumentos passados ao RestTemplate

**URLs validadas:**
```
GET http://localhost:8082/ratingsdata/users/user123
GET http://localhost:8081/movies/movie1
GET http://localhost:8081/movies/movie2
GET http://localhost:8081/movies/movie3
```

---

### 8. ✅ testCatalogItemFieldsNotNull
**Objetivo:** Garantir que nenhum campo é nulo

**O que testa:**
- Nome não é nulo nem vazio
- Descrição não é nula nem vazia
- Rating é um valor válido

**Validações:**
```
item.getName() != null          ✓
item.getName() != ""            ✓
item.getDescription() != null   ✓
item.getDescription() != ""     ✓
```

---

## Conceitos de Teste Utilizados

### 1. **Mocks com Mockito**
```java
@Mock
RestTemplate restTemplate;  // Cria um mock do RestTemplate

when(restTemplate.getForObject(...))
    .thenReturn(mockUserRating);  // Define o comportamento
```

### 2. **Injeção de Mocks**
```java
@InjectMocks
MovieCatalogResource movieCatalogResource;  // Injeta os mocks automaticamente
```

### 3. **Verificação de Chamadas**
```java
verify(restTemplate, times(1)).getForObject(...);  // Verifica que foi chamado 1 vez
verify(restTemplate, times(3)).getForObject(...);  // Verifica que foi chamado 3 vezes
```

### 4. **Teste de Exceções**
```java
assertThrows(RestClientException.class, () -> {
    movieCatalogResource.getCatalog(testUserId);
});
```

## Cobertura de Testes

### Linhas de Código Testadas
- ✅ Injeção do RestTemplate
- ✅ Chamada ao serviço de ratings
- ✅ Processamento do stream
- ✅ Chamadas ao serviço de filmes
- ✅ Criação de CatalogItems
- ✅ Coleta de resultados

### Cobertura por Ramo (Branch Coverage)
- ✅ Sucesso com múltiplos filmes
- ✅ Sucesso com 0 filmes
- ✅ Erro no serviço de ratings
- ✅ Erro no serviço de filmes

## Ordem de Execução dos Testes

```
1. setUp()                              → Inicializa dados
2. testGetCatalogSuccess()              → Caso de sucesso
3. testGetCatalogEmptyRatings()         → Lista vazia
4. testGetCatalogRatingsServiceError()  → Erro de ratings
5. testGetCatalogMovieServiceError()    → Erro de filmes
6. testCatalogItemStructure()           → Estrutura de dados
7. testGetCatalogMultipleUsers()        → Múltiplos usuários
8. testRestTemplateCallsWithCorrectUrls() → URLs corretas
9. testCatalogItemFieldsNotNull()       → Validação de nulos
```

## Dados de Teste

### UserRating Simulado
```json
{
  "userRatings": [
    { "movieId": "movie1", "rating": 5 },
    { "movieId": "movie2", "rating": 4 },
    { "movieId": "movie3", "rating": 3 }
  ]
}
```

### Movies Simulados
```json
[
  { "movieId": "movie1", "name": "Interestellar" },
  { "movieId": "movie2", "name": "Avatar" },
  { "movieId": "movie3", "name": "Inception" }
]
```

### CatalogItems Esperados
```json
[
  {
    "name": "Interestellar",
    "description": "Desc",
    "rating": 5
  },
  {
    "name": "Avatar",
    "description": "Desc",
    "rating": 4
  },
  {
    "name": "Inception",
    "description": "Desc",
    "rating": 3
  }
]
```

## Boas Práticas Implementadas

### ✅ 1. Nomenclatura Clara
```java
// ❌ Ruim
void test1() { }

// ✅ Bom
void testGetCatalogSuccess() { }
void testGetCatalogEmptyRatings() { }
```

### ✅ 2. DisplayName Descritivo
```java
@DisplayName("Deve retornar lista de filmes quando getCatalog é chamado com userId válido")
```

### ✅ 3. Comentários Explicativos
```java
// Arrange (Preparar dados)
// Act (Executar método)
// Assert (Verificar resultado)
```

### ✅ 4. Isolamento Total
- Cada teste é independente
- Dados são criados em `setUp()`
- Nenhuma dependência do banco de dados
- Todos os serviços são mockados

### ✅ 5. Testes Determinísticos
- Mesmo resultado sempre
- Sem chamadas reais à API
- Sem dependências de tempo
- Sem dados aleatórios

## Troubleshooting

### Problema: "Não encontra arquivo de teste"
```bash
# Solução: Garantir que está em
src/test/java/br/com/simplifiqueponto/movie_catalog_service/resources/MovieCatalogResourceTest.java
```

### Problema: "Cannot resolve symbol 'Mock' ou 'InjectMocks'"
```bash
# Solução: Executar
mvn clean install
```

### Problema: Testes falham com "NullPointerException"
```bash
# Solução: Verificar se @ExtendWith(MockitoExtension.class) está presente
```

### Problema: "RestTemplate is null"
```bash
# Solução: Verificar que @InjectMocks está anotando a classe corretamente
```

## Relatórios de Teste

### Gerar Relatório JaCoCo (Cobertura)
```bash
mvn clean test jacoco:report
# Abrir: target/site/jacoco/index.html
```

### Ver Resultados no Console
```bash
mvn test
# Procurar por:
# BUILD SUCCESS (todos passou)
# BUILD FAILURE (alguns falharam)
```

## Próximos Passos

Para expandir os testes:

1. **Testes de Integração**
   - Testar com um RestTemplate real
   - Usar `@SpringBootTest`

2. **Testes de Carga**
   - Testar com 1000+ usuários
   - Medir performance

3. **Testes de Cenários Reais**
   - Timeout na requisição
   - Resposta nula do servidor
   - Dados malformados

4. **Testes de Segurança**
   - SQL Injection (se aplicável)
   - XSS Prevention
   - CORS Validation

## Referências

- [JUnit 5 Documentation](https://junit.org/junit5/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://spring.io/guides/gs/testing-web/)
- [Test Driven Development](https://www.agilealliance.org/glossary/tdd/)

---

**Última Atualização:** 2026-03-04  
**Versão:** 1.0
