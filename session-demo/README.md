# session-demo

## Startup

Default store-type is JDBC(H2 database).

```
mvn spring-boot:run
```

Redis store also available via 'redis' profile.

```
docker-compose up -d
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=redis"
```

## Operation

Set

```
curl -c target/cookie.txt -b target/cookie.txt -v localhost:8080/hello -d value='Hello world'
```

Get

```
curl -c target/cookie.txt -b target/cookie.txt -v localhost:8080/hello
```

## Secure Cookie

```
mvn spring-boot:run -Dspring-boot.run.arguments="--server.servlet.session.cookie.secure=true"
```

