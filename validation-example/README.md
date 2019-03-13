# validation-example

Spring Bootでバリデーションの設定を行っているクラス。

- `org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration`

設定されている`javax.validation.Validator`実装クラス。
なお`org.springframework.validation.Validator`も`implements`している。

- `org.springframework.validation.beanvalidation.LocalValidatorFactoryBean`

こいつの`afterPropertiesSet`で`javax.validation.MessageInterpolator`をカスタマイズしている。

具体的には`org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator`でラップしている。
そうすることで`org.springframework.context.i18n.LocaleContextHolder`から取得できる`java.util.Locale`を使用してローカライズされたメッセージを取得できる。

