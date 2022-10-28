ALTER TABLE public.kashubian_entry
  ADD created_at timestamp,
  ADD created_by varchar(100),
  ADD modified_at timestamp,
  ADD modified_by varchar(100);

ALTER TABLE public.sound_file
  ADD created_at timestamp,
  ADD created_by varchar(100);