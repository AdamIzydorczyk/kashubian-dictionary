-- meaning_delete_trigger trigger
create trigger meaning_delete_trigger before
delete
	on
	public.meaning for each row execute function remove_meaning_related();

-- validate_insert_entry_meanings_exist trigger
create constraint trigger validate_insert_entry_meanings_exist after
insert
	on
	kashubian_entry initially deferred for each row execute procedure validate_on_entry_insert_at_least_one_meaning_related();

-- validate_delete_meaning_entry_meanings_exist trigger
create constraint trigger validate_delete_meaning_entry_meanings_exist after
delete
	on
	meaning initially deferred for each row execute procedure validate_on_meaning_delete_at_least_one_meaning_related();