# sqs-messaging-demo

## LocalStackを使った動作確認

LocalStackを起動する。

```
docker compose up -d
```

キューを作る。

```
docker compose up cfn
```

クレデンシャルとリージョンを設定する。

```
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_REGION=ap-northeast-1
```

アプリケーションを起動する。

```
mvn spring-boot:run
```

後始末をする。

```
docker compose down -v
```

