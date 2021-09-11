# consul-example

```sh
ls | grep service | xargs -t -I {} mvn -f {} spring-boot:build-image
```

```sh
docker compose up -d
```

http://localhost:8500

```sh
while true; do curl -s localhost:8080/id; echo ""; sleep 1; done
```

```
{"id":"foo"}
{"id":"bar"}
{"id":"baz"}
{"id":"foo"}
{"id":"bar"}
{"id":"baz"}
```

```sh
docker compose stop service2c
```

```
{"id":"foo"}
{"id":"bar"}
{"timestamp":"2021-09-11T06:13:08.847+00:00","status":500,"error":"Internal Server Error","path":"/id"}
{"id":"bar"}
{"id":"foo"}
{"id":"bar"}
{"id":"foo"}
{"id":"bar"}
{"id":"foo"}
{"id":"bar"}
```

```sh
docker compose start service2c
```

```
{"id":"foo"}
{"id":"bar"}
{"id":"foo"}
{"id":"bar"}
{"id":"foo"}
{"id":"bar"}
{"id":"baz"}
{"id":"foo"}
{"id":"bar"}
{"id":"baz"}
{"id":"foo"}
{"id":"bar"}
{"id":"baz"}
```

