```
docker run -d --name mq -h usaq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
# Add exchange: foo
# Add queue: bar
# bar bound foo
```

