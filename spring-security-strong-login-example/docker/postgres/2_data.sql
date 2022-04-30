
-- パスワードはいずれのアカウントも pass
insert into accounts (username, password, locked, password_expiration, login_failure_count, validity_from, validity_to, last_loggedin) values

-- ログインできる
 ('user01', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
,('user02', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
,('user03', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
,('admin', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- ロックされている
,('user20', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', true, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- パスワードの期限切れ
,('user30', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2022-04-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- 有効期間外(現在日時がfromよりも前)
,('user40', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2100-01-01', 0, date'2099-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00')
-- 有効期間外(現在日時がtoよりも後)
,('user50', '7e8cd1bf381b4ee0b87445d2479a6bd95e1c52ed4b7694cfd304e87b8c8c574e908c53cfa7e9c053', false, date'2100-01-01', 0, date'2022-01-01', date'2022-04-01', timestamp'2022-01-01 00:00:00')
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
