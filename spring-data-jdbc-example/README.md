# Spring Data JDBCの例

## 構成

Mavenのプロファイル機能でデフォルトならH2、`pg`プロファイルならPostgreSQLに関する依存関係が設定されるようにしている。

PostgreSQLの場合、Spring BootのDocker Composeサポート機能を使用して自動でPostgreSQLのコンテナが起動するようにしている。
また、テスト実行時にもコンテナを起動させたいためMaven Surefire Pluginの`argLine`プロパティで`spring.docker.compose.skip.in-tests`を`false`に設定している。

## 動作確認

ウィンドウ関数を利用して集約を1度のクエリーで取得する。
PostgreSQLを使用（H2はウィンドウ関数が実装されていないため）。

```bash
mvn -Ppg test -Dtest=#singer1
```

ログを見ると1度のクエリーで取得していることがわかる。
