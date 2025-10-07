-- Criação das tabelas do sistema de gerenciamento de pedidos

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME DEFAULT NULL,
    INDEX idx_users_email (email),
    INDEX idx_users_role (role),
    INDEX idx_users_ativo (ativo)
);

-- Tabela de produtos
CREATE TABLE IF NOT EXISTS produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    quantidade_estoque INT NOT NULL DEFAULT 0,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME DEFAULT NULL,
    INDEX idx_produtos_categoria (categoria),
    INDEX idx_produtos_ativo (ativo),
    INDEX idx_produtos_estoque (quantidade_estoque),
    INDEX idx_produtos_preco (preco)
);

-- Tabela de pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    status ENUM('PENDENTE', 'PAGO', 'CANCELADO') NOT NULL DEFAULT 'PENDENTE',
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    data_criacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao DATETIME DEFAULT NULL,
    INDEX idx_pedidos_usuario (usuario_id),
    INDEX idx_pedidos_status (status),
    INDEX idx_pedidos_data_criacao (data_criacao),
    FOREIGN KEY (usuario_id) REFERENCES users(id)
);

-- Tabela de itens do pedido
CREATE TABLE IF NOT EXISTS pedido_itens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    nome_produto VARCHAR(255) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    quantidade INT NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    INDEX idx_pedido_itens_pedido (pedido_id),
    INDEX idx_pedido_itens_produto (produto_id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Inserção de dados iniciais

-- Usuário admin padrão
INSERT INTO users (id, nome, email, senha, role, ativo) VALUES 
(1, 'Administrador', 'admin@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN', TRUE);

-- Usuário comum padrão
INSERT INTO users (id, nome, email, senha, role, ativo) VALUES 
(2, 'Usuário Teste', 'user@example.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'USER', TRUE);

-- Produtos de exemplo
INSERT INTO produtos (id, nome, descricao, preco, categoria, quantidade_estoque, ativo) VALUES 
(1, 'Smartphone Samsung Galaxy', 'Smartphone com tela de 6.1 polegadas, 128GB de armazenamento', 1299.99, 'Eletrônicos', 50, TRUE),
(2, 'Notebook Dell Inspiron', 'Notebook com processador Intel i5, 8GB RAM, 256GB SSD', 2499.99, 'Eletrônicos', 25, TRUE),
(3, 'Camiseta Polo', 'Camiseta polo de algodão, diversas cores disponíveis', 89.90, 'Vestuário', 100, TRUE),
(4, 'Tênis Nike Air Max', 'Tênis esportivo confortável para corrida e caminhada', 399.99, 'Calçados', 75, TRUE),
(5, 'Livro Clean Architecture', 'Livro sobre arquitetura de software por Robert C. Martin', 89.90, 'Livros', 30, TRUE),
(6, 'Cafeteira Elétrica', 'Cafeteira elétrica com capacidade para 12 xícaras', 159.99, 'Eletrodomésticos', 40, TRUE),
(7, 'Mesa de Escritório', 'Mesa de madeira maciça para escritório, 120x80cm', 599.99, 'Móveis', 15, TRUE),
(8, 'Fone de Ouvido Bluetooth', 'Fone de ouvido sem fio com cancelamento de ruído', 299.99, 'Eletrônicos', 60, TRUE),
(9, 'Relógio Smartwatch', 'Relógio inteligente com GPS e monitoramento de saúde', 899.99, 'Eletrônicos', 20, TRUE),
(10, 'Mochila Executiva', 'Mochila para notebook com compartimento acolchoado', 199.99, 'Acessórios', 45, TRUE);