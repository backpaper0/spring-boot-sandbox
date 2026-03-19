# circuit-breaker-demo

```sh
docker run -d --name httpbin -p 8000:80 kennethreitz/httpbin
```

```sh
mvn spring-boot:run
```

```sh
curl localhost:8080/demo -G -d status=200
```
