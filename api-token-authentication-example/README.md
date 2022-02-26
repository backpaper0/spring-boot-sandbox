
```
API_TOKEN=$(curl -s localhost:8080/login -d username=YourName | jq -r '.apiToken')
```

```
curl -s localhost:8080/demo -H "X-API-TOKEN: $API_TOKEN" | jq
```

