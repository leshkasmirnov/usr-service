# Aleksey Smirnov

[![Build Status](https://travis-ci.org/leshkasmirnov/usr-service.svg?branch=master)](https://travis-ci.org/leshkasmirnov/usr-service)

## Development Requirements
* Docker (for postgres)
* JDK 1.8

## Postgres
To start postgres:
```
$ docker build -t asmirnov/postgres .
$ docker run --name postgres -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_PASSWORD=password -d asmirnov/postgres

```
To migrate database:
```
$ ./gradlew dbMigrate
```
To start application:
```
$ ./gradlew run
```

## URLs
* Swagger: http://localhost:9000/swagger/ui#/
* Api: http://localhost:9000/v1/user

## Users
* admin/123456 with ADMIN role (all actions are accessible)
* user/123456 without ADMIN role (only get user ba name action is accessible)