# language-country-timezone-encoding-example

次のシステムプロパティとAPIを試す。

- システムプロパティ
    - `user.language`
    - `user.country`
    - `user.timezone`
    - `file.encoding`
- API
    - `java.util.Locale.getDefault()`
    - `java.util.TimeZone.getDefault()`
    - `java.nio.charset.Charset.defaultCharset()`
    - `java.time.LocalDateTime.now()`

Mavenで実行する場合。

```sh
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Duser.language=en -Duser.country=GB -Duser.timezone=Europe/London -Dfile.encoding=Windows-31J"
```

実行可能JARの場合。

```sh
mvn package
```

```sh
java -Duser.language=en -Duser.country=GB -Duser.timezone=Europe/London -Dfile.encoding=Windows-31J -jar target/language-country-timezone-encoding-example-0.0.1-SNAPSHOT.jar
```

コンテナの場合。

```sh
mvn spring-boot:build-image
```

```sh
docker run --rm -e JAVA_OPTS="-Duser.language=en -Duser.country=GB -Duser.timezone=Europe/London -Dfile.encoding=Windows-31J" language-country-timezone-encoding-example:0.0.1-SNAPSHOT
```

