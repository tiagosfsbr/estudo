# Angular Movie Catalog Frontend

Aplicação frontend em Angular para consumir a API de Catálogo de Filmes.

## Descrição

Este projeto é um exemplo prático de como criar uma aplicação Angular que consome uma API REST (neste caso, o serviço de catálogo de filmes). Demonstra as principais funcionalidades do Angular como:

- **Componentes** (Standalone Components)
- **Serviços** com HttpClient
- **Data Binding** (two-way binding com ngModel)
- **Diretivas estruturais** (*ngIf, *ngFor)
- **RxJS e Observables**
- **Roteamento**
- **Styling avançado** com CSS Grid

## Estrutura do Projeto

```
src/
├── app/
│   ├── models/
│   │   └── catalog-item.model.ts       # Interface para dados do catálogo
│   ├── services/
│   │   └── catalog.service.ts          # Serviço que faz chamadas HTTP
│   ├── catalog/
│   │   ├── catalog.component.ts        # Componente principal
│   │   ├── catalog.component.html      # Template
│   │   └── catalog.component.css       # Estilos
│   ├── app.component.ts                # Componente raiz
│   ├── app.component.html              # Template do app
│   ├── app.component.css               # Estilos do app
│   └── app.routes.ts                   # Configuração das rotas
├── index.html                           # HTML principal
├── main.ts                              # Ponto de entrada
└── styles.css                           # Estilos globais
```

## Requisitos

- Node.js (versão 16 ou superior)
- npm ou yarn
- Angular CLI (opcional, mas recomendado)

## Instalação

1. **Clonar ou baixar o projeto:**
```bash
cd movie-catalog-frontend
```

2. **Instalar dependências:**
```bash
npm install
```

## Execução

### Modo desenvolvimento

```bash
npm start
```

ou

```bash
ng serve
```

A aplicação será aberta automaticamente em `http://localhost:4200`

### Modo produção (Build)

```bash
npm run build
```

Os arquivos compilados serão gerados na pasta `dist/`

## Como Usar

1. Abra a aplicação no navegador
2. Você verá um campo de entrada para "ID do Usuário"
3. Digite um ID de usuário (ex: `user123`)
4. Clique no botão "Buscar"
5. A lista de filmes com ratings será exibida

## Integração com o Backend

Esta aplicação espera que o serviço de **Movie Catalog Service** esteja rodando em:

```
http://localhost:8080/catalog/{userId}
```

Se o seu serviço está rodando em outra porta, altere a URL em `src/app/services/catalog.service.ts`:

```typescript
private apiUrl = 'http://localhost:8080/catalog';
```

## Tipos de Dados

### CatalogItem

```typescript
interface CatalogItem {
  name: string;        // Nome do filme
  description: string; // Descrição
  rating: number;      // Avaliação (1-5)
}
```

## Principais Conceitos Demonstrados

### 1. **Standalone Components**
Componentes que não precisam de NgModule:
```typescript
@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})
```

### 2. **HttpClient**
Fazer requisições HTTP:
```typescript
this.http.get<CatalogItem[]>(`${this.apiUrl}/${userId}`)
```

### 3. **Observables e Subscribe**
Tratar respostas assíncronas:
```typescript
this.catalogService.getCatalogByUserId(this.userId).subscribe({
  next: (data) => { /* sucesso */ },
  error: (error) => { /* erro */ }
});
```

### 4. **Two-Way Binding**
Sincronizar dados entre componente e template:
```html
<input [(ngModel)]="userId" />
```

### 5. **Renderização Condicional**
Usar *ngIf e *ngFor:
```html
<div *ngIf="isLoading">Carregando...</div>
<div *ngFor="let item of catalogItems">{{ item.name }}</div>
```

## Correção de Erros Comuns

### "Cannot GET /catalog"
- Certifique-se que o serviço Java está rodando
- Verifique a porta (deve ser 8080)

### "No 'Access-Control-Allow-Origin'"
- Adicione CORS no backend Java:
```java
@CrossOrigin(origins = "http://localhost:4200")
```

### Módulos não encontrados após instalação
```bash
npm install
```

## Desenvolvimento Futuro

Ideias para expandir este projeto:

- [ ] Adicionar paginação
- [ ] Filtrar por rating mínimo
- [ ] Buscar filmes por nome
- [ ] Adicionar animações
- [ ] Implementar cache com RxJS
- [ ] Adicionar testes unitários
- [ ] Dark mode

## Referências Úteis

- [Angular Documentation](https://angular.io)
- [Angular HttpClient](https://angular.io/guide/http)
- [RxJS Documentation](https://rxjs.dev/)
- [Angular Forms](https://angular.io/guide/forms)

## Autor

Exemplo criado para demonstrar integração entre Angular e API REST.

## Licença

MIT
