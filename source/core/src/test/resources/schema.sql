CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL    NOT NULL primary key,
    name varchar(255) NOT NULL UNIQUE,
    start_date date DEFAULT current_timestamp,
    end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY')
    );

CREATE TABLE IF NOT EXISTS categories
(
    id          BIGSERIAL    NOT NULL primary key,
    name        varchar(255) NOT NULL UNIQUE,
    description varchar(255),
    start_date date DEFAULT current_timestamp,
    end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY')
);

CREATE TABLE IF NOT EXISTS brands
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

CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL    NOT NULL primary key,
    first_name  varchar(255),
    second_name varchar(255),
    last_name   varchar(255),
    email varchar(255) UNIQUE NOT NULL,
    username    varchar(255) NOT NULL UNIQUE,
    password    varchar(255) NOT NULL,
    start_date date DEFAULT current_timestamp,
    end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY'),
    update_date date DEFAULT current_timestamp
    );

CREATE TABLE IF NOT EXISTS instruments
(
    id          BIGSERIAL    NOT NULL primary key,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR (255),
    price       NUMERIC,
    fee         NUMERIC,
    start_date date DEFAULT current_timestamp,
    end_date date DEFAULT to_date('31.12.2999', 'DD.MM.YYYY'),
    category BIGINT NOT NULL REFERENCES categories,
    owner     BIGINT NOT NULL REFERENCES users,
    brand_brand_id BIGINT REFERENCES brands
    );

CREATE TABLE IF NOT EXISTS pictures
(
    id  BIGSERIAL    NOT NULL primary key,
    url varchar(500) not null,
    instr_instr_id bigint,
    CONSTRAINT fk_pictures_instr_id FOREIGN KEY (instr_instr_id)
    REFERENCES instruments (id)
    );