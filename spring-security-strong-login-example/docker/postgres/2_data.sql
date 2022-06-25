
-- パスワードはいずれのアカウントも pass
insert into accounts (username, locked, login_failure_count, validity_from, validity_to) values

-- ログインできる
 ('user01', false, 0, date'2022-01-01', null)
,('user02', false, 0, date'2022-01-01', null)
,('user03', false, 0, date'2022-01-01', null)
,('admin',  false, 0, date'2022-01-01', null)
-- ロックされている
,('user20', true,  0, date'2022-01-01', date'2100-01-01')
-- パスワードの期限切れ
,('user30', false, 0, date'2022-01-01', date'2100-01-01')
-- 有効期間外(現在日時がfromよりも前)
,('user40', false, 0, date'2099-01-01', date'2100-01-01')
-- 有効期間外(現在日時がtoよりも後)
,('user50', false, 0, date'2022-01-01', date'2022-04-01')
;

insert into account_passwords (username, password, password_expiration) values
 ('user01', 'n2RC5Ml7Xc/e0AMIC18SB9BouiF0XmLOjAiyhUqjzKkSFyyseW5DkZafvBbD1uGs', date'2100-01-01')
,('user02', 'YXFgyChWY+C+KsBAqT1chO0CO+c7bOklCToJ/z5+41ZFIflYHc1Vsz6lEYdjfI98', date'2100-01-01')
,('user03', 'pwQg/3vzcL+MOC924eaOSgGy4OduCsYQA5Ge9whJbYhPpo3xrlwuxd2G8ozlFPqK', date'2100-01-01')
,('admin',  'oMJkJgLaSSyodTr+iALXBFEieeIpmmsLPaOfq0MuWgIzmSMdVkQfAkTyOpUdk2TN', date'2100-01-01')
,('user20', '85Q5XT5vTn7ylmLQOCiz8RUIiQlg3iW6Q/zEZFUYvpzlin4x8LeUfg6bNiBdACEh', date'2100-01-01')
,('user30', 'ZwyGv1UFA5YF+dfJfbmyHZ/n9GsnkvKA7PWUztUnORdLgQWylOkRgdbcirX3OaiI', date'2022-04-01')
,('user40', 'JrIKSoaroToJrCa0aTQBFnK8QkltWN5pYED05AKrJvLwMH1PfezQHYVC3P5v7axh', date'2100-01-01')
,('user50', 'vSlqhAEbcBUE574bM01rYQgttVpL+64uRcYZtkhDjUGkD1ryNIfEo40bUigtGCHR', date'2100-01-01')
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
,('user20', 'AUTH1')
,('user30', 'AUTH1')
,('user40', 'AUTH1')
,('user50', 'AUTH1')
;
