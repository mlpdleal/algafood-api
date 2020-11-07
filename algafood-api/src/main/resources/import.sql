insert into cozinha (nome) values ('Chinesa');
insert into cozinha (nome) values ('Indiana');
insert into cozinha (nome) values ('Tailandesa');

insert into estado (nome) values ('Espirito Santo');
insert into estado (nome) values ('São Paulo');
insert into estado (nome) values ('Distrito Federal');

insert into cidade (nome, estado_id) values ('Vitória', 1);
insert into cidade (nome, estado_id) values ('Cachoeiro de Itapemirim', 1);
insert into cidade (nome, estado_id) values ('São Paulo', 2);
insert into cidade (nome, estado_id) values ('Santos', 2);
insert into cidade (nome, estado_id) values ('Brasília', 3);

insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Careca', 5.0, 1, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Long', 8.0, 2, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Taigourmet', 8.0, 3, utc_timestamp, utc_timestamp);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Frango xadrez', 'Frango cozido no molho shoyo com legumes', 18.90, true, 1)
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Arroz colorido', 'Arroz com legumes, presunto e ovos', 11.90, true, 1)
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Frango ao molho agridoce', 'Frango ao molho agridoce', 11.90, false, 1)
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Frango xadrez com amendoim', 'Frango cozido no molho shoyo com legumes e amendoin', 21.90, true, 2)
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Arroz colorido', 'Arroz com legumes, presunto e ovos', 11.90, true, 2)
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Arroz da tailandia', 'Arroz especial com especiarias', 18.90, true, 3)

insert into permissao (nome, descricao) values('Oreia seca', 'Só pode consultar');
insert into permissao (nome, descricao) values('Bigode grosso', 'Pode fazer tudo');

insert into forma_pagamento (descricao) values ('Cartao de crédito');
insert into forma_pagamento (descricao) values ('Dinheiro');
insert into forma_pagamento (descricao) values ('Cartao de débito');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 2), (2, 3), (3, 1), (3, 2)