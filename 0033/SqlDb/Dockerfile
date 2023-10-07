FROM postgres:13-alpine
RUN mkdir /scripts
ADD init-work-db.sh /docker-entrypoint-initdb.d/init-work-db.sh
ADD create_tables.sql /create_tables.sql
ADD create_data.sql /create_data.sql
ADD tune-config.sh /docker-entrypoint-initdb.d/tune-config.sh
ADD postgresql.conf /postgresql.conf
