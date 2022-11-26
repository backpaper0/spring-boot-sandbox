# metrics-example

## 動作確認

Prometheusを起動する。

```
docker compose up -d
```

アプリケーションを起動する。

```
mvn spring-boot:run
```

リクエストを送信する。

```
while true; do (curl -s localhost:8080/count|jq -cS); sleep 1; done
```

[prometheus](http://localhost:9090/graph?g0.expr=example_count_1&g0.tab=0&g0.stacked=1&g0.show_exemplars=0&g0.range_input=5m&g1.expr=example_count_2&g1.tab=0&g1.stacked=1&g1.show_exemplars=0&g1.range_input=5m&g2.expr=example_count_3_total&g2.tab=0&g2.stacked=1&g2.show_exemplars=0&g2.range_input=5m)でメトリクスを確認する。

## その他

- 取得できるメトリクスの一覧は`curl -s http://localhost:8080/actuator/metrics | jq`で確認できる
- 不要なメトリクスを出さないようにするには`management.metrics.enable.<メトリクス>=false`を設定する
    - 特定のメトリクスだけを出すには`management.metrics.enable.all=false`を設定してから個別のメトリクスを`true`にする

## 参考

- https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.metrics.supported

