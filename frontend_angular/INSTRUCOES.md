# Instruções para Executar o Projeto Angular

## 1. Pré-requisitos

Certifique-se de ter instalado:
- **Node.js** (versão 16+ recomendada)
- **npm** (vem com Node.js)
- **Git** (opcional, para clonar o repositório)

Para verificar as versões:
```bash
node --version
npm --version
```

## 2. Instalação das Dependências

Navegue até a pasta do projeto e execute:
```bash
cd movie-catalog-frontend
npm install
```

Este comando irá instalar todas as dependências listadas em `package.json`.

## 3. Executar o Servidor de Desenvolvimento

Execute o comando:
```bash
npm start
```

Ou, se preferir usar Angular CLI diretamente:
```bash
ng serve
```

A aplicação abrirá automaticamente em `http://localhost:4200`

## 4. Verificar a Comunicação com o Backend

Antes de usar a aplicação, certifique-se de que o serviço Java está rodando:

```bash
# Em outro terminal, na pasta do serviço Java
cd ../movie-catalog-service
mvn spring-boot:run
```

O serviço deve estar escutando em `http://localhost:8080`

## 5. Usar a Aplicação

1. Abra o navegador em `http://localhost:4200`
2. Digite um ID de usuário (ex: `user123`)
3. Clique no botão "Buscar"
4. Veja a lista de filmes com suas avaliações

## Troubleshooting

### Erro: "Cannot find module"
```bash
# Delete node_modules and reinstall
rm -r node_modules package-lock.json
npm install
```

### Porta 4200 já está em uso
```bash
# Use uma porta diferente
ng serve --port 4300
```

### Erro de CORS na requisição
Adicione CORS no backend Java:
```java
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/catalog")
public class MovieCatalogResource { }
```

### Backend não está respondendo
Verifique se:
- [ ] O serviço Java está rodando
- [ ] Está na porta correta (8080)
- [ ] Para CORS, acesse a URL diretamente no navegador: `http://localhost:8080/catalog/user123`

## Build para Produção

Para criar a versão otimizada:
```bash
npm run build
```

Os arquivos serão gerados em `dist/movie-catalog-frontend/`

## Parar o Servidor

Pressione `Ctrl + C` no terminal onde o servidor está rodando.

## Estrutura de Pastas Importante

```
src/
├── app/
│   ├── catalog/           ← Componente principal
│   ├── services/          ← Lógica de comunicação com API
│   └── models/            ← Interfaces/Tipos TypeScript
├── index.html             ← HTML principal
├── main.ts                ← Ponto de entrada
└── styles.css             ← Estilos globais
```

## Dicas de Desenvolvimento

1. **Hot Reload**: Qualquer mudança nos arquivos é recarregada automaticamente
2. **Abra o DevTools**: F12 para ver logs e debug
3. **Angular DevTools**: Instale a extensão do Chrome para maior facilidade
4. **HTTP Interceptor**: Se precisar adicionar headers (como tokens), crie um interceptor

## Próximos Passos

- [ ] Adicione mais funcionalidades ao catálogo
- [ ] Implemente filtros e busca
- [ ] Adicione testes com Jasmine
- [ ] Faça deploy em um servidor (Vercel, Netlify, etc.)

## Mais Ajuda

- Documentação Angular: https://angular.io/docs
- Comunidade Angular: https://discord.gg/angular
