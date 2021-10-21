# cloud-bus-demo

[Spring Cloud Bus](https://spring.io/projects/spring-cloud-bus)で複数のサービスを一括で設定変更するデモ。

## 準備

まずRabbitMQを起動する。

```sh
docker run -d --name bus -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

次に`service1`、`service2`を起動する。

```sh
mvn -f service1 spring-boot:run
```

```sh
mvn -f service2 spring-boot:run
```

## 初期状態の確認

各サービスに`curl`でリクエストを送信してみる。

まずは`service1`。
なお、わかりやすさのため`jq -S`でソートしつつ整形している。

```sh
curl -s localhost:8080/demo | jq -S
```

レスポンスは次の通り。

```json
{
  "bar": "bar1",
  "foo": "foo1",
  "value1": "2021-09-17T05:56:54.949341",
  "value2": "2021-09-17T05:56:55.846984"
}
```

`foo`と`bar`は`application.properties`で設定した値。

`value1`と`value2`はそれぞれ`DemoObject1`、`DemoObject2`がインスタンス化された日時。
これは`RefreshScope`のコンポーネントがリフレッシュされることを確認するために出力している。
なお、`DemoObject1`がシングルトン、`DemoObject2`が`RefreshScope`となっている。

`service2`も確認しておく。

```sh
curl -s localhost:8180/demo | jq -S
```

レスポンスは次の通り。

```json
{
  "baz": "baz1",
  "foo": "foo1"
}
```

`service1`とは異なり、単に`application.properties`で設定された値を出力している。

## 設定の変更

`service1`にはSpring Boot Actuatorを仕込んであり、`busenv`エンドポイントと`busrefresh`エンドポイントを有効化している。

まず`busenv`エンドポイントで設定値を変更してみる。

```sh
curl localhost:8080/actuator/busenv \
    -H "Content-Type: application/json" \
    -d '{"name": "my.foo", "value": "foo2"}'
```

`service1`の返す値を見てみる。

```sh
curl -s localhost:8080/demo | jq .foo
```

```json
"foo2"
```

設定が反映されている。

次は`service2`。

```sh
curl -s localhost:8180/demo | jq .foo
```

```json
"foo1"
```

こちらは設定が反映されていない。

次のようなログが出ているため設定値自体は変更されていそう。

```
Received remote environment change request. Keys/values to update {my.foo=foo2}
```

`service1`はミュータブルな`DemoProperties`というクラスに`@ConfigurationProperties`を付けて設定値を受け取っているため即時反映されたのだと思う。

`service2`は`@Value`でコントローラーにインジェクションしているため即時反映されないのだろう。

`service2`のコントローラーは`RefreshScope`にしているため`busrefresh`エンドポイントを利用すれば設定値が変更できるはず。

## リフレッシュする

というわけで`busrefresh`エンドポイントで各サービスをリフレッシュしてみる。

```sh
curl -s localhost:8080/actuator/busrefresh -XPOST
```

`service2`の`foo`も設定値が変更された様子。

```sh
curl -s localhost:8180/demo | jq .foo
```

```json
"foo2"
```

それでは`service1`のすべての値を確認してみる。

```sh
curl -s localhost:8080/demo | jq -S              
```

```json
{
  "bar": "bar1",
  "foo": "foo2",
  "value1": "2021-09-17T05:56:54.949341",
  "value2": "2021-09-17T06:11:48.585093"
}
```

初期状態の値を再掲する。

```json
{
  "bar": "bar1",
  "foo": "foo1",
  "value1": "2021-09-17T05:56:54.949341",
  "value2": "2021-09-17T05:56:55.846984"
}
```

`foo`が変更されているのは先ほど確認した。
`value1`は変化がないのに対して`value2`は新しい値になっている。
これで`RefreshScope`のコンポーネントがリフレッシュされたことが確認できた。
