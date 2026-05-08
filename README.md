# Controle de Estoque API

API REST para gerenciamento de estoque com autenticação JWT.

## Tecnologias

- Java 25 + Spring Boot 4
- Spring Security + JWT (jjwt 0.12.6)
- Spring Data JPA + Hibernate 7
- PostgreSQL + Flyway
- Lombok

## Pré-requisitos

- Java 25+
- Docker (para o banco de dados)

## Como rodar

**1. Suba o banco de dados**
```bash
docker compose up -d
```

**2. Rode a aplicação**
```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

> Por padrão conecta em `localhost:5432` com usuário `admin` e senha `admin123`. Configure via variáveis de ambiente se necessário.

## Variáveis de ambiente

| Variável | Padrão | Descrição |
|---|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/estoque` | URL do banco |
| `DB_USER` | `admin` | Usuário do banco |
| `DB_PASS` | `admin123` | Senha do banco |
| `JWT_SECRET` | *(valor padrão inseguro)* | Chave secreta JWT (mínimo 256 bits) |
| `JWT_EXPIRATION` | `86400000` | Expiração do token em ms (padrão: 24h) |

> Em produção, sempre defina `JWT_SECRET` com uma chave segura.

## Endpoints

### Autenticação (público)

```
POST /api/auth/registrar
POST /api/auth/login
```

### Produtos (requer token)

```
GET    /api/produtos
GET    /api/produtos/{id}
POST   /api/produtos        (ADMIN)
PUT    /api/produtos/{id}   (ADMIN)
DELETE /api/produtos/{id}   (ADMIN)
```

### Movimentações (requer token)

```
GET  /api/movimentacoes/produto/{produtoId}
POST /api/movimentacoes
```

## Exemplos de uso

**Registrar usuário**
```http
POST /api/auth/registrar
Content-Type: application/json

{
  "nome": "Admin",
  "email": "admin@estoque.com",
  "senha": "admin123"
}
```

**Login**
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@estoque.com",
  "senha": "admin123"
}
```
Resposta:
```json
{
  "token": "eyJhbGci...",
  "tipo": "Bearer",
  "email": "admin@estoque.com"
}
```

**Usar o token nas demais requests**
```
Authorization: Bearer eyJhbGci...
```

**Criar produto**
```http
POST /api/produtos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "Arroz",
  "descricao": "Pacote 5kg",
  "quantidade": 100,
  "preco": 25.90
}
```

**Registrar movimentação**
```http
POST /api/movimentacoes
Authorization: Bearer {token}
Content-Type: application/json

{
  "produtoId": 1,
  "tipo": "ENTRADA",
  "quantidade": 50,
  "observacao": "Reposição de estoque"
}
```

## Estrutura do projeto

```
src/main/java/com/controle/estoque/
├── config/         # SecurityConfig
├── controller/     # AuthController, ProdutoController, MovimentacaoController
├── dto/
│   ├── request/    # LoginRequest, RegisterRequest, ProdutoRequest, MovimentacaoRequest
│   └── response/   # AuthResponse, ProdutoResponse, MovimentacaoResponse
├── entity/         # Usuario, Produto, Movimentacao
├── exception/      # GlobalExceptionHandler, ResourceNotFoundException, BusinessException
├── repository/     # Interfaces JPA
├── security/       # JwtService, JwtAuthFilter, UserDetailsServiceImpl
└── service/        # AuthService, ProdutoService, MovimentacaoService

src/main/resources/
├── application.yaml
└── db/migration/   # V1 usuarios, V2 produtos, V3 movimentacoes
```
