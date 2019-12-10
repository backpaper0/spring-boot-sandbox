# Sprint Boot Sandbox

- [![](https://github.com/backpaper0/spring-boot-sandbox/workflows/spring-boot-sandbox/badge.svg)](https://github.com/backpaper0/spring-boot-sandbox/actions?query=workflow:spring-boot-sandbox)

## めも( ・ิω・ิ)

一括でバージョンアップする。

```
ruby bump_spring_boot.rb
```

GitHub Actionsの定義ファイルを出力する。ステータスバッジも。

```
ruby generate_actions.rb
```

`pom.xml`を変更したプロジェクトを全部ビルドする。

```
for i in `git ls-files -m|grep pom\.xml`; do mvn -f $i clean package; done
```

すべてのプロジェクトをビルドする。

```
for i in `git ls-files -- **/pom.xml`; do mvn -f $i clean package; done
```

## License

Licensed under [The MIT License](https://opensource.org/licenses/MIT)
