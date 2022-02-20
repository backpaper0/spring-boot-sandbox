create table song (
	id int generated by default as identity (start with 100),
	title varchar(100),
	singer int,
	primary key (id)
);

create table singer (
	id int generated by default as identity (start with 100),
	name varchar(100),
	primary key (id)
);

alter table song add foreign key (singer) references singer (id);
