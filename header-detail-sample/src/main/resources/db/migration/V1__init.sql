create table header (
    id int,
    name varchar(100),
    primary key(id)
);

create table detail (
    id int,
    parent int,
    name varchar(100),
    primary key(id)
);
