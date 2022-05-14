# ロギングのカスタマイズ

## TomcatのアクセスログをLogbackで出力する

TomcatのアクセスログをLogbackで出力するには`ch.qos.logback.access.tomcat.LogbackValve`を追加し、クラスパス上の`conf/logback-access.xml`で設定を行う。

- 参照 https://logback.qos.ch/access.html

Spring Bootでは`org.springframework.boot.web.server.WebServerFactoryCustomizer`を実装したコンポーネント（型変数には`org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory`をバインドする）でTomcatをカスタマイズできるので、それを利用して`LogbackValve`を追加する。

### アクセスログの出力内容

アクセスログの出力内容のうち、いくつかをカスタマイズする必要がある。

まず`%u`または`%user`で出力できる認証済みユーザー。
Spring Securityを使用しているときは`org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName()`を出力する必要がある。

次に`%S`または`%sessionID`で出力できるセッションID。
Spring Sessionを使用しているときは`org.springframework.session.web.http.SessionRepositoryFilter`で`HttpSession`のラッパーが仕込まれるため、このフィルターよりも後のフィルターで`HttpSession.getId()`で取得したセッションIDを出力する必要がある。

最後に`%D`または`%elapsedTime`で出力できるリクエストの処理に要した時間。
外部APIの呼び出しを行った際、外部APIの処理に要した時間は自システムの処理時間ではないため差し引きたい場合がある。

カスタマイズの方法としては`ch.qos.logback.access.pattern.AccessConverter`の実装クラスを用意して、`logback-access.xml`で`conversionRule`要素を書く。
`AccessConverter`の中では`HttpServletRequest`を取得できるため、サーブレットフィルターなどで必要な値を`HttpServletRequest#setAttribute`で設定しておき、ログ出力時に取り出して使う。

