# sqs-messaging-demo

## LocalStackを使った動作確認

LocalStackを起動する。

```
docker run -d --name localstack -p 4566:4566 -p 4571:4571 localstack/localstack
```

キューを作っておく。
キューの名前は`demo-queue`。

```
awslocal sqs create-queue --queue-name demo-queue
```

クレデンシャルとリージョンを設定しておく。

```
export AWS_ACCESS_KEY_ID=test
export AWS_SECRET_ACCESS_KEY=test
export AWS_REGION=ap-northeast-1
```

アプリケーションを起動する。

```
mvn spring-boot:run
```

