# Sprint Boot Sandbox

## めも( ・ิω・ิ)

一括でバージョンアップする。

```console
git ls-files -- **/pom.xml|xargs sed -i '' -e 's/2\.0\..*\.RELEASE/2.0.6.RELEASE/g'
```

`pom.xml`を変更したプロジェクトを全部ビルドする。

```
for i in `git ls-files -m -- **/pom.xml`; do mvn -f $i clean package; done
```

すべてのプロジェクトをビルドする。

```
for i in `git ls-files -- **/pom.xml`; do mvn -f $i clean package; done
```

## License

Licensed under [The MIT License](https://opensource.org/licenses/MIT)
