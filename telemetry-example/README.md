# telemetry-example

## 動作確認

[OpenObserve](https://openobserve.ai/)を起動する。

```
docker compose up -d
```

アプリケーションを起動する。

```
mvn spring-boot:run
```

リクエストを送信する。

```
while true; do (curl -s localhost:8080/rolldice -G -d rolls=3|jq -cS); sleep 1; (curl -s localhost:8080/rolldice -G -d rolls=5 -d player=urgm|jq -cS); sleep 1; done
```

OpenObserveでテレメトリーを確認する。

- http://localhost:5080
- [OrbStack](https://orbstack.dev/)を使っている場合は https://openobserve.telemetry-example.orb.local

ユーザー名とパスワードは次の通り。

- ユーザー名：`root@example.com`
- パスワード：`pass1234`

## その他

- 取得できるメトリクスの一覧は`curl -s http://localhost:8080/actuator/metrics | jq`で確認できる
- 不要なメトリクスを出さないようにするには`management.metrics.enable.<メトリクス>=false`を設定する
    - 特定のメトリクスだけを出すには`management.metrics.enable.all=false`を設定してから個別のメトリクスを`true`にする
- 可視化は[SigNoz](https://signoz.io/)も良さそう
    - [SigNozをDocker Composeで起動する](https://github.com/SigNoz/signoz/tree/develop/deploy)。
    - [JVMメトリクスのダッシュボード](https://github.com/SigNoz/dashboards/blob/main/JVM%20Metrics.json)

## 参考

- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.supported
- https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/v2.2.0/instrumentation/spring
- https://github.com/open-telemetry/opentelemetry-java/blob/v1.36.0/sdk-extensions/autoconfigure/README.md
- https://opentelemetry.io/docs/languages/java/automatic/spring-boot/
