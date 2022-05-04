# アプリケーション設定ファイルまわり

ルートにある`application.properties`以外を読み込む。

- `@Configuration`を付けたクラスに`@PropertySource`を付けてファイル名を指定する
- `org.springframework.boot.context.config.ConfigDataEnvironment`を読んでそれっぽいところにファイルを置く
    - クラスパス上の`application.properties`
    - クラスパス上の`config/application.properties`
    - ファイルシステム上の`./application.properties`
    - ファイルシステム上の`./config/application.properties`
    - ファイルシステム上の`./config/<任意のディレクトリ>/application.properties`
- `spring.config.import`(または`spring.config.additional-location`)にインポートするパスを設定してそこにファイルを置く
