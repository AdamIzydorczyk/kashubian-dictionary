-- meaning_delete_trigger trigger
ALTER TABLE phrasal_verb RENAME TO idiom;
ALTER TABLE idiom RENAME COLUMN phrasal_verb TO idiom;
ALTER INDEX phrasal_verb_meaning_id_index RENAME TO idiom_meaning_id_index;
ALTER INDEX phrasal_verb_pk_unique_index RENAME TO idiom_pk_unique_index;
ALTER SEQUENCE phrasal_verb_id_sequence RENAME TO idiom_id_sequence;