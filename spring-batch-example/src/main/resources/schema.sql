create table demo1 (
	id int not null,
	content varchar(100) not null,
	primary key (id)
);

create table demo2 (
	id identity,
	status varchar(10) not null
);

create table demo3 (
	id identity,
	content1 varchar(10) not null,
	content2 varchar(10) not null
);
