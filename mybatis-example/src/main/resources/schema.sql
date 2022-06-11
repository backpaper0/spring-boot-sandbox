
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
	created_at timestamp,
	updated_at timestamp
);
