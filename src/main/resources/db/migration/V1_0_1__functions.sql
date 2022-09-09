-- clear_kashubian_entry_base_id function
create or replace function clear_kashubian_entry_base_id() returns trigger as $$
begin
	update
	public.kashubian_entry
set
	"base_id" = null
where
	"base_id" = old.id;

return old;
end;

$$ language plpgsql;

-- clear_meaning_hyperonym_id function
create or replace function clear_meaning_hyperonym_id() returns trigger as $$
begin
update
	public.meaning
set
	"hyperonym_id" = null
where
	"hyperonym_id" = old.id;

return old;
end;

$$ language plpgsql;

-- meaning_hierarchy_element type
create type meaning_hierarchy_element as (
	meaning_id bigint,
	definition text,
	entry_id bigint,
	word text
);

-- entry_hierarchy_element type
create type entry_hierarchy_element as (
	entry_id bigint,
	word text
);

-- find_derivatives function
create or replace
function public.find_derivatives(entry_id bigint) returns json as $$
declare
	derivatives json;

begin
with recursive childs as (
select
	entry_id::bigint as id,
	''::text as word
union all
select
	ke.id,
	ke.word
from
	kashubian_entry ke
join childs on
	childs.id = ke.base_id ) (
select into derivatives
	json_agg((id,
	word)::entry_hierarchy_element)
from
	childs where id != entry_id);

return derivatives;
end;

$$ language plpgsql;

-- find_hyponyms function
create or replace function public.find_hyponyms(meaning_id bigint) returns json as $$
declare
	hyponyms json;

begin
with recursive childs(id, definition, entry_id, word) as (
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
select into hyponyms
	json_agg((c.id,
	c.definition,
	c.entry_id,
	c.word)::meaning_hierarchy_element)
from
	childs c where c.id != meaning_id);

return hyponyms;
end;

$$ language plpgsql;

-- find_bases function
create type base as (
    base_id bigint,
    word text
);

create or replace
function public.find_bases(entry_id bigint) returns json as $$

declare
	bases json;

begin
with recursive parents(id, base_id, word) as (
select
	ke.id,
	ke.base_id,
	base.word
from
	kashubian_entry ke
join kashubian_entry base on
	ke.base_id = base.id
union
select
	parents.id,
	ke.base_id,
	base.word
from
	parents
join kashubian_entry ke on
	parents.base_id = ke.id
join kashubian_entry base on
	ke.base_id = base.id )
select into bases
    json_agg((p.base_id, p.word)::entry_hierarchy_element)
from
	parents p
where
	p.id = entry_id
	and p.base_id is not null;

return bases;
end;

$$ language plpgsql;

-- find_hyperonyms function
create or replace
function public.find_hyperonyms(meaning_id bigint) returns json as $$
declare
	hyperonyms json;

begin
with recursive parents(id, hyperonym_id, definition, entry_id, word) as (
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
select into hyperonyms
	json_agg((p.hyperonym_id,
	p.definition,
	p.entry_id,
	p.word)::meaning_hierarchy_element)
from
	parents p
where
	p.id = meaning_id
	and p.hyperonym_id is not null;

return hyperonyms;
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