insert into header (id, name) values (1, 'hoge');
insert into header (id, name) values (2, 'fuga');
insert into header (id, name) values (3, 'piyo');

insert into detail (id, parent, name) values (1, 1, 'foo');
insert into detail (id, parent, name) values (2, 1, 'bar');
insert into detail (id, parent, name) values (3, 2, 'baz');
insert into detail (id, parent, name) values (4, 1, 'qux');
insert into detail (id, parent, name) values (5, 3, 'quux');
insert into detail (id, parent, name) values (6, 2, 'foobar');

