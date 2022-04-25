ALTER TABLE IF EXISTS public.instruments
    ADD COLUMN category_id bigint;
ALTER TABLE IF EXISTS public.instruments
    ADD CONSTRAINT fk_instruments_category_id FOREIGN KEY (category_id)
    REFERENCES public.categories (id) MATCH SIMPLE;

update public.instruments SET category_id = 1 WHERE id = 1;
update public.instruments SET category_id = 2 WHERE id in (2,3);
