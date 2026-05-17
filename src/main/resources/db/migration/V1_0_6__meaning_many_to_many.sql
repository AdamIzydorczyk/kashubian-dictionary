create table meaning_kashubian_entry
(
    kashubian_entry_id bigint not null,
    meaning_id         bigint not null,
    constraint pk_meaning_kashubian_entry primary key (kashubian_entry_id, meaning_id),
    constraint fk_mke_kashubian_entry foreign key (kashubian_entry_id) references public.kashubian_entry (id) on delete cascade on update restrict,
    constraint fk_mke_meaning foreign key (meaning_id) references public.meaning (id) on delete cascade on update restrict
);

insert into meaning_kashubian_entry (kashubian_entry_id, meaning_id)
select kashubian_entry_id, id
from meaning;

create index meaning_kashubian_entry_meaning_id_index on public.meaning_kashubian_entry (meaning_id);

alter table meaning
    drop constraint fk_meaning_kashubian_entry;

drop index meaning_kashubian_entry_id_index;

alter table meaning
    drop column kashubian_entry_id;
