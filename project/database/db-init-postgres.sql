-- AS a database administrator
-- Create database and database user for Springsteps microservice.
--
-- Run this script (or rewrite code manually) only once, when database is executed for the first time.
-- This script must be run before the Springsteps microservice is launched for the first time.
--
-- This is the only functionality where you use database server as its administrator.
-- All Springsteps microservice code communicate with the server with the dedicated, non-administrator database user.

CREATE ROLE springsteps_db_role NOSUPERUSER NOCREATEROLE LOGIN PASSWORD 'dev123';

CREATE DATABASE springsteps_db WITH OWNER springsteps_db_role ENCODING 'UTF8' LOCALE 'en_US.utf8' TEMPLATE template0;

-- Check the created role:

SELECT *
FROM pg_roles
WHERE rolcanlogin;

-- Finally check the created database.
-- This is possible only from the psql database client with `\l` command. For example:
--
-- postgres=# \l