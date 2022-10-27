INSERT INTO public.kashubian_entry (id,priority,part_of_speech,part_of_speech_sub_type,word,normalized_word,base_id) VALUES
(nextval('kashubian_entry_id_sequence'),false,'PRONOUN','NUMERAL_PRONOUN','test_word_1','test_word_1',NULL);
INSERT INTO public.meaning (id,definition,hyperonym_id,kashubian_entry_id) VALUES
(nextval('meaning_id_sequence'),'def1',NULL,1);
INSERT INTO public."translation" (id,polish,normalized_polish,meaning_id) VALUES
(nextval('translation_id_sequence'),'test_pl','test_pl',1);

INSERT INTO public.kashubian_entry (id,priority,part_of_speech,part_of_speech_sub_type,word,normalized_word,base_id) VALUES
(nextval('kashubian_entry_id_sequence'),true,'PRONOUN','NUMERAL_PRONOUN','test_word_2','test_word_2',1);
INSERT INTO public.meaning (id,definition,hyperonym_id,kashubian_entry_id) VALUES
(nextval('meaning_id_sequence'),'def5',1,2);
INSERT INTO public."translation" (id,polish,normalized_polish,meaning_id) VALUES
(nextval('translation_id_sequence'),'test_pl','test_pl',2);

INSERT INTO public.kashubian_entry (id,priority,part_of_speech,part_of_speech_sub_type,word,normalized_word,base_id) VALUES
(nextval('kashubian_entry_id_sequence'),true,'PRONOUN','NUMERAL_PRONOUN','test_word_3','test_word_3',2);
INSERT INTO public.meaning (id,definition,hyperonym_id,kashubian_entry_id) VALUES
(nextval('meaning_id_sequence'),'def5',2,3);
INSERT INTO public."translation" (id,polish,normalized_polish,meaning_id) VALUES
(nextval('translation_id_sequence'),'test_pl','test_pl',3);

INSERT INTO public.kashubian_entry (id,priority,part_of_speech,part_of_speech_sub_type,word,normalized_word,base_id) VALUES
(nextval('kashubian_entry_id_sequence'),true,'PRONOUN','NUMERAL_PRONOUN','test_word_4','test_word_4',3);
INSERT INTO public.meaning (id,definition,hyperonym_id,kashubian_entry_id) VALUES
(nextval('meaning_id_sequence'),'def5',3,4);
INSERT INTO public."translation" (id,polish,normalized_polish,meaning_id) VALUES
(nextval('translation_id_sequence'),'test_pl','test_pl',4);

INSERT INTO public.kashubian_entry (id,priority,part_of_speech,part_of_speech_sub_type,word,normalized_word,base_id) VALUES
(nextval('kashubian_entry_id_sequence'),true,'PRONOUN','NUMERAL_PRONOUN','test_word_5','test_word_5',4);
INSERT INTO public.meaning (id,definition,hyperonym_id,kashubian_entry_id) VALUES
(nextval('meaning_id_sequence'),'def5',4,5);
INSERT INTO public."translation" (id,polish,normalized_polish,meaning_id) VALUES
(nextval('translation_id_sequence'),'test_pl','test_pl',5);