create table if not exists accounts (
	-- ユーザー名
	username varchar(100) not null,
	-- パスワード
	password varchar(500) not null,
	-- ロックされていることを表すフラグ
	locked boolean not null default(false),
	-- パスワードの有効期限
	password_expiration date not null,
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

create table authorities (
	username varchar(100) not null,
	authority varchar(100) not null,
	primary key (username, authority)
);
