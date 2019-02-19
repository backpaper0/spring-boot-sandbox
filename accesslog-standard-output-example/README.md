# accesslog-standard-output-example

Try following curl:

```
curl localhost:8080/foo
curl localhost:8080/bar
```

log example:

```
2019-02-19 21:07:27.832  INFO 7721 --- [nio-8080-exec-1] com.example.AccessLogValve               : 0:0:0:0:0:0:0:1 - - [19/Feb/2019:21:07:27 +0900] "GET /foo HTTP/1.1" 200 3
2019-02-19 21:07:31.241  INFO 7721 --- [nio-8080-exec-2] com.example.AccessLogValve               : 0:0:0:0:0:0:0:1 - - [19/Feb/2019:21:07:31 +0900] "GET /bar HTTP/1.1" 200 3
```

