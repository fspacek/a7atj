create sequence note_seq;

create table note (
    id int primary key,
    title varchar(255) not null,
    text varchar(1000) not null
);