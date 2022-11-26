# security-two-authn-api-example

異なるパスに異なるセキュリティの設定を行う例。

こんな感じでセキュリティの設定を行なってる。

|パス|`alice`|`bob`|
|---|---|---|
|`/customers/**`|OK|NG|
|`/orders/**`|NG|OK|

これは200が返る。

```sh
curl localhost:8080/customers --user alice:secret
```

```sh
curl localhost:8080/orders --user bob:secret
```

これは401が返る。

```sh
curl localhost:8080/customers --user bob:secret
```

```sh
curl localhost:8080/orders --user alice:secret
```

