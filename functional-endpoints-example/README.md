# functional-endpoints-example

```sh
mvn spring-boot:run
```

```sh
curl localhost:8080/hello
```

```sh
curl localhost:8080/people/alice
```

```sh
curl localhost:8080/people -H "Content-Type: application/json" -d '{"name":"bob"}'
```

