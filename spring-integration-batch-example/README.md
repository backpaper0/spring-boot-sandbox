# spring-integration-batch-example

起動。

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

Spring Integrationに関するコンポーネントの停止。

```sh
docker compose exec db psql -U postgres -c "insert into stop_signals (id) values (1)"
```

