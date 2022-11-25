# integration-example

[Spring Integration](https://spring.io/projects/spring-integration)を試す。

## 準備

RabbitMQとFTPサーバーを起動する。

```sh
docker compose up -d
```

ExchangeとQueueを作成してバインドする。

```sh
docker compose exec mq rabbitmqadmin declare exchange name=foo type=direct
```

```sh
docker compose exec mq rabbitmqadmin declare queue name=bar
```

```sh
docker compose exec mq rabbitmqadmin declare binding source=foo destination=bar
```

## 動作確認

```sh
mvn test
```

## 後始末

```sh
docker compose down -v
```

