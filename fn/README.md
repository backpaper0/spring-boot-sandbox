# Spring Cloud Functionを試す

## ビルドと起動

```console
gradlew build -x check

java -jar build/libs/fn-0.0.1-SNAPSHOT.jar
```

## 関数呼び出し

関数はリクエストURLで指定する。
`,`で区切ってチェーンできるっぽい。

```console
curl -H "content-type:text/plain" :8080/hello -d "world"

curl -H "content-type:text/plain" :8080/upper -d "world"

curl -H "content-type:text/plain" :8080/hello,upper -d "world"
```

