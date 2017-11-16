create sequence NOTE_SEQ;
create sequence OWNER_SEQ;
create table Note (id bigint not null, text varchar(255), title varchar(255), owner_id bigint not null, primary key (id));
create table Owner (id bigint not null, email varchar(255) not null, password varchar(255) not null, primary key (id));
alter table Owner add constraint UK_rlkynff9swh6bj0inqug8eio2 unique (email);
alter table Note add constraint FKi39kjje5i7uotoc76fnlu2m0s foreign key (owner_id) references Owner;