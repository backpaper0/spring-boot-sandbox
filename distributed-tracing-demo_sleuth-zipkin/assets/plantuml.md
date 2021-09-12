```plantuml
@startuml service-diagram
[front-service] --> [fizz-service]
[front-service] --> [buzz-service]
[front-service] --> [fizzbuzz-service]
[fizzbuzz-service] --> [fizz-service]
[fizzbuzz-service] --> [buzz-service]
@enduml
```
