# Spring Bootを使わずSpringを使う

`dependencies`へ`org.springframework:spring-context`を追加する。
`org.springframework.context.annotation.AnnotationConfigApplicationContext`でコンテナを構築する。

AOPをしたい場合は`dependencies`へ`org.aspectj:aspectjweaver`を追加して`@org.springframework.context.annotation.EnableAspectJAutoProxy(proxyTargetClass = true)`を付ける。

