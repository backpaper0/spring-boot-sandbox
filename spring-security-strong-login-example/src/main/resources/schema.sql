create table if not exists users (
	username varchar(100) not null,
	password varchar(500) not null,
	locked boolean not null default(false),
	password_expire date not null,
	login_failure_count int not null default(0),
	active_from date not null,
	active_to date not null,
	last_loggedin_at timestamp not null,
	authority varchar(100) not null,
	primary key (username)
);

