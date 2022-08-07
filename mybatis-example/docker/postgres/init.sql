
create table table1 (
	id int primary key,
	name varchar(100)
);

create table table2 (
	id int primary key,
	name varchar(100) not null,
	table1_id int not null,
	foreign key (table1_id) references table1 (id)
);

create table table3 (
	id int primary key,
	name varchar(100) not null,
	table1_id int not null,
	foreign key (table1_id) references table1 (id)
);



create table table4 (
	id int primary key,
	long_value bigint not null,
	big_decimal_value bigint not null,
	string_value varchar(100) not null,
	bool_value1 boolean not null,
	bool_value2 boolean not null,
	date_time_value timestamp not null,
	date_value date not null
);



create table table5 (
	id int primary key,
	name varchar(100)
);



create table table6 (
	id int primary key,
	name varchar(100),
	created_by varchar(100),
	created_at timestamp,
	updated_by varchar(100),
	updated_at timestamp
);



insert into table1 (id, name) values (1, 'foo1');
insert into table1 (id, name) values (2, 'bar1');

insert into table2 (id, name, table1_id) values (1, 'foo2', 1);
insert into table2 (id, name, table1_id) values (2, 'bar2', 2);

insert into table3 (id, name, table1_id) values (1, 'foo3a', 1);
insert into table3 (id, name, table1_id) values (2, 'foo3b', 1);
insert into table3 (id, name, table1_id) values (3, 'bar3a', 2);
insert into table3 (id, name, table1_id) values (4, 'bar3b', 2);



insert into table4 values (1, 2, 3, 'foobar', true, true, timestamp '2022-05-05 23:30:00', date '2022-05-05');



insert into table5 values (1, 'foo'), (2, 'bar'), (3, 'baz'), (4, 'qux');



create sequence numbering1;
create sequence numbering2;
create sequence numbering3;
create sequence numbering4;
create sequence numbering5;
create sequence numbering6;
