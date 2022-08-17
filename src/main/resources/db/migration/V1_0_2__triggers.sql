-- meaning_delete_trigger trigger
create trigger meaning_delete_trigger before
delete
	on
	public.meaning for each row execute function remove_meaning_related();