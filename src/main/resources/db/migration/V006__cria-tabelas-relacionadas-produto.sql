create table categoria_produto (
	id bigint not null auto_increment, 
    descricao varchar(25) not null, 
    restaurante_id bigint not null, 
    primary key (id)
)engine=InnoDB default charset=utf8;

create table produto (
	id bigint not null auto_increment,
	nome varchar(25) not null,
    descricao varchar(80) not null, 
    disponivel bit not null, 
    preco decimal(19,2) not null, 
    categoria_produto_id bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table complemento (
	id bigint not null auto_increment,
    descricao varchar(30) not null,
    qtd_minima smallint(6) not null,
    qtd_maxima smallint(6) not null,
    obrigatorio bit not null,
    produto_id bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table item_complemento (
	id bigint not null auto_increment, 
    nome varchar(45) not null,
    preco decimal(19,2) not null,
    disponivel bit not null,
    complemento_id bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table promocao_produto (
	id bigint not null auto_increment,
    ativa bit not null,
    valor_produto_descricao decimal(19,2) not null,
    porcentagem_desconto bigint not null,
    produto_id bigint not null, 
    primary key (id)
) engine=InnoDB default charset=utf8;

create table dia_semana (
	id bigint not null auto_increment,
    dia_semana varchar(20) not null,
    primary key (id)
) engine=InnoDB default charset=utf8;

create table produto_dia_semana (
	produto_id bigint not null, 
	dia_disponivel_id bigint not null, 
	primary key (produto_id, dia_disponivel_id)
) engine=InnoDB default charset=utf8;


alter table categoria_produto add constraint fk_categoria_produto_restaurante foreign key (restaurante_id) references restaurante (id);

alter table produto add constraint fk_produto_categoria_produto foreign key (categoria_produto_id) references categoria_produto (id);

alter table complemento add constraint fk_complemento_produto foreign key (produto_id) references produto (id);

alter table item_complemento add constraint fk_item_complemento_complemento foreign key (complemento_id) references complemento (id);

alter table promocao_produto add constraint fk_promocao_produto_produto foreign key (produto_id) references produto (id);

alter table produto_dia_semana add constraint fk_produto foreign key (produto_id) references produto (id);

alter table produto_dia_semana add constraint fk_dia_semana foreign key (dia_disponivel_id) references dia_semana (id);

