# Spring Securityで盛りだくさんのログイン

DBを起動する。

```bash
docker compose up -d
```

アプリケーションを実行する。

```bash
mvn spring-boot:run
```

- [アプリケーション](http://localhost:8080)
- [pgAdmin](http://localhost:8082)
  - pgAdmin自体のログインは`demo@example.com`/`password`
  - `/pgpass`が効いていないっぽくてDBのパスワードも求められる
  - DBのパスワードは`pass1234`

アプリケーションのユーザーは次のバリエーションがある。

|ユーザー名|パスワード|説明|
|---|---|---|
|`user01`|`pass`|ログインできる。`AUTH1`という権限を持っている。|
|`user02`|`pass`|ログインできる。`AUTH2`という権限を持っている。|
|`user03`|`pass`|ログインできる。`AUTH1`と`AUTH2`の両権限を持っている。|
|`admin`|`pass`|ログインできる。`ADMIN`という権限を持っており、管理者という扱い。|
|`user20`|`pass`|ログインできない。アカウントがロックされている。|
|`user30`|`pass`|ログインできない。パスワードの有効期限が切れている。|
|`user40`|`pass`|ログインできない。アカウントの有効期間外（現在日時が有効期間よりも前）。|
|`user50`|`pass`|ログインできない。アカウントの有効期間外（現在日時が有効期間よりも後）。|

なお、ログインできるユーザーであっても3回ログインに失敗するとアカウントがロックされてログインできなくなる。

TODO コードの説明

TODO エラー時のメッセージをカスタマイズしたい場合はどうする？

## UserDetailsServiceのモックを使う

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="-Dapp.user-details-service.mock.enabled=true"
```
