-- Drop tables
drop table if exists instrument_orderitem cascade;
drop table if exists instrument_picture cascade;
drop table if exists order_orderitem cascade;
drop table if exists user_order cascade;
drop table if exists user_role cascade;

-- Rename tables
alter table if exists public."user" rename to users;
alter table if exists public."picture" rename to pictures;
alter table if exists public."order_item" rename to order_items;
alter table if exists public."order" rename to orders;
alter table if exists public."instrument" rename to instruments;
alter table if exists public."category" rename to categories;
alter table if exists public."role" rename to roles;

-- Instruments
ALTER TABLE IF EXISTS public.instruments
    ADD COLUMN start_date date DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.instruments
    ADD COLUMN end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY');

-- Categories
ALTER TABLE IF EXISTS public.categories
    ADD COLUMN start_date date DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.categories
    ADD COLUMN end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY');

-- Order_items
ALTER TABLE IF EXISTS public.order_items
    RENAME date_start TO start_date;
ALTER TABLE public.order_items
    ALTER COLUMN start_date TYPE date;
ALTER TABLE IF EXISTS public.order_items
    ALTER COLUMN start_date SET DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.order_items
    RENAME date_finish TO end_date;
ALTER TABLE public.order_items
    ALTER COLUMN end_date TYPE date;
ALTER TABLE IF EXISTS public.order_items
    ALTER COLUMN end_date SET DEFAULT to_date('31.12.2999', 'DD.MM.YYYY');
ALTER TABLE IF EXISTS public.order_items
    ADD COLUMN instr_instr_id bigint NOT NULL;
ALTER TABLE IF EXISTS public.order_items
    ADD COLUMN rent_price numeric;
ALTER TABLE IF EXISTS public.order_items
    ADD CONSTRAINT "fk_orderItems_instr_id" FOREIGN KEY (instr_instr_id)
        REFERENCES public.instruments (id) MATCH SIMPLE;

-- Orders
ALTER TABLE IF EXISTS public.orders
    RENAME date_start TO start_date;
ALTER TABLE public.orders
    ALTER COLUMN start_date TYPE date;
ALTER TABLE IF EXISTS public.orders
    ALTER COLUMN start_date SET DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.orders
    RENAME date_finish TO end_date;
ALTER TABLE public.orders
    ALTER COLUMN end_date TYPE date;
ALTER TABLE IF EXISTS public.orders
    ALTER COLUMN end_date SET DEFAULT to_date('31.12.2999', 'DD.MM.YYYY');
ALTER TABLE IF EXISTS public.orders
    ADD COLUMN total_rent_price numeric;

-- Roles
ALTER TABLE IF EXISTS public.roles
    ADD COLUMN start_date date DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.roles
    ADD COLUMN end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY');

-- Users
ALTER TABLE IF EXISTS public.users
    ADD COLUMN start_date date DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.users
    ADD COLUMN end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY');
ALTER TABLE IF EXISTS public.users
    ADD COLUMN update_date date DEFAULT current_timestamp;
ALTER TABLE IF EXISTS public.users
    ALTER COLUMN email SET NOT NULL;

-- Pictures
ALTER TABLE IF EXISTS public.pictures
    ADD COLUMN instr_instr_id bigint;
ALTER TABLE IF EXISTS public.pictures
    ADD CONSTRAINT fk_pictures_instr_id FOREIGN KEY (instr_instr_id)
        REFERENCES public.instruments (id) MATCH SIMPLE;

-- Create tables
CREATE TABLE if not exists public.granted_authorities
(
    grant_id bigserial,
    user_user_id bigint NOT NULL,
    role_role_id bigint NOT NULL,
    start_date date DEFAULT current_timestamp,
    end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY'),
    update_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY'),
    CONSTRAINT pk_grants_grant_id PRIMARY KEY (grant_id),
    CONSTRAINT fk_grants_users_user_id FOREIGN KEY (user_user_id)
        REFERENCES public.users (id) MATCH SIMPLE,
    CONSTRAINT fk_grants_roles_user_id FOREIGN KEY (role_role_id)
        REFERENCES public.roles (id) MATCH SIMPLE
);
ALTER TABLE IF EXISTS public.granted_authorities
    OWNER to postgres;
COMMENT ON TABLE public.granted_authorities
    IS 'Таблица агрегатор выданных ролей пользователю';
COMMENT ON CONSTRAINT fk_grants_users_user_id ON public.granted_authorities
    IS 'Вторичный ключ на таблицу Users';
COMMENT ON CONSTRAINT fk_grants_roles_user_id ON public.granted_authorities
    IS 'Вторичный ключ на таблицу Roles';

CREATE TABLE public.brands
(
    brand_id bigserial NOT NULL,
    brand_name character varying(250) NOT NULL,
    description character varying(2000),
    start_date date DEFAULT current_timestamp,
    end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY'),
    update_date date DEFAULT current_timestamp,
    CONSTRAINT pk_brands_brand_id PRIMARY KEY (brand_id),
    CONSTRAINT unique_brand_brand_name UNIQUE (brand_name)
);
ALTER TABLE IF EXISTS public.brands
    OWNER to postgres;

ALTER TABLE IF EXISTS public.instruments
    ADD COLUMN brand_brand_id bigint;
ALTER TABLE IF EXISTS public.instruments
    ADD CONSTRAINT fk_instruments_brands_brand_id FOREIGN KEY (brand_brand_id)
        REFERENCES public.brands (brand_id) MATCH SIMPLE;