create table DOER
(
    id      numeric not null,
    surname varchar(50) not null,
    name    varchar(50) not null
);

alter table DOER
    add constraint DOER_PK primary key (ID);

-- Create table
create table QUOTE
(
    id      numeric not null,
    id_doer numeric not null,
    text    VARCHAR(2000) not null
);
-- Create/Recreate primary, unique and foreign key constraints
alter table QUOTE
    add constraint QUOTE_PK primary key (ID);

select * from doer;

---------------------------
insert into DOER (id, surname, name)
values (2, 'Медведев', 'Дмитрий');

insert into DOER (id, surname, name)
values (1, 'ЭЙНШТЕЙН', 'АЛЬБЕРТ');
--------------------------------------
insert into QUOTE (id, id_doer, text)
values (100, 1, 'Только дурак нуждается в порядке — гений господствует над хаосом.');

insert into QUOTE (id, id_doer, text)
values (101, 1, 'Образование — это то, что остаётся после того, как забывается всё выученное в школе');

insert into QUOTE (id, id_doer, text)
values (102, 2, 'Денег нет, но вы держитесь');

insert into QUOTE (id, id_doer, text)
values (103, 2, 'Правительство нельзя трясти, как грушу');

select * from quote;

----
create sequence DOER_SEQ
    start with 200
    increment by 1;

create sequence QUOTE_SEQ
    start with 1000
    increment by 1;

select nextval('QUOTE_SEQ');

