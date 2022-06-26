# profile demo

`default`プロファイルに対応するファイルは実は`application-default.properties`っぽい。

このリポジトリは次のプロパティファイルがある状態。

- `properties.properties`
- `properties-default.properties`
- `properties-production.properties`

この状態で次のプロファイル指定を試してみた。

- 何も指定しない(`default`へフォールバック)
- `production`を指定
- `properties_file_not_found`を指定(プロパティファイルが存在しない)

結果は次の通り。

```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.2.RELEASE)

2019-12-29 07:24:11.095  INFO 52658 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication v0.0.1-SNAPSHOT on snowman with PID 52658 (/Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo/target/demo-0.0.1-SNAPSHOT.jar started by backpaper0 in /Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo)
2019-12-29 07:24:11.100  INFO 52658 --- [           main] com.example.demo.DemoApplication         : No active profile set, falling back to default profiles: default
2019-12-29 07:24:11.736  INFO 52658 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 1.329 seconds (JVM running for 1.917)
HELLO DEFAULT!!!!!
```

```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=production

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.2.RELEASE)

2019-12-29 07:24:21.817  INFO 52662 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication v0.0.1-SNAPSHOT on snowman with PID 52662 (/Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo/target/demo-0.0.1-SNAPSHOT.jar started by backpaper0 in /Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo)
2019-12-29 07:24:21.822  INFO 52662 --- [           main] com.example.demo.DemoApplication         : The following profiles are active: production
2019-12-29 07:24:22.651  INFO 52662 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 1.463 seconds (JVM running for 2.076)
HELLO PRODUCTION!!!!!!!!!!!!!!!!!!!!!!!!!!
```

```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=properties_file_not_found

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.2.RELEASE)

2019-12-29 07:24:31.020  INFO 52666 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication v0.0.1-SNAPSHOT on snowman with PID 52666 (/Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo/target/demo-0.0.1-SNAPSHOT.jar started by backpaper0 in /Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo)
2019-12-29 07:24:31.025  INFO 52666 --- [           main] com.example.demo.DemoApplication         : The following profiles are active: properties_file_not_found
2019-12-29 07:24:31.773  INFO 52666 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 1.335 seconds (JVM running for 1.907)
HELLO WORLD!!
$
```

以上のことから次のことが言える。

- `application.properties`はどのプロファイルであっても読み込まれる
- `default`プロファイルに対応するプロパティファイルは`application-default.properties`

## おまけ

プロパティファイルの読み込み順はプロファイルの指定順っぽい(つまり優先順序は指定の逆順)。

```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=default,production

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.2.RELEASE)

2019-12-29 07:25:04.581  INFO 52693 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication v0.0.1-SNAPSHOT on snowman with PID 52693 (/Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo/target/demo-0.0.1-SNAPSHOT.jar started by backpaper0 in /Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo)
2019-12-29 07:25:04.584  INFO 52693 --- [           main] com.example.demo.DemoApplication         : The following profiles are active: default,production
2019-12-29 07:25:05.483  INFO 52693 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 1.578 seconds (JVM running for 2.276)
HELLO PRODUCTION!!!!!!!!!!!!!!!!!!!!!!!!!!
```

```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar --spring.profiles.active=production,default

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.2.2.RELEASE)

2019-12-29 07:25:12.278  INFO 52697 --- [           main] com.example.demo.DemoApplication         : Starting DemoApplication v0.0.1-SNAPSHOT on snowman with PID 52697 (/Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo/target/demo-0.0.1-SNAPSHOT.jar started by backpaper0 in /Users/backpaper0/src/mine/spring-boot-sandbox/profile-demo)
2019-12-29 07:25:12.283  INFO 52697 --- [           main] com.example.demo.DemoApplication         : The following profiles are active: production,default
2019-12-29 07:25:13.094  INFO 52697 --- [           main] com.example.demo.DemoApplication         : Started DemoApplication in 1.447 seconds (JVM running for 2.04)
HELLO DEFAULT!!!!!
```

## プロファイル別にコンポーネントを切り替える例

`com.example.demo.component`パッケージを参照。

