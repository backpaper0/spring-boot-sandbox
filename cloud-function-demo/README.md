# Cloud Function DEMO

[Spring Cloud Function](https://spring.io/projects/spring-cloud-function)を試す。

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

```sh
curl -s localhost:8080/uppercase/foobar
```

```sh
curl -s localhost:8080/reverse/foobar
```

```sh
curl -s localhost:8080/uppercase,reverse/foobar
```

```sh
curl -s localhost:8080/person -H "Content-Type: application/json" -d '{"name": "Alice"}' | jq
```

```sh
curl -s localhost:8080/person -H "Content-Type: application/json" -d '[{"name": "Alice"}, {"name": "Bob"}]' | jq
```
