# Sprint Boot Sandbox

## めも( ・ิω・ิ)

一括でバージョンアップする。

```console
ruby bump_spring_boot.rb
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
