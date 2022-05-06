
insert into table1 (id, name) values (1, 'foo1');
insert into table1 (id, name) values (2, 'bar1');

insert into table2 (id, name, table1_id) values (1, 'foo2', 1);
insert into table2 (id, name, table1_id) values (2, 'bar2', 2);

insert into table3 (id, name, table1_id) values (1, 'foo3a', 1);
insert into table3 (id, name, table1_id) values (2, 'foo3b', 1);
insert into table3 (id, name, table1_id) values (3, 'bar3a', 2);
insert into table3 (id, name, table1_id) values (4, 'bar3b', 2);



insert into table4 values (1, 2, 3, 'foobar', true, 1, timestamp '2022-05-05 23:30:00', date '2022-05-05');



insert into table5 values (1, 'foo'), (2, 'bar'), (3, 'baz'), (4, 'qux');
