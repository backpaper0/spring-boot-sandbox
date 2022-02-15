```
mvn spring-boot:run
```

```
docker compose up -d
```

```
while true; do (curl -s localhost:8080/count|jq); sleep 1; done
```

[prometheus](http://localhost:9090/graph?g0.expr=example_count_1&g0.tab=0&g0.stacked=1&g0.show_exemplars=0&g0.range_input=5m&g1.expr=example_count_2&g1.tab=0&g1.stacked=1&g1.show_exemplars=0&g1.range_input=5m&g2.expr=example_count_3_total&g2.tab=0&g2.stacked=1&g2.show_exemplars=0&g2.range_input=5m)

