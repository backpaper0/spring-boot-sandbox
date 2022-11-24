# circuit-breaker-demo

```sh
docker run -d --name hello -p 8000:80 -v $PWD/src/nginx:/usr/share/nginx/html nginx
```

```sh
mvn spring-boot:run
```

```sh
while true; do curl -s localhost:8080/hello; sleep 1; done
```

```
Hello, Nginx!
Hello, Nginx!
Hello, Nginx!
Hello, Nginx!
Hello, Nginx!
```

```sh
docker stop hello
```

```
Hello, Circuit Breaker!!!
Hello, Circuit Breaker!!!
Hello, Circuit Breaker!!!
Hello, Circuit Breaker!!!
Hello, Circuit Breaker!!!
```

```sh
docker start hello
```

```
Hello, Circuit Breaker!!!
Hello, Circuit Breaker!!!
Hello, Nginx!
Hello, Nginx!
Hello, Nginx!
```
