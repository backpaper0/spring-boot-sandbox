# Spring Cloud Functionを試す

## ビルドと起動

```console
mvn package

java -jar target/cloud-function-example-0.0.1-SNAPSHOT.jar
```

## 関数呼び出し

関数はリクエストURLで指定する。
`,`で区切ってチェーンできるっぽい。

```console
curl -H "content-type:text/plain" localhost:8080/hello -d "world"

curl -H "content-type:text/plain" localhost:8080/upper -d "world"

curl -H "content-type:text/plain" localhost:8080/hello,upper -d "world"
```

