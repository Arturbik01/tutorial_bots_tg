# Мeowbot - telegram
### required dependency RabbitMQ
- docker pull rabbitmq:3.11.0-management
- docker volume create rabbitmq_data
- docker run -d --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 -v rabbitmq_data rabbitmq:3.11.0-management
- add user for RMQ and to fill application.properties 

### requider dependency Postgres
- docker pull postgres:14.5
- docker volume create postgres-data
- docker run -d --hostname meowdb -p 5432:5432 -e POSTGRES_PASSWORD=<pass> -e POSTGRES_DB=meowdb -v postgres-data postgres:14.5