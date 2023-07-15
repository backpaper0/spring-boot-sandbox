ScyllaDBを起動する。

```bash
docker run -d --name scylla -p 9042:9042 scylladb/scylla
```

ScyllaDBの状態を確認する。

```bash
docker exec -it scylla nodetool status
```

ScyllaDBの起動が完了していない場合は`runtime error`になる。

KEYSPACEを作成する。

```bash
docker exec -it scylla cqlsh -e "CREATE KEYSPACE example WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};"
```

ScyllaDBの起動が完了していない場合は`Connection error`になる。

TABLEの作成はアプリケーション内で行なっているが、備忘のためCQLも書いておく。

```bash
CREATE TABLE user (id uuid PRIMARY KEY, name text);
```

アプリケーションの実行。

```bash
mvn spring-boot:run
```

Webでもないのになぜか終了しないので`control + c`で止める。

後始末。

```bash
docker stop scylla
```

```bash
docker rm scylla
```

