CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL    NOT NULL primary key,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS category
(
    id          BIGSERIAL    NOT NULL primary key,
    name        varchar(255) NOT NULL UNIQUE,
    description varchar(255)
);

CREATE TABLE IF NOT EXISTS producer
(
    id          BIGSERIAL    NOT NULL primary key,
    name        varchar(255) NOT NULL UNIQUE,
    description varchar(255)
);

CREATE TABLE IF NOT EXISTS "user"
(
    id          BIGSERIAL    NOT NULL primary key,
    first_name  varchar(255),
    second_name varchar(255),
    last_name   varchar(255),
    email       varchar(255) UNIQUE,
    username    varchar(255) NOT NULL UNIQUE,
    password    varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS instrument
(
    id          BIGSERIAL    NOT NULL primary key,
    title       varchar(255) NOT NULL,
    category_id BIGINT       NOT NULL REFERENCES category,
    producer_id BIGINT       NOT NULL REFERENCES producer,
    description varchar(255),
    price       numeric,
    fee         numeric,
    user_id     BIGINT       NOT NULL REFERENCES "user"
);

CREATE TABLE IF NOT EXISTS rent
(
    id         BIGSERIAL                   NOT NULL primary key,
    renter     BIGINT                      NOT NULL REFERENCES "user",
    lessor     BIGINT                      NOT NULL REFERENCES "user",
    date_start timestamp without time zone not null,
    date_end   timestamp without time zone not null
);

CREATE TABLE IF NOT EXISTS picture
(
    id  BIGSERIAL    NOT NULL primary key,
    url varchar(500) not null
);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id BIGINT NOT NULL REFERENCES "user",
    role_id BIGINT NOT NULL REFERENCES role,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS instrument_rent
(
    instrument_id BIGINT NOT NULL REFERENCES "user",
    rent_id       BIGINT NOT NULL REFERENCES role,
    PRIMARY KEY (instrument_id, rent_id)
);

CREATE TABLE IF NOT EXISTS instrument_picture
(
    instrument_id BIGINT NOT NULL REFERENCES "user",
    picture_id    BIGINT NOT NULL REFERENCES picture,
    PRIMARY KEY (instrument_id, picture_id)
);
