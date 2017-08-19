FROM postgres:9.6.4

MAINTAINER Alexey Smirnov <aleksey.smirnov89@gmail.com>

# Environment variables used to define a database, account and password
ENV DB_NAME users_db
ENV DB_USER usr_srv_user
ENV DB_PASS password

# For convert line separators
RUN apt-get update && apt-get install -y dos2unix

# Initialize the postgres database
ADD setup-database.sh /docker-entrypoint-initdb.d/
RUN chmod 755 /docker-entrypoint-initdb.d/setup-database.sh

# Convert line separators and remove extension
RUN dos2unix /docker-entrypoint-initdb.d/setup-database.sh && apt-get --purge remove -y dos2unix && rm -rf /var/lib/apt/lists/*
