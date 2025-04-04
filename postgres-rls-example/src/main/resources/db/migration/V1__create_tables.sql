-- https://www.postgresql.jp/document/16/html/ddl-rowsecurity.html
-- https://www.postgresql.jp/document/16/html/sql-createpolicy.html

create table animals (id serial primary key, name text, species text);

alter table animals enable row level security;

create policy animals_policy on animals using (species = current_user);

create role neko;
create role koguma;
create role usagi;

grant select on animals to public;
