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

### WIP

[Function Mapping rulesセクション](https://docs.spring.io/spring-cloud-function/docs/3.1.3/reference/html/spring-cloud-function.html#_function_mapping_rules)

```sh
curl -s localhost:8080/uppercase/foobar
```

```sh
curl -s localhost:8080/reverse/foobar
```

```sh
curl -s localhost:8080/uppercase,reverse/foobar
```

### WIP

[Type conversion (Content-Type negotiation)](https://docs.spring.io/spring-cloud-function/docs/3.1.3/reference/html/spring-cloud-function.html#_type_conversion_content_type_negotiation)

```sh
curl -s localhost:8080/person -H "Content-Type: application/json" -d '{"name": "Alice"}' | jq
```

```sh
curl -s localhost:8080/person -H "Content-Type: application/json" -d '[{"name": "Alice"}, {"name": "Bob"}]' | jq
```

```sh
curl -s localhost:8080/person/%7B%22name%22%3A%20%22Alice%22%7D | jq
```
