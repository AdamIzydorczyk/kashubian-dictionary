-- remove_meaning_related function
create or replace function remove_meaning_related() returns trigger as $$
begin
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

$$ language plpgsql;

-- find_derivative_meanings_ids function
create or replace
function public.find_derivative_meanings_ids(meaning_id bigint) returns refcursor as $$
declare
	childs_ids refcursor;

begin open childs_ids for with recursive childs as (
select
	meaning_id::bigint as id,
	''::text as definition,
	-1::bigint as entry_id,
	''::text as word
union all
select
	m.id,
	m.definition,
	ke.id,
	ke.word
from
	meaning as m
join kashubian_entry ke on
	ke.id = m.kashubian_entry_id
join childs on
	childs.id = m.base_id ) (
select
	id,
	definition,
	entry_id,
	word
from
	childs offset 1);

return childs_ids;
end;

$$ language plpgsql;

-- find_hyponyms_ids function
create or replace function public.find_hyponym_ids(meaning_id bigint) returns refcursor as $$
declare
	childs_ids refcursor;

begin open childs_ids for with recursive childs as (
select
	meaning_id::bigint as id,
	''::text as definition,
	-1::bigint as entry_id,
	''::text as word
union all
select
	m.id,
	m.definition,
	ke.id,
	ke.word
from
	meaning as m
join kashubian_entry ke on
	ke.id = m.kashubian_entry_id
join childs on
	childs.id = m.hyperonym_id ) (
select
	id,
	definition,
	entry_id,
	word
from
	childs offset 1);

return childs_ids;
end;

$$ language plpgsql;

-- find_base_meanings_ids function
create or replace
function public.find_base_meanings_ids(meaning_id bigint) returns refcursor as $$
declare
	parents_ids refcursor;

begin open parents_ids for with recursive parents(id, base_id, definition, entry_id, word) as (
select
	m.id,
	m.base_id,
	base.definition,
	ke.id,
	ke.word
from
	meaning m
join meaning base on
	m.base_id = base.id
join kashubian_entry ke on
	ke.id = base.kashubian_entry_id
union
select
	parents.id,
	m.base_id,
	base.definition,
	ke.id,
	ke.word
from
	parents
join meaning m on
	parents.base_id = m.id
join meaning base on
	m.base_id = base.id
join kashubian_entry ke on
	ke.id = base.kashubian_entry_id )
select
	base_id,
	definition,
	entry_id,
	word
from
	parents
where
	id = meaning_id
	and base_id is not null;

return parents_ids;
end;

$$ language plpgsql;

-- find_base_meanings_ids function
create or replace
function public.find_hyperonyms_ids(meaning_id bigint) returns refcursor as $$
declare
	parents_ids refcursor;

begin open parents_ids for with recursive parents(id, hyperonym_id, definition, entry_id, word) as (
select
	m.id,
	m.hyperonym_id,
	hyperonym.definition,
	ke.id,
	ke.word
from
	meaning m
join meaning hyperonym on
	m.hyperonym_id = hyperonym.id
join kashubian_entry ke on
	ke.id = hyperonym.kashubian_entry_id
union
select
	parents.id,
	m.hyperonym_id,
	hyperonym.definition,
	ke.id,
	ke.word
from
	parents
join meaning m on
	parents.hyperonym_id = m.id
join meaning hyperonym on
	m.hyperonym_id = hyperonym.id
join kashubian_entry ke on
	ke.id = hyperonym.kashubian_entry_id )
select
	hyperonym_id,
	definition,
	entry_id,
	word
from
	parents
where
	id = meaning_id
	and hyperonym_id is not null;

return parents_ids;
end;

$$ language plpgsql;

-- find_word_of_the_day function
create or replace function public.find_word_of_the_day(seed float8) returns refcursor as $$ declare random_result refcursor;
begin
	perform setseed(seed);

open random_result for
select
	random_word.id as id,
	random_word.word as word,
	m.definition as definition
from
	(
	select
		ke.id, ke.word
	from
		kashubian_entry ke
	where
		ke.priority = true offset floor(random() * ( select count(*) from kashubian_entry where priority = true))
	limit 1 ) as random_word
join meaning m on
	m.kashubian_entry_id = random_word.id;

return random_result;
end;

$$ language plpgsql;

-- validate_at_least_one_meaning_related_to_entry function
create or replace function validate_on_entry_insert_at_least_one_meaning_related() returns trigger as $$
begin
	if not exists (
		select
    		1
    	from
    		meaning m
    	where
    		m.kashubian_entry_id = new.id
	limit 1) then raise exception 'entry must have at least one meaning';
end if;

return new;
end;

$$ language plpgsql;

-- validate_at_least_one_meaning_related_to_entry function
create or replace function validate_on_meaning_delete_at_least_one_meaning_related() returns trigger as $$
begin
	if
	not exists (
		select
    		1
    	from
    		meaning m
    	where
    		m.kashubian_entry_id = old.kashubian_entry_id
    	limit 1
	)
	and
	exists (
		select
    		1
    	from
    		kashubian_entry ke
    	where
    		ke.id = old.kashubian_entry_id
    	limit 1
	)
	then raise exception 'entry must have at least one meaning';
end if;

return new;
end;

$$ language plpgsql;