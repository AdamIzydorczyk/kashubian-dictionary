TRUNCATE kashubian_entry CASCADE;
TRUNCATE event CASCADE;

SELECT SETVAL(c.oid, 1, false)
from pg_class c JOIN pg_namespace n
on n.oid = c.relnamespace
where c.relkind = 'S' and n.nspname = 'public'