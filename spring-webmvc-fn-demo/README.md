# spring-webmvc-fn-demo

Spring Web MVCのFunctional Endpointsを試す。

## 起動

```sh
mvn spring-boot:run
```

## curlで試す

GETリクエスト。

```sh
curl localhost:8080/hello
```

POSTリクエスト。

```sh
curl localhost:8080/hello -d name=foobar
```

Server-Sent Eventsも。

```sh
curl -N localhost:8080/sse
```

