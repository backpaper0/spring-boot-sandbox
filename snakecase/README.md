# スネークケースのリクエストパラメーターをモデルにバインドする

## やりたいこと

スネークケースでやってくるリクエストパラメーターをPOJOのモデルにバインドしたい。

## アプローチ

[ServletModelAttributeMethodProcessor](https://docs.spring.io/spring/docs/4.3.11.RELEASE/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ServletModelAttributeMethodProcessor.html)を拡張してその中でスネークケースのパラメーターをキャメルケースでも取得できるようにコピーする。

## 確認方法

手動で確認する場合はアプリケーションを起動してからcurlを実行すると良い。

アプリケーションの起動は次のコマンド。

```
mvnw spring-boot:run
```

curlで試すのは次の通り。
クエリパラメーターがスネークケースになっている。

```
curl "localhost:8080?foo_bar=helloworld"
```

他にはテストを実行することでも動作を確認できる。

```
mvnw test
```

## 思いつく他のアプローチ

* モデルのプロパティを思い切ってスネークケースにする
* パラメーターのコピーをサーブレットフィルターでする

