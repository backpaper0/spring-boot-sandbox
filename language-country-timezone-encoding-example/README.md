
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Duser.language=en -Duser.country=GB -Duser.timezone=Europe/London -Dfile.encoding=Windows-31J"
```

```bash
mvn package

java -Duser.language=en -Duser.country=GB -Duser.timezone=Europe/London -Dfile.encoding=Windows-31J -jar target/language-country-timezone-encoding-example-0.0.1-SNAPSHOT.jar
```

```bash
mvn spring-boot:build-image

docker run --rm -e JAVA_OPTS="-Duser.language=en -Duser.country=GB -Duser.timezone=Europe/London -Dfile.encoding=Windows-31J" language-country-timezone-encoding-example:0.0.1-SNAPSHOT
```

