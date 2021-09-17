# Cloud Config DEMO

[Spring Cloud Config](https://spring.io/projects/spring-cloud-config)のデモ。

## 準備

設定値を格納するためのRedisを起動する。

```sh
docker run -d --name config -p 6379:6379 redis
```

初期値を登録しておく。
※なお`my.foobar`は登録しているけど使っていない。今後使うような修正を入れるかも。

```sh
docker exec config redis-cli HMSET my-config my.text "Hello World" my.foobar "foo"
```

[Spring Cloud Bus](https://spring.io/projects/spring-cloud-bus)のためRabbitMQを起動する。

```sh
docker run -d --name bus -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

コンフィグサーバーとクライアントアプリケーションを起動する。

```sh
mvn -f server spring-boot:run
```

```sh
mvn -f client spring-boot:run
```

## 動作確認

`curl`と`jq`で動作確認をしてみる。

```sh
curl -s localhost:8080/demo | jq
```

出力は次の通り。

```json
{
  "text": "Hello World"
}
```

`my.text`プロパティの値を変更してみる。

```sh
docker exec config redis-cli HSET my-config my.text "Hello Spring Cloud Config"
```

この時点では設定値は変わっていない。

```sh
curl -s localhost:8080/demo | jq
```

```json
{
  "text": "Hello World"
}
```

Spring Cloud Busの`busrefresh`エンドポイントを利用してクライアントアプリケーションをリフレッシュする。

```sh
curl -s localhost:8888/actuator/busrefresh -XPOST
```

再度クライアントアプリケーションへ`curl`をすると設定値が変わっていることがわかる。

```sh
curl -s localhost:8080/demo | jq
```

```json
{
  "text": "Hello Spring Cloud Config"
}
```
