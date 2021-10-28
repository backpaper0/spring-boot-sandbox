.PHONY: status
status:
	docker ps --filter name=redis

.PHONY: up
up:
	docker run -d --name redis -p 6379:6379 redis

.PHONY: down
down:
	docker rm -f redis

.PHONY: cli
cli:
	docker exec -it redis redis-cli

.PHONY: logs
logs:
	docker logs -f redis

