create table public.event (
	id bigint not null,
	event_type varchar(100) not null,
    event jsonb not null,
    invoked_by varchar(100),
    invoked_at timestamp,
    constraint pk_event primary key (id)
);
create unique index event_pk_unique_index on public.event (id);
create sequence event_id_sequence;