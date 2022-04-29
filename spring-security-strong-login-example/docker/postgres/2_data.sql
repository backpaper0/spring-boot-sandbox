
-- パスワードはいずれのアカウントも pass
insert into accounts (username, password, locked, password_expiration, login_failure_count, validity_from, validity_to, last_loggedin) values

-- ログインできる
 ('user01', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
,('user02', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
,('user03', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
,('admin', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- ロックされている
,('user20', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', true, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- パスワードの期限切れ
,('user30', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2022-04-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- 有効期間外(現在日時がfromよりも前)
,('user40', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2100-01-01', 0, date'2099-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- 有効期間外(現在日時がtoよりも後)
,('user50', '{bcrypt}$2a$10$Blf7Ko0OBpcZty4aucOUfun41lKCkhimnYABhE/qXlee6PSM/46Ma', false, date'2100-01-01', 0, date'2022-01-01', date'2022-04-01', timestamp'2022-01-01 00:00:00')
;

insert into authorities (username, authority) values
-- 権限1
 ('user01', 'AUTH1')
-- 権限2
,('user02', 'AUTH2')
-- 複数の権限
,('user03', 'AUTH1')
,('user03', 'AUTH2')
-- 管理権限(のつもり)
,('admin', 'ADMIN')
-- 以下、ログインできないアカウントたち
,('user20', 'GENERAL')
,('user30', 'GENERAL')
,('user40', 'GENERAL')
,('user50', 'GENERAL')
;
