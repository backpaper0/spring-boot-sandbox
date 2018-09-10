# Sprint Boot Sandbox

## めも( ・ิω・ิ)

一括でバージョンアップする。

```console
git grep -l 2.0.3 -- **/pom.xml|xargs sed -i '' -e 's/2\.0\.3/2.0.4/g'
```

`pom.xml`を変更したプロジェクトを全部ビルドする。

```
for i in `git ls-files -m -- **/pom.xml`; do mvn -f $i clean package; done
```

## License

Licensed under [The MIT License](https://opensource.org/licenses/MIT)
