-- public.kashubian_entry definition
create table public.kashubian_entry (
	id bigint not null,
	note text,
	priority boolean not null,
	part_of_speech varchar(12) not null,
	part_of_speech_sub_type varchar(21) not null,
	word varchar(100) not null,
	normalized_word varchar(100) not null,
	base_id bigint,
	constraint pk_kashubian_entry primary key (id),
	constraint fk_kashubian_entry_base foreign key (base_id) references public.kashubian_entry(id) on delete restrict on update restrict
);
create unique index kashubian_entry_pk_unique_index on public.kashubian_entry (id);
create unique index kashubian_entry_normalized_word_unique_index on public.kashubian_entry (normalized_word);
create index kashubian_entry_base_id_index on public.kashubian_entry (base_id);
create sequence kashubian_entry_id_sequence;

-- public.variation definition
create table public.variation (
	id bigint not null,
	variation jsonb not null,
	kashubian_entry_id bigint not null,
	constraint pk_variation primary key (id),
	constraint fk_variation_kashubian_entry foreign key (kashubian_entry_id) references public.kashubian_entry(id) on delete cascade on update restrict
);
create unique index variation_id_word_unique_index on public.variation (id);

-- public.sound_file definition
create table public.sound_file (
	id bigint not null,
	file bytea not null,
	file_name text not null,
	"type" varchar(80) not null,
	kashubian_entry_id bigint,
	constraint pk_sound_file primary key (id),
	constraint fk_sound_file_kashubian_entry foreign key (kashubian_entry_id) references public.kashubian_entry(id) on delete cascade on update restrict
);
create unique index sound_file_pk_unique_index on public.sound_file (id);

-- public.meaning definition
create table public.meaning (
	id bigint not null,
	definition text not null,
	origin text,
	hyperonym_id bigint,
	kashubian_entry_id bigint not null,
	constraint pk_meaning primary key (id),
	constraint fk_meaning_kashubian_entry foreign key (kashubian_entry_id) references public.kashubian_entry(id) on delete cascade on update restrict,
	constraint fk_meaning_hyperonym foreign key (hyperonym_id) references public.meaning(id) on delete restrict on update restrict
);
create index meaning_kashubian_entry_id_index on public.meaning (kashubian_entry_id);
create index meaning_hyperonym_id_index on public.meaning (hyperonym_id);
create unique index meaning_pk_unique_index on public.meaning (id);
create sequence meaning_id_sequence;

-- public.translation definition
create table public."translation" (
	id bigint not null,
	english varchar(100),
	normalized_english varchar(100),
	german varchar(100),
	normalized_german varchar(100),
	polish varchar(100),
	normalized_polish varchar(100),
	ukrainian varchar(100),
	normalized_ukrainian varchar(100),
	meaning_id bigint not null,
	constraint pk_translation primary key (id),
	constraint fk_translation_meaning foreign key (meaning_id) references public."meaning"(id) on delete cascade on update restrict
);
create index translation_meaning_id_index on public."translation" (meaning_id);
create unique index translation_pk_unique_index on public."translation" (id);
create index translation_normalized_english_index on public.translation (english);
create index translation_normalized_german_index on public.translation (german);
create index translation_normalized_polish_index on public.translation (polish);
create index translation_normalized_ukrainian_index on public.translation (ukrainian);
create sequence translation_id_sequence;

-- public.other definition
create table public.other (
	id bigint not null,
	note text,
	other_id bigint not null,
	kashubian_entry_id bigint not null,
	constraint pk_other primary key (id),
	constraint fk_other_kashubian_entry foreign key (kashubian_entry_id) references public.kashubian_entry(id) on delete cascade on update restrict,
	constraint fk_other_other foreign key (other_id) references public.kashubian_entry(id) on delete cascade on update restrict
);
create index other_kashubian_entry_id_index on public.other (kashubian_entry_id);
create index other_other_id_index on public.other (other_id);
create unique index other_pk_unique_index on public.other (id);
create sequence other_id_sequence;

-- public.phrasal_verb definition
create table public.phrasal_verb (
	id bigint not null,
	note text,
	phrasal_verb varchar(150) not null,
	meaning_id bigint not null,
	constraint pk_phrasal_verb primary key (id),
	constraint fk_phrasal_verb_meaning foreign key (meaning_id) references public.meaning(id) on delete cascade on update restrict
);
create index phrasal_verb_meaning_id_index on public.phrasal_verb (meaning_id);
create unique index phrasal_verb_pk_unique_index on public.phrasal_verb (id);
create sequence phrasal_verb_id_sequence;

-- public.proverb definition
create table public.proverb (
	id bigint not null,
	note text,
	proverb varchar(150) not null,
	meaning_id bigint not null,
	constraint pk_proverb primary key (id),
	constraint fk_proverb_meaning foreign key (meaning_id) references public.meaning(id) on delete cascade on update restrict
);
create index proverb_meaning_id_index on public.proverb (meaning_id);
create unique index proverb_pk_unique_index on public.proverb (id);

create sequence proverb_id_sequence;

-- public.quote definition
create table public.quote (
	id bigint not null,
	note text,
	quote varchar(150) not null,
	meaning_id bigint not null,
	constraint pk_quote primary key (id),
	constraint fk_quote_meaning foreign key (meaning_id) references public.meaning(id) on delete cascade on update restrict
);
create index quote_meaning_id_index on public.quote (meaning_id);
create unique index quote_pk_unique_index on public.quote (id);

create sequence quote_id_sequence;

-- public.synonym definition
create table public.synonym (
	id bigint not null,
	note text,
	synonym_id bigint not null,
	meaning_id bigint not null,
	constraint pk_synonym primary key (id),
	constraint fk_synonym_synonym foreign key (synonym_id) references public.meaning(id) on delete cascade on update restrict,
	constraint fk_synonym_synonym_meaning foreign key (meaning_id) references public.meaning(id) on delete cascade on update restrict
);
create index synonym_synonym_id_index on public.synonym (synonym_id);
create index synonym_meaning_id_index_ on public.synonym (meaning_id);
create unique index synonym_pk_unique_index on public.synonym (id);

create sequence synonym_id_sequence;

-- public.antonym definition
create table public.antonym (
	id bigint not null,
	note text,
	antonym_id bigint not null,
	meaning_id bigint not null,
	constraint pk_antonym primary key (id),
	constraint fk_antonym_meaning foreign key (meaning_id) references public.meaning(id) on delete cascade on update restrict,
	constraint fk_antonym_antonym foreign key (antonym_id) references public.meaning(id) on delete cascade on update restrict
);
create index antonym_meaning_id_index on public.antonym (meaning_id);
create index antonym_antonym_id_index on public.antonym (antonym_id);
create unique index antonym_pk_unique_index on public.antonym (id);

create sequence antonym_id_sequence;

-- public.example definition
create table public.example (
	id bigint not null,
	example varchar(150) not null,
	note text,
	meaning_id bigint not null,
	constraint pk_example primary key (id),
	constraint fk_example_meaning foreign key (meaning_id) references public.meaning(id) on delete cascade on update restrict
);
create index example_meaning_id_index on public.example (meaning_id);
create unique index example_pk_unique_index on public.example (id);

create sequence example_id_sequence;
