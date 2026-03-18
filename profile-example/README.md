# プロファイルの切り替えから連鎖して実装クラスを切り替える例

## まずは動作確認

普通にアプリケーションを起動する。

```sh
mvn spring-boot:run
```

http://localhost:8080/swagger-ui.html を開いて`GET /greeting`を実行する。

```json
{
  "message": "☀️おはようございます"
}
```

アプリケーションを止めて、次はプロファイルを指定して起動する。

```sh
mvn spring-boot:run -Psera
```

http://localhost:8080/swagger-ui.html を開いて`GET /greeting`を実行する。

```json
{
  "message": "こんばんは⭐️"
}
```

## 仕組み

実装クラスの切り替えは次の仕組みを組み合わせて(連鎖させて)実現している。

- Mavenのプロファイル
- Springのプロファイル
- Springのアプリケーションプロパティ

まず、アプリケーションの起動コマンドではMavenのプロファイルを利用している。
二度目に起動したコマンドの`-Psera`がそれにあたり、`sera`プロファイルでMavenを起動している。

`pom.xml`を見ると`id`属性が`sera`となっている`profile`要素があることがわかる。
`profile`要素を見ると`spring-boot.run.profiles`プロパティの値を`yoru`に設定していることがわかる。

`spring-boot.run.profiles`はSpringのプロファイルを指定できるプロパティ。
つまり、`yoru`プロファイルでSpring Bootアプリケーションを起動している。

Spring BootアプリケーションはSpringのプロファイルに対応した設定ファイルを読み込むため、`application-yoru.yaml`が読み込まれる。
なお、`application.yaml`はどのプロファイルであっても必ず読み込まれる。

`application-yoru.yaml`の中で`app.greeting.time-of-day`というアプリケーションプロパティが`evening`という値に設定されている。
`EveningGreetingService`を見ると`ConditionalOnProperty`アノテーションで`app.greeting.time-of-day`プロパティが`evening`のときに有効化されるよう定義されている。

以上がこのリポジトリにおける実装クラス切り替えの仕組み。

## 余談

`spring-boot-docker-compose`を使用すれば`spring.docker.compose.profiles.active`プロパティでDocker Composeのプロファイルも連鎖させることができる。

アプリケーションプロパティは`Value`アノテーションでインジェクションするのではなく、`ConfigurationProperties`アノテーションを付けたクラスを定義してそのクラスをインジェクションする方が良い。
型安全だし、`spring-boot-configuration-processor`を利用して設定ファイルで補完が効くようになる。
