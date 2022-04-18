
insert into users (username, password, locked, password_expire, login_failure_count, active_from, active_to, last_loggedin_at, authority) values

-- パスワードは pass1234

('user1', '{bcrypt}$2a$10$NJxwtj7hAEeETtHUt1EpsestoPfej9DSv1xHVRBZcCr03Mn0agSW.', false, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00', 'USER')
-- ロックされている
,('user2', '{bcrypt}$2a$10$NJxwtj7hAEeETtHUt1EpsestoPfej9DSv1xHVRBZcCr03Mn0agSW.', true, date'2100-01-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00', 'USER')
-- パスワードの期限切れ
,('user3', '{bcrypt}$2a$10$NJxwtj7hAEeETtHUt1EpsestoPfej9DSv1xHVRBZcCr03Mn0agSW.', false, date'2022-04-01', 0, date'2022-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00', 'USER')
-- 有効期間外(1)
,('user4', '{bcrypt}$2a$10$NJxwtj7hAEeETtHUt1EpsestoPfej9DSv1xHVRBZcCr03Mn0agSW.', false, date'2100-01-01', 0, date'2099-01-01', date'2100-01-01', timestamp'2022-01-01 00:00:00', 'USER')
-- 有効期間外(2)
,('user5', '{bcrypt}$2a$10$NJxwtj7hAEeETtHUt1EpsestoPfej9DSv1xHVRBZcCr03Mn0agSW.', false, date'2100-01-01', 0, date'2022-01-01', date'2022-04-01', timestamp'2022-01-01 00:00:00', 'USER')
;

