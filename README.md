# Aleksey Smirnov

## Development Requirements
* Docker (for postgres)
* JDK 1.8

##Postgres
To start postgres:
```
$ docker build -t asmirnov/postgres .
$ docker run --name postgres \
    -p 5432:5432 \
    -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password \
    -d \
    asmirnov/postgres

```
To migrate database:
```
$ ./gradlew dbMigrate
```
To start application:
```
$ ./gradlew run
```

##URLs
* Swagger: http://localhost:8080/swagger/ui#/
* Api: http://localhost:8080/v1/user