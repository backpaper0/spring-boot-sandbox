# どのURLにアクセスしてもindex.htmlを返す例

## /へのアクセスでindex.htmlを返す設定

`WebMvcConfigurer.addViewControllers`を実装する。

## /aaa/bbbなどへのアクセスでindex.htmlを返す設定

`WebMvcConfigurer.addResourceHandlers`を実装する。

デフォルトの実装は次のクラスにある。

- `org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter`

## おまけ

拡張子を持つファイルはデフォルトの動作をさせたい場合は`application.properties`に次のように書けば良い。

```
spring.mvc.static-path-pattern=/**/*.*
```

## 参考

- `org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry`
- `org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport.resourceHandlerMapping()`
- `org.springframework.web.servlet.handler.SimpleUrlHandlerMapping`
- `org.springframework.web.servlet.resource.ResourceHttpRequestHandler`
