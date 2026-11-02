-- find_word_of_the_day function, now also returning the entry note
-- (replaces the same-named function from V1_0_1)
create or replace function public.find_word_of_the_day(seed float8) returns refcursor as $$ declare random_result refcursor;
begin
	perform setseed(seed);

open random_result for
select
	random_word.id as id,
	random_word.word as word,
	m.definition as definition,
	random_word.note as note
from
	(
	select
		ke.id, ke.word, ke.note
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

