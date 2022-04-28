# Spring Batch Example

## ファイルを読み込んでDBへ保存するバッチ

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=FileToDb input.file=inputs/input-valid.csv"
```

invalidな行を含むファイルを読み込む(`Application finished with exit code: 6`)。

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=FileToDb input.file=inputs/input-invalid.csv"
```

入力ファイルが存在しない場合(`Application finished with exit code: 5`)。

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=FileToDb input.file=inputs/input-not-exists.csv"
```

固定長ファイルを読み込む場合。

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=FixedFileToDb input.file=inputs/input-fixed.txt"
```

`Shift_JIS`でバイト数区切りの場合。

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=FixedByBytesFileToDb input.file=inputs/input-fixed-sjis.txt"
```

## DBからレコードを読み込んで1件ずつ悲観ロックを取って更新するバッチ

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=DbToDb"
```

## chunkの処理順序を確認するバッチ

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=ChunkDemo --logging.level.org.springframework.jdbc.datasource.DataSourceTransactionManager=debug"
```

## taskletの処理順序を確認するバッチ

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=TaskletDemo --logging.level.org.springframework.jdbc.datasource.DataSourceTransactionManager=debug"
```

## マルチスレッドで処理を行うバッチ

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=MultiThreadDemo"
```

シングルスレッドで処理を行う場合。

```
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.names=MultiThreadDemo --app.multithread.enabled=false"
```

