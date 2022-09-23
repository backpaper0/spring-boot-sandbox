# accesslog-standard-output-example

https://logback.qos.ch/access.html

Try following curl:

```
curl localhost:8080/foo
curl localhost:8080/bar
```

log example:

```
0:0:0:0:0:0:0:1 - - - 23/9月/2022:13:15:31 +0900 "GET /foo HTTP/1.1" 200 3
0:0:0:0:0:0:0:1 - - - 23/9月/2022:13:15:33 +0900 "GET /bar HTTP/1.1" 200 3
```

