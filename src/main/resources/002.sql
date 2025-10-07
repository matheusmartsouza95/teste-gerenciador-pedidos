-- Consultas SQL otimizadas para relatórios

-- View para top 5 usuários que mais compraram
CREATE VIEW vw_top_usuarios_por_compras AS
SELECT 
    p.usuario_id,
    u.nome as nome_usuario,
    u.email,
    COUNT(p.id) as total_pedidos,
    SUM(p.valor_total) as valor_total_gasto,
    AVG(p.valor_total) as ticket_medio
FROM pedidos p
INNER JOIN users u ON p.usuario_id = u.id
WHERE p.status = 'PAGO'
GROUP BY p.usuario_id, u.nome, u.email
ORDER BY total_pedidos DESC, valor_total_gasto DESC
LIMIT 5;

-- View para ticket médio por usuário
CREATE VIEW vw_ticket_medio_por_usuario AS
SELECT 
    p.usuario_id,
    u.nome as nome_usuario,
    u.email,
    COUNT(p.id) as total_pedidos,
    SUM(p.valor_total) as valor_total_gasto,
    AVG(p.valor_total) as ticket_medio,
    MIN(p.valor_total) as pedido_menor_valor,
    MAX(p.valor_total) as pedido_maior_valor
FROM pedidos p
INNER JOIN users u ON p.usuario_id = u.id
WHERE p.status = 'PAGO'
GROUP BY p.usuario_id, u.nome, u.email
ORDER BY ticket_medio DESC;

-- View para faturamento mensal
CREATE VIEW vw_faturamento_mensal AS
SELECT 
    YEAR(p.data_criacao) as ano,
    MONTH(p.data_criacao) as mes,
    COUNT(p.id) as total_pedidos,
    SUM(p.valor_total) as faturamento_total,
    AVG(p.valor_total) as ticket_medio_geral
FROM pedidos p
WHERE p.status = 'PAGO'
GROUP BY YEAR(p.data_criacao), MONTH(p.data_criacao)
ORDER BY ano DESC, mes DESC;

-- View para produtos mais vendidos
CREATE VIEW vw_produtos_mais_vendidos AS
SELECT 
    pi.produto_id,
    pi.nome_produto,
    p.categoria,
    SUM(pi.quantidade) as total_vendido,
    COUNT(DISTINCT pi.pedido_id) as total_pedidos,
    SUM(pi.valor_total) as receita_total,
    AVG(pi.preco_unitario) as preco_medio
FROM pedido_itens pi
INNER JOIN produtos p ON pi.produto_id = p.id
INNER JOIN pedidos ped ON pi.pedido_id = ped.id
WHERE ped.status = 'PAGO'
GROUP BY pi.produto_id, pi.nome_produto, p.categoria
ORDER BY total_vendido DESC;

-- Índices adicionais para otimização de consultas
CREATE INDEX idx_pedidos_data_criacao_status ON pedidos(data_criacao, status);
CREATE INDEX idx_pedido_itens_produto_quantidade ON pedido_itens(produto_id, quantidade);
CREATE INDEX idx_produtos_categoria_ativo ON produtos(categoria, ativo);
CREATE INDEX idx_users_ativo_role ON users(ativo, role);