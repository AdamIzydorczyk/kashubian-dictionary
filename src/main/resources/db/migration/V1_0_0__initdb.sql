-- public.sound_file definition
create table public.sound_file (
	id bigint not null,
	file bytea not null,
	filename character varying(255) not null,
	"type" character varying(255) not null,
	constraint constraint_a primary key (id)
);
create unique index primary_key_a on public.sound_file (id);

-- public.translation definition
create table public."translation" (
	id bigint not null,
	english character varying(255),
	german character varying(255),
	polish character varying(255),
	ukrainian character varying(255),
	constraint constraint_12 primary key (id)
);
create unique index primary_key_12 on public."translation" (id);

create sequence translation_id_sequence;

-- public.variation definition
create table public.variation (
	id bigint not null,
	variation jsonb,
	constraint constraint_a1 primary key (id)
);
create unique index primary_key_a1 on public.variation (id);

create sequence variation_id_sequence;

-- public.kashubian_entry definition

create table public.kashubian_entry (
	id bigint not null,
	note character varying(255),
	partofspeech character varying(255),
	partofspeechsubtype character varying(255),
	word character varying(255),
	sound_file_id bigint,
	variation_id bigint,
	constraint constraint_1 primary key (id),
	constraint fk6t4poschr0f82tg4meepi6wss foreign key (sound_file_id) references public.sound_file(id) on delete restrict on update restrict,
	constraint fksblqo4rchjxdb3nl7iw7fvome foreign key (variation_id) references public.variation(id) on delete restrict on update restrict
);
create index fk6t4poschr0f82tg4meepi6wss_index_1 on public.kashubian_entry (sound_file_id);
create index fksblqo4rchjxdb3nl7iw7fvome_index_1 on public.kashubian_entry (variation_id);
create unique index primary_key_1 on public.kashubian_entry (id);
create unique index uk_1c7kuah9o9rnvvv118us5m6d5_index_1 on public.kashubian_entry (word);

create sequence kashubian_entry_id_sequence;

-- public.meaning definition
create table public.meaning (
	id bigint not null,
	definition character varying(255),
	origin character varying(255),
	base_id bigint,
	superordinate_id bigint,
	translation_id bigint,
	kashubian_entry_id bigint,
	constraint constraint_6 primary key (id),
	constraint fk3e19p4wfu5nw0aa8lhc3c7w2n foreign key (kashubian_entry_id) references public.kashubian_entry(id) on delete restrict on update restrict,
	constraint fk60is1opx5ajacx094pmshuote foreign key (base_id) references public.meaning(id) on delete restrict on update restrict,
	constraint fkhpem2wi575s5rnsmu4emmrgl9 foreign key (superordinate_id) references public.meaning(id) on delete restrict on update restrict,
	constraint fkobkr0squ19h5dpa7umwdr4e0s foreign key (translation_id) references public."translation"(id) on delete restrict on update restrict
);
create index fk3e19p4wfu5nw0aa8lhc3c7w2n_index_6 on public.meaning (kashubian_entry_id);
create index fk60is1opx5ajacx094pmshuote_index_6 on public.meaning (base_id);
create index fkhpem2wi575s5rnsmu4emmrgl9_index_6 on public.meaning (superordinate_id);
create index fkobkr0squ19h5dpa7umwdr4e0s_index_6 on public.meaning (translation_id);
create unique index primary_key_6 on public.meaning (id);

create sequence meaning_id_sequence;

-- public.other definition
create table public.other (
	id bigint not null,
	note character varying(255),
	other_id bigint,
	kashubian_entry_id bigint,
	constraint constraint_4 primary key (id),
	constraint fkisg2s6fblenc9msgjajbxwomm foreign key (kashubian_entry_id) references public.kashubian_entry(id) on delete restrict on update restrict,
	constraint fkj7ssb8xxr3ofx4xvvhv1e720n foreign key (other_id) references public.kashubian_entry(id) on delete restrict on update restrict
);
create index fkisg2s6fblenc9msgjajbxwomm_index_4 on public.other (kashubian_entry_id);
create index fkj7ssb8xxr3ofx4xvvhv1e720n_index_4 on public.other (other_id);
create unique index primary_key_4 on public.other (id);


