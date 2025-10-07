# Sistema de Gerenciamento de Pedidos e Produtos

Sistema de e-commerce desenvolvido seguindo os princípios de Clean Architecture, com autenticação JWT e funcionalidades completas de CRUD para produtos e gerenciamento de pedidos.

## 🎯 Funcionalidades Implementadas

### 🔑 Autenticação e Autorização
- **JWT Authentication**: Sistema completo de autenticação com tokens JWT
- **Perfis de Usuário**:
  - `ADMIN`: Pode criar, atualizar e deletar produtos
  - `USER`: Pode criar pedidos e visualizar produtos

### ⚙️ Gestão de Produtos
- **CRUD Completo** com os seguintes campos:
  - ID (UUID)
  - Nome
  - Descrição
  - Preço
  - Categoria
  - Quantidade em estoque
  - Data de criação e atualização
- **Filtros avançados** por nome, categoria, faixa de preço e status ativo
- **Busca por categoria**

### 📦 Gestão de Pedidos
- **Criação de pedidos** com múltiplos produtos
- **Status do pedido**: PENDENTE → PAGO/CANCELADO
- **Validação de estoque** antes da confirmação de pagamento
- **Atualização automática** do estoque após pagamento
- **Cancelamento automático** se estoque insuficiente
- **Cálculo dinâmico** do valor total baseado nos preços atuais
- **Listagem de pedidos** por usuário autenticado

### 📊 Relatórios e Consultas SQL Otimizadas
- **Top 5 usuários** que mais compraram
- **Ticket médio** dos pedidos por usuário
- **Faturamento total** do mês atual
- **Views otimizadas** para consultas complexas

## 🛠 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Security** com JWT
- **Spring Data JPA**
- **MySQL** como banco de dados
- **Hibernate** como ORM
- **Maven** para gerenciamento de dependências

## 🏗 Arquitetura

O projeto segue os princípios de **Clean Architecture** com as seguintes camadas:

```
src/main/java/com/example/gerenciador/
├── produto/
│   ├── domain/           # Entidades e regras de negócio
│   ├── application/      # Use cases e DTOs
│   └── infra/           # Controllers, repositórios e persistência
├── pedido/
│   ├── domain/           # Entidades e regras de negócio
│   ├── application/      # Use cases e DTOs
│   └── infra/           # Controllers, repositórios e persistência
├── user/
│   ├── domain/           # Entidades e regras de negócio
│   ├── application/      # Use cases e DTOs
│   └── infra/           # Controllers, repositórios e persistência
└── security/
    └── infra/           # Configurações JWT e segurança
```

## 🚀 Como Executar o Projeto

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- MySQL 8.0+
- IDE de sua preferência (IntelliJ IDEA, Eclipse, VS Code)

### 1. Configuração do Banco de Dados

```sql
-- Crie o banco de dados
CREATE DATABASE gerenciador_pedidos;

-- Use o banco
USE gerenciador_pedidos;
```

### 2. Configuração das Propriedades

Edite o arquivo `src/main/resources/application.properties` com suas credenciais do MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gerenciador_pedidos
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### 3. Executando o Projeto

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd teste-gerenciador-pedidos

# Compile o projeto
mvn clean compile

# Execute o projeto
mvn spring-boot:run
```

O servidor estará disponível em: `http://localhost:8080`

### 4. Usuários Padrão

O sistema cria automaticamente usuários padrão:

**Administrador:**
- Email: `admin@example.com`
- Senha: `123456`
- Role: `ADMIN`

**Usuário:**
- Email: `user@example.com`
- Senha: `123456`
- Role: `USER`

## 📚 Documentação da API

### Autenticação

#### Registrar Usuário
```http
POST /api/auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "123456",
  "role": "USER"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "senha": "123456"
}
**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "user@example.com",
  "nome": "Usuário Teste",
  "role": "USER"
}
```

### Produtos

#### Criar Produto (ADMIN)
```http
POST /api/produtos
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Smartphone Samsung",
  "descricao": "Smartphone com tela de 6.1 polegadas",
  "preco": 1299.99,
  "categoria": "Eletrônicos",
  "quantidadeEstoque": 50
}
```

#### Listar Produtos
```http
GET /api/produtos?page=0&size=10&categoria=Eletrônicos
Authorization: Bearer <token>
```

#### Buscar Produto por ID
```http
GET /api/produtos/{id}
Authorization: Bearer <token>
```

#### Atualizar Produto (ADMIN)
```http
PUT /api/produtos/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "nome": "Smartphone Samsung Galaxy S23",
  "descricao": "Smartphone atualizado",
  "preco": 1399.99,
  "categoria": "Eletrônicos",
  "quantidadeEstoque": 45
}
```

