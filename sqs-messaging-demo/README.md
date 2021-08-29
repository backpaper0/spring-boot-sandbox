# sqs-messaging-demo

## localstackを使った動作確認

`latest`(`0.12.17`かな？)だとアプリケーション実行時、SQSに繋ぐときに`The specified queue does not exist for this wsdl version.`というエラーが発生するため、バージョンを`0.12.11`に落とす。
`localstack start`すると`latest`のコンテナイメージを使おうとするため、`0.12.11`に`latest`タグを付ける。

```
# workaround
docker pull localstack/localstack:0.12.11
docker tag localstack/localstack:0.12.11 localstack/localstack:latest
```

上記のワークアラウンドができたら`localstack start`する。

```
localstack start
```

キューを作っておく。
キューの名前は`demo-queue`。

```
aws sqs create-queue --queue-name demo-queue --endpoint-url http://localhost:4566
```

アプリケーションを起動する。

```
mvn spring-boot:run
```

