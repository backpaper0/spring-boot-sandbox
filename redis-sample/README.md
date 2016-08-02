# Sprign BootでRedisのサンプル

RedisはDockerで起動しています。

```
docker run -d -p 6379:6379 --name=redis redis
```

```
　　　　　　　　　(⌒⌒) 
　　　　 　　 　ii!i!i ﾄﾞｶｰｿ 
　　　　　 　／ ~~~ ＼ 
　　⊂⊃　／　＾ω ＾ ＼ ⊂⊃ 
.........,,,,傘傘傘:::::::::傘傘傘...
```

読む。

```
curl http://localhost:8080
```

書く。

```
curl http://localhost:8080 -d text=helloworld
```

