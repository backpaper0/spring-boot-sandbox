# Spring Web MVCの例外ハンドリングを試す

http://localhost:8080 をWebブラウザで開いて色々試す。

APIは`curl`で試す。

正常なレスポンスを返すエンドポイント。

```sh
curl localhost:8080/api/a
```

ハンドリングされる例外をスローするエンドポイント。

```sh
curl localhost:8080/api/b
```

ハンドリングされない例外をスローするエンドポイント。

```sh
curl localhost:8080/api/c
```

