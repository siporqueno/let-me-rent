CREATE TABLE IF NOT EXISTS public.comments_about_instrument
(
    comment_id     bigserial NOT NULL,
    user_user_id   bigint    NOT NULL,
    instr_instr_id bigint    NOT NULL,
    description    character varying(3200),
    grade          integer,
    start_date     timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date    timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    end_date       timestamp without time zone DEFAULT to_date('31.12.2999'::text, 'DD.MM.YYYY'::text),
    CONSTRAINT pk_instrument_comment_id PRIMARY KEY (comment_id),
    CONSTRAINT fk_comments_instr_id FOREIGN KEY (instr_instr_id)
        REFERENCES public.instruments (id) MATCH SIMPLE,
    CONSTRAINT fk_user_about_instr_id FOREIGN KEY (user_user_id)
        REFERENCES public.users (id) MATCH SIMPLE
);

ALTER TABLE IF EXISTS public.comments_about_instrument
    OWNER to postgres;
COMMENT ON TABLE public.comments_about_instrument
    IS 'Таблица комментариев пользователей об инструменте';
COMMENT ON COLUMN public.comments_about_instrument.comment_id
    IS 'Идентификатор комментария';
COMMENT ON COLUMN public.comments_about_instrument.user_user_id
    IS 'Идентификатор пользователя, оставившего комментарий';
COMMENT ON COLUMN public.comments_about_instrument.instr_instr_id
    IS 'Идентификатор инструмента';
COMMENT ON COLUMN public.comments_about_instrument.description
    IS 'Текст комментария';
COMMENT ON COLUMN public.comments_about_instrument.grade
    IS 'Оценка пользователя';
COMMENT ON COLUMN public.comments_about_instrument.start_date
    IS 'Дата комментария';
COMMENT ON COLUMN public.comments_about_instrument.update_date
    IS 'Дата обновления комментария';
COMMENT ON COLUMN public.comments_about_instrument.end_date
    IS 'Дата удаления комментария';

CREATE TABLE IF NOT EXISTS public.comments_about_users
(
    comment_id      bigserial NOT NULL,
    user_user_id    bigint    NOT NULL,
    address_user_id bigint    NOT NULL,
    description     character varying(3200) COLLATE pg_catalog."default",
    grade           integer,
    start_date      timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    update_date     timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    end_date        timestamp without time zone DEFAULT to_date('31.12.2999'::text, 'DD.MM.YYYY'::text),
    CONSTRAINT pk_user_comment_id PRIMARY KEY (comment_id),
    CONSTRAINT fk_comments_user_id FOREIGN KEY (user_user_id)
        REFERENCES public.users (id) MATCH SIMPLE,
    CONSTRAINT fk_user_about_user_id FOREIGN KEY (user_user_id)
        REFERENCES public.users (id) MATCH SIMPLE
);

ALTER TABLE IF EXISTS public.comments_about_users
    OWNER to postgres;
COMMENT ON TABLE public.comments_about_users
    IS 'Таблица комментариев пользователей о пользователе';
COMMENT ON COLUMN public.comments_about_users.comment_id
    IS 'Идентификатор комментария';
COMMENT ON COLUMN public.comments_about_users.user_user_id
    IS 'Идентификатор пользователя, оставившего комментарий';
COMMENT ON COLUMN public.comments_about_users.address_user_id
    IS 'Идентификатор комментируемого пользователя';
COMMENT ON COLUMN public.comments_about_users.description
    IS 'Текст комментария';
COMMENT ON COLUMN public.comments_about_users.grade
    IS 'Оценка пользователя';
COMMENT ON COLUMN public.comments_about_users.start_date
    IS 'Дата комментария';
COMMENT ON COLUMN public.comments_about_users.update_date
    IS 'Дата обновления комментария';
COMMENT ON COLUMN public.comments_about_users.end_date
    IS 'Дата удаления комментария';