FROM postgres:9.6.4

MAINTAINER Alexey Smirnov <aleksey.smirnov89@gmail.com>

# Environment variables used to define a database, account and password
ENV DB_NAME users_db
ENV DB_USER usr_srv_user
ENV DB_PASS password

# Initialize the postgres database
ADD setup-database.sh /docker-entrypoint-initdb.d/
RUN chmod 755 /docker-entrypoint-initdb.d/setup-database.sh