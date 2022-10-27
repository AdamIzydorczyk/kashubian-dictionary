INSERT INTO public.kashubian_entry VALUES (nextval('kashubian_entry_id_sequence'), 'test', true, 'NOUN', 'NEUTER', 'test', 'test', '{"nounVariation": {"nominative": "nominative","nominativePlural": "nominativePlural","genitive": "genitive","genitivePlural": "genitivePlural","dative": "dative","dativePlural": "dativePlural","accusative": "accusative","accusativePlural": "accusativePlural","instrumental": "instrumental","instrumentalPlural": "instrumentalPlural","locative": "locative","locativePlural": "locativePlural","vocative": "vocative","vocativePlural": "vocativePlural"}}', NULL);

INSERT INTO public.meaning VALUES (nextval('meaning_id_sequence'), 'meaning_test_1', 'meaning_note_test_1', NULL, 1);
INSERT INTO public.translation VALUES (1, 'translation_pl_test_1', 'translation_pl_test_1', 'translation_en_test_1', 'translation_en_test_1', 'translation_ge_test_1', 'translation_ge_test_1', 'translation_ua_test_1', 'translation_ua_test_1', 1);
INSERT INTO public.example VALUES (nextval('example_id_sequence'), 'example_test_1', 'example_note_test_1', 1);
INSERT INTO public.example VALUES (nextval('example_id_sequence'), 'example_test_2', 'example_note_test_2', 1);
INSERT INTO public.idiom VALUES (nextval('idiom_id_sequence'), 'idiom_test_1', 'idiom_note_test_1', 1);
INSERT INTO public.proverb VALUES (nextval('proverb_id_sequence'), 'proverb_test_1', 'proverb_note_test_1', 1);
INSERT INTO public.quote VALUES (nextval('quote_id_sequence'), 'quote_test_1', 'quote_note_test_1', 1);

INSERT INTO public.meaning VALUES (nextval('meaning_id_sequence'), 'meaning_test_2', 'meaning_note_test_2', NULL, 1);
INSERT INTO public.translation VALUES (2, 'translation_pl_test_2', 'translation_pl_test_2', 'translation_en_test_2', 'translation_en_test_2', 'translation_ge_test_2', 'translation_ge_test_2', 'translation_ua_test_2', 'translation_ua_test_2', 2);
INSERT INTO public.example VALUES (nextval('example_id_sequence'), 'example_test_3', 'example_note_test_3', 2);
INSERT INTO public.example VALUES (nextval('example_id_sequence'), 'example_test_4', 'example_note_test_4', 2);
INSERT INTO public.idiom VALUES (nextval('idiom_id_sequence'), 'idiom_test_2', 'idiom_note_test_2', 2);
INSERT INTO public.proverb VALUES (nextval('proverb_id_sequence'), 'proverb_test_2', 'proverb_note_test_2', 2);
INSERT INTO public.quote VALUES (nextval('quote_id_sequence'), 'quote_test_2', 'quote_note_test_2', 2);
