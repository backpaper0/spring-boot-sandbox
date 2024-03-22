# telemetry-example

## 動作確認

[SigNozをDocker Composeで起動する](https://github.com/SigNoz/signoz/tree/develop/deploy)。

アプリケーションを起動する。

```
mvn spring-boot:run
```

リクエストを送信する。

```
while true; do (curl -s localhost:8080/rolldice -G -d rolls=3|jq -cS); sleep 1; done
```

SigNozでテレメトリーを確認する。

## その他

- 取得できるメトリクスの一覧は`curl -s http://localhost:8080/actuator/metrics | jq`で確認できる
- 不要なメトリクスを出さないようにするには`management.metrics.enable.<メトリクス>=false`を設定する
    - 特定のメトリクスだけを出すには`management.metrics.enable.all=false`を設定してから個別のメトリクスを`true`にする

## 参考

- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.supported
- https://github.com/open-telemetry/opentelemetry-java-instrumentation/tree/v2.2.0/instrumentation/spring
- https://github.com/open-telemetry/opentelemetry-java/blob/v1.36.0/sdk-extensions/autoconfigure/README.md
- https://opentelemetry.io/docs/languages/java/automatic/spring-boot/
