# Spring Cloud Config Sample

Setup config repository.

```
mkdir -p ~/src/config-repo
cd ~/src/config-repo
git init
echo info.foo > application.properties
git add .
git commit -m "Initial commit"
```

Run config server.

```
cd config-server
mvn spring-boot:run
```

Run sample API using config client.

```
cd config-client
mvn spring-boot:run
```

Try following.

```
curl localhost:8080
```

Refresh client application if config modified.

```
curl localhost:8080/actuator/refresh -X POST
```

