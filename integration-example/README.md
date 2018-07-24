```
docker run -d --name mq -h usaq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
# Add exchange: foo
# Add queue: bar
# bar bound foo
```

```
docker run -d --name ftpd -p 10021:21 -p 30000-30009:30000-30009 -e "PUBLICHOST=localhost" -e "FTP_USER_NAME=example" -e "FTP_USER_PASS=example" -e "FTP_USER_HOME=/home/example" -v `pwd`/input:/home/example stilliard/pure-ftpd
```

