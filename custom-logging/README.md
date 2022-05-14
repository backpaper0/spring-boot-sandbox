# ロギングのカスタマイズ

これは次のような内容を取り扱っているexampleとなっている。

- TomcatのアクセスログをLogbackで出力する
- アクセスログの出力内容をいくつかカスタマイズする

## exampleの起動・動作確認

`mvn spring-boot:run`で起動して http://localhost:8080 へアクセスする。
ユーザー名・パスワードは`demo`・`pass`。

ログイン後の画面では次のことができる。

- 任意の名前をセッションに格納する（クリアもできる）
    - 格納した値をアクセスログに出力している
- 任意の時間をかけて外部API呼び出しを行う（[HTTPBin](https://httpbin.org/)を利用）
    - リクエストの処理時間から外部APIを呼び出している間の時間を差し引いてアクセスログに出力している
- 他の画面へ移動する
    - （単にアクセスログへ出力されるパスが変わる例を見たかった）

## TomcatのアクセスログをLogbackで出力する

TomcatのアクセスログをLogbackで出力するには`ch.qos.logback.access.tomcat.LogbackValve`を追加し、クラスパス上の`conf/logback-access.xml`で設定を行う。

- 参照 https://logback.qos.ch/access.html

Spring Bootでは`org.springframework.boot.web.server.WebServerFactoryCustomizer`を実装したコンポーネント（型変数には`org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory`をバインドする）でTomcatをカスタマイズできるので、それを利用して`LogbackValve`を追加する。

## アクセスログの出力内容

アクセスログの出力内容のうち、いくつかをカスタマイズする必要がある。

まず`%u`または`%user`で出力できる認証済みユーザー。
Spring Securityを使用しているときは`org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName()`を出力する必要がある。

次に`%S`または`%sessionID`で出力できるセッションID。
Spring Sessionを使用しているときは`org.springframework.session.web.http.SessionRepositoryFilter`で`HttpSession`のラッパーが仕込まれるため、このフィルターよりも後のフィルターで`HttpSession.getId()`で取得したセッションIDを出力する必要がある。

最後に`%D`または`%elapsedTime`で出力できるリクエストの処理に要した時間。
外部APIの呼び出しを行った際、外部APIの処理に要した時間は自システムの処理時間ではないため差し引きたい場合がある。

カスタマイズの方法としては`ch.qos.logback.access.pattern.AccessConverter`の実装クラスを用意して、`logback-access.xml`で`conversionRule`要素を書く。
`AccessConverter`の中では`HttpServletRequest`を取得できるため、サーブレットフィルターなどで必要な値を`HttpServletRequest#setAttribute`で設定しておき、ログ出力時に取り出して使う。

