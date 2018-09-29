# R2DBC example

- https://r2dbc.io

## Setup database

Start PostgreSQL using Docker.

```
docker run -d -e POSTGRES_USER=example -p 5432:5432 --name=example-db postgres:10.5
```

Connect to database by `psql`.

```
psql -h localhost -U example example
```

Create table and insert some record.

```
CREATE TABLE messages (id SERIAL PRIMARY KEY, text VARCHAR(100));
INSERT INTO messages (text) VALUES ('foo');
INSERT INTO messages (text) VALUES ('bar');
INSERT INTO messages (text) VALUES ('baz');
```

## Run application

By `spring-boot-maven-plugin`.

```
mvn spring-boot:run
```

Or by `java -jar` command.

```
mvn package
java -jar target/reactive-postgres-example-0.0.1-SNAPSHOT.jar
```

## Try curl

JSON

```
curl -v localhost:8080/messages
curl -v localhost:8080/messages/by_client
```

JSON Stream

```
curl -v -H "Accept: application/stream+json" localhost:8080/messages
curl -v -H "Accept: application/stream+json" localhost:8080/messages/by_client
```

Server-Sent Events

```
curl -v -H "Accept: text/event-stream" localhost:8080/messages
curl -v -H "Accept: text/event-stream" localhost:8080/messages/by_client
```
