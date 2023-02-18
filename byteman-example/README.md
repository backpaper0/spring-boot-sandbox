# Spring BootアプリケーションをBytemanでデバッグする

Spring Boot Maven Pluginの`run`ゴール。

```sh
mvn spring-boot:run -Dspring-boot.run.agents="$BYTEMAN_HOME/lib/byteman.jar=script:demo.btm"
```

実行可能JAR。

```sh
java -javaagent:$BYTEMAN_HOME/lib/byteman.jar=script:demo.btm -jar target/byteman-example-0.0.1-SNAPSHOT.jar
```

コンテナイメージ（動作確認時の見やすさ優先でNative Memory Trackingをオフにしてる）。

```sh
docker run --rm -e BPL_JAVA_NMT_ENABLED=false \
  -e JAVA_TOOL_OPTIONS="-javaagent:/tmp/byteman.jar=script:/tmp/demo.btm" \
  -v $BYTEMAN_HOME/lib/byteman.jar:/tmp/byteman.jar:ro \
  -v $PWD/demo.btm:/tmp/demo.btm:ro byteman-example:0.0.1-SNAPSHOT
```

