# Cloud Function DEMO

[Spring Cloud Function](https://spring.io/projects/spring-cloud-function)を試す。

## 動作確認

WIP

### WIP

[Standalone Web Applicationsセクション](https://docs.spring.io/spring-cloud-function/docs/3.1.3/reference/html/spring-cloud-function.html#_standalone_web_applications)

```sh
curl -v localhost:8080/helloSupplier
```

```sh
curl -v localhost:8080/helloConsumer -H "Content-Type: text/plain" -d 'foobar'
```

```sh
curl -v localhost:8080/helloConsumer -H "Content-Type: application/json" -d '["foo", "bar", "baz"]'
```

```sh
curl -v localhost:8080/helloFunction -H "Content-Type: text/plain" -d 'foobar'
```

```sh
curl -v localhost:8080/helloFunction -H "Content-Type: application/json" -d '["foo", "bar", "baz"]'
```

```sh
curl -v localhost:8080/helloFunction/foobar
```
