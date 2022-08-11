create table example (
	id int primary key,
	content varchar(100)
);

insert into example (id, content)
select
	generate_series,
	'initial value'
from
	generate_series(1, 20)
;
