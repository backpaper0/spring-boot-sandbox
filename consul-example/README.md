# consul-example

```sh
ls | grep service | xargs -t -I {} mvn -f {} spring-boot:build-image
```

```sh
docker compose up -d --scale service2=3
```

http://localhost:8500

```sh
while true; do curl -s localhost:8080/id; echo ""; sleep 1; done
```

```
{"id":"service2-1a0e6c258b9589cca1dfdbcc9bb7adec"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-1a0e6c258b9589cca1dfdbcc9bb7adec"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
```

```sh
docker stop consul-example_service2_3
```

```
{"id":"service2-1a0e6c258b9589cca1dfdbcc9bb7adec"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
```

```sh
docker start consul-example_service2_3
```

```
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-836c50b812bdcb584a27f99372b1c95d"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-836c50b812bdcb584a27f99372b1c95d"}
{"id":"service2-a457d5fd491cb485f93e4b9baface35b"}
{"id":"service2-2f56b081b401c02e50fbf77d86998523"}
{"id":"service2-836c50b812bdcb584a27f99372b1c95d"}
```

