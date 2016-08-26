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
gradlew bootRun
```

Run sample API using config client.

```
cd config-client
gradlew bootRun
```

Try following.

```
curl localhost:8080
```

Refresh client application if config modified.

```
curl localhost:8080/refresh -X POST
```

