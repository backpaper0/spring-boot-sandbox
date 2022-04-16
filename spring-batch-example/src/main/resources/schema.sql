create table demo1 (
	id int not null,
	content varchar(100) not null,
	primary key (id)
);

create table demo2 (
	id identity,
	status varchar(10) not null
);