#### Deletar Produto (ADMIN)
```http
DELETE /api/produtos/{id}
Authorization: Bearer <token>
```

### Pedidos

#### Criar Pedido
```http
POST /api/pedidos
Authorization: Bearer <token>
Content-Type: application/json

{
  "itens": [
    {
      "produtoId": "10",
      "quantidade": 2
    },
    {
      "produtoId": "9",
      "quantidade": 1
    }
  ]
}
```

#### Listar Pedidos do Usuário
```http
GET /api/pedidos?page=0&size=10
Authorization: Bearer <token>
```

#### Buscar Pedido por ID
```http
GET /api/pedidos/{id}
Authorization: Bearer <token>
```

#### Confirmar Pagamento
```http
POST /api/pedidos/{id}/pagar
Authorization: Bearer <token>
```

### Relatórios (ADMIN)

#### Top 5 Usuários por Compras
```http
GET /api/relatorios/top-usuarios?limite=5
Authorization: Bearer <admin-token>
```

#### Ticket Médio por Usuário
```http
GET /api/relatorios/ticket-medio
Authorization: Bearer <admin-token>
```

#### Faturamento do Mês
```http
GET /api/relatorios/faturamento-mes
Authorization: Bearer <admin-token>
```

## 🗄 Estrutura do Banco de Dados

### Tabelas Principais

#### `users`
- `id` (VARCHAR(36), PK)
- `nome` (VARCHAR(255))
- `email` (VARCHAR(255), UNIQUE)
- `senha` (VARCHAR(255))
- `role` (ENUM: ADMIN, USER)
- `ativo` (BOOLEAN)
- `data_criacao` (DATETIME)
- `data_atualizacao` (DATETIME)

#### `produtos`
- `id` (VARCHAR(36), PK)
- `nome` (VARCHAR(255))
- `descricao` (TEXT)
- `preco` (DECIMAL(10,2))
- `categoria` (VARCHAR(100))
- `quantidade_estoque` (INT)
- `ativo` (BOOLEAN)
- `data_criacao` (DATETIME)
- `data_atualizacao` (DATETIME)

#### `pedidos`
- `id` (VARCHAR(36), PK)
- `usuario_id` (VARCHAR(36), FK)
- `status` (ENUM: PENDENTE, PAGO, CANCELADO)
- `valor_total` (DECIMAL(10,2))
- `data_criacao` (DATETIME)
- `data_atualizacao` (DATETIME)

#### `pedido_itens`
- `id` (VARCHAR(36), PK)
- `pedido_id` (VARCHAR(36), FK)
- `produto_id` (VARCHAR(36), FK)
- `nome_produto` (VARCHAR(255))
- `preco_unitario` (DECIMAL(10,2))
- `quantidade` (INT)
- `valor_total` (DECIMAL(10,2))

### Views Otimizadas

- `vw_top_usuarios_por_compras`: Top usuários por volume de compras
- `vw_ticket_medio_por_usuario`: Estatísticas de ticket médio
- `vw_faturamento_mensal`: Faturamento por mês
- `vw_produtos_mais_vendidos`: Produtos com maior volume de vendas

## 🔒 Segurança

- **JWT Tokens** com expiração configurável
- **BCrypt** para hash de senhas
- **Autorização baseada em roles** (ADMIN/USER)
- **Validação de entrada** com Bean Validation
- **Filtros de autenticação** customizados

## 🧪 Testando a API

### Usando cURL

```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","senha":"123456"}'

# Criar produto (como ADMIN)
curl -X POST http://localhost:8080/api/produtos \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Produto Teste","descricao":"Descrição","preco":99.99,"categoria":"Teste","quantidadeEstoque":10}'

# Criar pedido
curl -X POST http://localhost:8080/api/pedidos \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"itens":[{"produtoId":"<produto-id>","quantidade":1}]}'
```

### Variáveis de Ambiente

```bash
export DB_URL=jdbc:mysql://localhost:3306/gerenciador_pedidos
export DB_USERNAME=root
export DB_PASSWORD=root
export JWT_SECRET=mySecretKeyForJWTTokenGeneration123456789
export JWT_EXPIRATION=86400000
```

## 📝 Logs e Monitoramento

O sistema possui logs detalhados para:
- Queries SQL executadas
- Operações de autenticação
- Erros de validação
- Operações de CRUD

Configure o nível de log no `application.properties`:
```properties
logging.level.com.example.gerenciador=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```