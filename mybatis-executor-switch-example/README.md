# 一部だけexecutor typeをbatchにする例

## 簡単な説明

1. インジェクションした`SqlSessionFactory`を用いて`ExecutorType.BATCH`を指定して新しい`SqlSession`を作成する
2. 新しい`SqlSession`からマッパーを取得する
3. 複数件の更新処理を繰り返し行う
   * 更新処理(下記の例では`insert`メソッド)のタイミングでは`addBatch`されるだけであり、クエリーは実行されない
   * SQL(XMLに書かれたプレースホルダー付きのクエリー)が変わったタイミング、あるいは`SqlSession.flushStatements()`が呼ばれたときにクエリーが実行される
4. `SqlSession.flushStatements()`を実行する
   * 同一の`SqlSession`で他のクエリーを実行しない場合、これを忘れるとクエリーが実行されない 

コード例:

```java
try (SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
    MessageMapper batchMapper = session.getMapper(MessageMapper.class);
    for (String content : contents) {
        Message message = new Message();
        message.setContent(content);
        batchMapper.insert(message);
        messages.add(message);
    }
    session.flushStatements();
}
return messages;
```

## 試す

アプリケーションの起動。

```bash
mvn spring-boot:run
```

`curl`で試す。

```bash
$ curl localhost:8080/messages
[]
$ curl localhost:8080/messages/bulk -d contents=foo -d contents=bar -d contents=baz -d contents=qux
[{"id":1,"content":"foo"},{"id":2,"content":"bar"},{"id":3,"content":"baz"},{"id":4,"content":"qux"}]
$ curl localhost:8080/messages
[{"id":1,"content":"foo"},{"id":2,"content":"bar"},{"id":3,"content":"baz"},{"id":4,"content":"qux"}]
```

バッチインサートのログは次の通り。

```
2026-02-08T11:49:16.551+09:00 DEBUG 73994 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==>  Preparing: INSERT INTO messages ( content ) VALUES ( ? )
2026-02-08T11:49:16.553+09:00 DEBUG 73994 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: foo(String)
2026-02-08T11:49:16.553+09:00 DEBUG 73994 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: bar(String)
2026-02-08T11:49:16.553+09:00 DEBUG 73994 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: baz(String)
2026-02-08T11:49:16.553+09:00 DEBUG 73994 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: qux(String)
```

executor typeがsimpleの場合、ログは次のようになる。

```
2026-02-08T12:00:00.737+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==>  Preparing: INSERT INTO messages ( content ) VALUES ( ? )
2026-02-08T12:00:00.739+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: foo(String)
2026-02-08T12:00:00.740+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : <==    Updates: 1
2026-02-08T12:00:00.741+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==>  Preparing: INSERT INTO messages ( content ) VALUES ( ? )
2026-02-08T12:00:00.741+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: bar(String)
2026-02-08T12:00:00.741+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : <==    Updates: 1
2026-02-08T12:00:00.741+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==>  Preparing: INSERT INTO messages ( content ) VALUES ( ? )
2026-02-08T12:00:00.741+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: baz(String)
2026-02-08T12:00:00.742+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : <==    Updates: 1
2026-02-08T12:00:00.742+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==>  Preparing: INSERT INTO messages ( content ) VALUES ( ? )
2026-02-08T12:00:00.742+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : ==> Parameters: qux(String)
2026-02-08T12:00:00.742+09:00 DEBUG 84633 --- [nio-8080-exec-2] c.example.mapper.MessageMapper.insert    : <==    Updates: 1
```
