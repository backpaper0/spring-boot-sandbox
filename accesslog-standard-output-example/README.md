# accesslog-standard-output-example

アクセスログを標準出力に出力する例。

- https://logback.qos.ch/access.html

アプリケーションを起動する。

```
mvn spring-boot:run
```

次の`curl`を試してアクセスログを確認する。

```
curl localhost:8080/foo
curl localhost:8080/bar
```

次のようなログが出ているはず。

```
0:0:0:0:0:0:0:1 - - - 23/9月/2022:13:15:31 +0900 "GET /foo HTTP/1.1" 200 3
0:0:0:0:0:0:0:1 - - - 23/9月/2022:13:15:33 +0900 "GET /bar HTTP/1.1" 200 3
```

