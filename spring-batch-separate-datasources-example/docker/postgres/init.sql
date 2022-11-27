create table example (
	id integer primary key,
	content integer not null
);

insert into example (id, content)
select
	generate_series,
	0
from
	generate_series(1, 20)
;
