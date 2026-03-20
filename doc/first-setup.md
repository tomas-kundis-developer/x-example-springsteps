Launch Microservice for The 1st Time
====================================

## 1. Start PostgreSQL Database Container

Start PostgreSQL database as a Docker container on the (default) port `5432`.

> PostgreSQL server version: **18.3**

|                                   |            |
|-----------------------------------|------------|
| Administrator database (default): | `postgres` |
| Administrator username (default): | `postgres` |
| Administrator password:           | `dev123`   |

Container name description: `postgres-db-c<container-instance-number>`

Run the container:

`docker run --name postgres-db-c1 -e POSTGRES_PASSWORD=dev123 -p 5432:5432 -d postgres:18.3`

|                               |                                                       |
|-------------------------------|-------------------------------------------------------|
| `--name`                      | Name of the container instance.                       |
| `-e`, `--env`                 | Set environment variable (inside container instance). |
| `-p HOST_PORT:CONTAINER_PORT` | Map `CONTAINER_PORT` to `HOST_PORT`.                  |
| `-d, --detach`                | Run container in background and print container ID.   |

## 2. Connect to the PostgreSQL as An Administrator

`psql` - PostgreSQL interactive client. \
https://www.postgresql.org/docs/18/app-psql.html

Connect to the PostgreSQL server with `psql`:

`psql <dbname> -U <username>`

> By default, psql connects to the server running on `localhost` on default port `5432`.

Run the `psql` inside the Docker container from your local computer:

`docker exec -it postgres-db-c1 psql postgres -U postgres`

Check the running database versions, current data and current time on database server:

```postgresql
SELECT version();
SELECT current_date;
SELECT current_time;
```

## 3. Setup Database

As a database administrator (postgres user) exec the following SQL statements inside the psql client to create database
role (database user) and database itself.

Run SQL statements from the code: \
[`<project-dir>/project/database/db-init-postgres.sql`](../project/database/db-init-postgres.sql)

Check the created database with `\l` command:

postgres=# \l

```
      Name      |        Owner        | Encoding | Locale Provider |  Collate   |   Ctype    | Locale | ICU Rules |   Access privileges
----------------+---------------------+----------+-----------------+------------+------------+--------+-----------+--------------------
 springsteps_db | springsteps_db_role | UTF8     | libc            | en_US.utf8 | en_US.utf8 |        |           | 
```