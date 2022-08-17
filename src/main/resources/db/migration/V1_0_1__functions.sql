-- remove_meaning_related function
create or replace
function remove_meaning_related() returns trigger as $$ begin
update
	public.meaning
set
	"base_id" = null
where
	"base_id" = old.id;

update
	public.meaning
set
	"hyperonym_id" = null
where
	"hyperonym_id" = old.id;

return old;
end;

$$ language 'plpgsql';

-- find_derivative_meanings_ids function
create or replace
function public.find_derivative_meanings_ids(meaning_id bigint) returns REFCURSOR language plpgsql as $function$
declare
	childs_ids REFCURSOR;

begin open childs_ids for with recursive childs as (
select
	meaning_id as id
union all
select
	m.id::integer
from
	meaning as m
join childs on
	childs.id = m.base_id ) (
select
	id
from
	childs offset 1);

return childs_ids;
end;

$function$ ;

-- find_hyponyms_ids function
create or replace
function public.find_hyponym_ids(meaning_id bigint) returns REFCURSOR language plpgsql as $function$
declare
	childs_ids REFCURSOR;

begin open childs_ids for with recursive childs as (
select
	meaning_id as id
union all
select
	m.id::integer
from
	meaning as m
join childs on
	childs.id = m.hyperonym_id ) (
select
	id
from
	childs offset 1);

return childs_ids;
end;

$function$;

-- find_base_meanings_ids function
create or replace
function public.find_base_meanings_ids(meaning_id bigint) returns REFCURSOR language plpgsql as $function$
declare
	parents_ids REFCURSOR;

begin open parents_ids for with recursive pl(id, base_id) as (
select
	id,
	base_id
from
	meaning
union
select
	pl.id,
	meaning.base_id
from
	pl
join meaning on
	pl.base_id = meaning.id )
select
	base_id
from
	pl
where
	id = meaning_id
	and base_id is not null;
return parents_ids;
end;

$function$;

-- find_base_meanings_ids function
create or replace
function public.find_hyperonyms_ids(meaning_id bigint) returns REFCURSOR language plpgsql as $function$
declare
	parents_ids REFCURSOR;

begin open parents_ids for with recursive pl(id, hyperonym_id) as (
select
	id,
	hyperonym_id
from
	meaning
union
select
	pl.id,
	meaning.hyperonym_id
from
	pl
join meaning on
	pl.hyperonym_id = meaning.id )
select
	hyperonym_id
from
	pl
where
	id = meaning_id
	and hyperonym_id is not null;
return parents_ids;
end;

$function$;