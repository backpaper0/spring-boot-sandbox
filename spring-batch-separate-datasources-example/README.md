# spring-batch-separate-datasources-example

Spring Batchのデータソースを分ける例。

20件のデータを取得して10件ずつ更新している(チャンクサイズ=10)。

15件目でエラーを発生させて、11件目〜14件目がロールバックされることを確認する。

## 準備

```sh
docker compose up -d
```

## 動作確認

```sh
mvn spring-boot:run -Dspring-boot.run.arguments=--example.error-id=15
```

データの確認は次のコマンドで行う。

```sh
docker compose exec postgres psql -U postgres -c "select * from example order by id asc"
```

## 後始末

```sh
docker compose down -v
```

