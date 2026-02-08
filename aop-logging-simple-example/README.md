# AOPを用いたシンプルなロギングの例

サービス層でメソッドの開始・終了をログ出力する。

アプリケーションを起動する。

```bash
mvn spring-boot:run
```

リクエストを送信する。

```bash
# 商品一覧
curl -s localhost:8080/products
# 特定の商品
curl -s localhost:8080/products/1 | jq
```

ログを確認する。

```
2026-02-08T10:39:42.436+09:00  INFO 93075 --- [nio-8080-exec-4] com.example.service.ProductService       : begin - ProductService#getAllProducts
2026-02-08T10:39:42.437+09:00  INFO 93075 --- [nio-8080-exec-4] com.example.service.ProductService       : Fetching all products
2026-02-08T10:39:42.476+09:00  INFO 93075 --- [nio-8080-exec-4] com.example.service.ProductService       : end - ProductService#getAllProducts
2026-02-08T10:39:51.967+09:00  INFO 93075 --- [nio-8080-exec-6] com.example.service.ProductService       : begin - ProductService#getProductById
2026-02-08T10:39:51.967+09:00  INFO 93075 --- [nio-8080-exec-6] com.example.service.ProductService       : Fetching product with id=1
2026-02-08T10:39:51.993+09:00  INFO 93075 --- [nio-8080-exec-6] com.example.service.ProductService       : end - ProductService#getProductById
```
