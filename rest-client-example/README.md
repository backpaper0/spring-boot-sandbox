# REST API Client Example

## curlでAPIを叩く例

トークンを取得する。

```
curl -v localhost:8888/token -d username=hoge -d password=secret
```

一覧。

```
curl -v localhost:8888/tweets -H "Authorization: Bearer <token>
```

投稿。

```
curl -v localhost:8888/tweets -H "Authorization: Bearer <token> -d text=<tweet>
```

