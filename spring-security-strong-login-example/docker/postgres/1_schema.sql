create table accounts (
	-- ユーザー名
	username varchar(100) not null,
	-- ロックされていることを表すフラグ
	locked boolean not null default(false),
	-- ログイン失敗回数
	login_failure_count int not null default(0),
	-- 有効期間(from)
	validity_from date not null,
	-- 有効期間(to)
	validity_to date,
	-- 最終ログイン日時
	last_loggedin timestamp not null,
	primary key (username)
);

create table account_passwords (
	-- ユーザー名
	username varchar(100) not null,
	-- パスワード
	password varchar(500) not null,
	-- パスワードの有効期限
	password_expiration date not null,
	primary key (username)
);

create table authorities (
	-- ユーザー名
	username varchar(100) not null,
	-- 権限
	authority varchar(100) not null,
	primary key (username, authority)
);