-- public.phrasal_verb definition

create table public.phrasal_verb (
	id bigint not null,
	note character varying(255),
	phrasalverb character varying(255),
	meaning_id bigint,
	constraint constraint_d0 primary key (id),
	constraint fk3hs9m2dob4tt3iofctp5yg2ao foreign key (meaning_id) references public.meaning(id) on delete restrict on update restrict
);
create index fk3hs9m2dob4tt3iofctp5yg2ao_index_d on public.phrasal_verb (meaning_id);
create unique index primary_key_d0 on public.phrasal_verb (id);

create sequence phrasal_verb_id_sequence;
-- public.proverb definition

create table public.proverb (
	id bigint not null,
	note character varying(255),
	proverb character varying(255),
	meaning_id bigint,
	constraint constraint_18 primary key (id),
	constraint fknie4380g3een3mwtkdfm6lk6s foreign key (meaning_id) references public.meaning(id) on delete restrict on update restrict
);
create index fknie4380g3een3mwtkdfm6lk6s_index_1 on public.proverb (meaning_id);
create unique index primary_key_18 on public.proverb (id);

create sequence proverb_id_sequence;

-- public.quote definition
create table public.quote (
	id bigint not null,
	note character varying(255),
	quote character varying(255),
	meaning_id bigint,
	constraint constraint_49 primary key (id),
	constraint fk4t7aj6ksiy56182smel82gyqp foreign key (meaning_id) references public.meaning(id) on delete restrict on update restrict
);
create index fk4t7aj6ksiy56182smel82gyqp_index_4 on public.quote (meaning_id);
create unique index primary_key_49 on public.quote (id);

create sequence quote_id_sequence;

-- public.synonym definition
create table public.synonym (
	id bigint not null,
	note character varying(255),
	synonym_id bigint,
	meaning_id bigint,
	constraint constraint_c primary key (id),
	constraint fk1f3ckhnfn359g43gat6ky4rtb foreign key (synonym_id) references public.meaning(id) on delete restrict on update restrict,
	constraint fke0k184b7vxled6we12q1rosyg foreign key (meaning_id) references public.meaning(id) on delete restrict on update restrict
);
create index fk1f3ckhnfn359g43gat6ky4rtb_index_c on public.synonym (synonym_id);
create index fke0k184b7vxled6we12q1rosyg_index_c on public.synonym (meaning_id);
create unique index primary_key_c on public.synonym (id);

create sequence synonym_id_sequence;
-- public.antonym definition

create table public.antonym (
	id bigint not null,
	note character varying(255),
	antonym_id bigint,
	meaning_id bigint,
	constraint constraint_f primary key (id),
	constraint fk9phr751aufdv4nwaf0feu1ddg foreign key (meaning_id) references public.meaning(id) on delete restrict on update restrict,
	constraint fkl9di15w2vijnkrkdxd9fuk1f4 foreign key (antonym_id) references public.meaning(id) on delete restrict on update restrict
);
create index fk9phr751aufdv4nwaf0feu1ddg_index_f on public.antonym (meaning_id);
create index fkl9di15w2vijnkrkdxd9fuk1f4_index_f on public.antonym (antonym_id);
create unique index primary_key_f on public.antonym (id);

create sequence antonym_id_sequence;
-- public.example definition

create table public.example (
	id bigint not null,
	example character varying(255),
	note character varying(255),
	meaning_id bigint,
	constraint constraint_d primary key (id),
	constraint fkq6gsx8863ssk3gnmdmo9jgxqu foreign key (meaning_id) references public.meaning(id) on delete restrict on update restrict
);
create index fkq6gsx8863ssk3gnmdmo9jgxqu_index_d on public.example (meaning_id);
create unique index primary_key_d on public.example (id);

create sequence example_id_sequence;