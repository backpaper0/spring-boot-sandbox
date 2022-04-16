create table if not exists stop_signals (
	id int not null,
	stopped boolean not null default(false),
	primary key (id)
);

