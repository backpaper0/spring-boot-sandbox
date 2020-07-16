# Spring Boot OIDC Example

First, run [Keycloak with Docker](https://hub.docker.com/r/jboss/keycloak/).

```
docker run -d --name kc -p 8000:8080 \
  -e KEYCLOAK_USER=example -e KEYCLOAK_PASSWORD=example \
  -e KEYCLOAK_IMPORT=/tmp/spring-boot-oidc-example.json \
  -v $(pwd)/docker/keycloak/spring-boot-oidc-example.json:/tmp/spring-boot-oidc-example.json \
  jboss/keycloak
```

The next step is to launch Spring Boot application.

```
mvn spring-boot:run
```

Browse [http://localhost:8080](http://localhost:8080).

The following users exist.

- `alice`
- `bob`

Every user's password is `secret`.

