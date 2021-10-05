# distributed-tracing-demo\_sleuth-zipkin\_cloud-stream

`span`の`name`が`handle`/`function`/`send`になってしまう問題。

`org.springframework.cloud.sleuth.instrument.messaging.TraceFunctionAroundWrapper#doApply`を読んだ感じだと`supplier`の場合のみ関数名を`span`に設定しているように見える。

`org.springframework.cloud.sleuth.instrument.messaging.TraceMessageHandler`をカスタマイズできれば任意の名前を設定できそうだけどカスタマイズできる余地が無いように見える。

---

関数の引数を`org.springframework.messaging.Message<T>`にして`org.springframework.cloud.sleuth.instrument.messaging.MessagingSleuthOperators`を使えば`span`を取得できるので任意の`name`を設定できる。

