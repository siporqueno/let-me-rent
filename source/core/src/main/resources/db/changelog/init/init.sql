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

CREATE TABLE IF NOT EXISTS role
(
    id   BIGSERIAL    NOT NULL primary key,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS instrument
(
    id          BIGSERIAL    NOT NULL primary key,
    title       varchar(255) NOT NULL,
    brand       varchar(255) NOT NULL default 'no_brand',
    description varchar(255),
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
    id           BIGSERIAL    NOT NULL primary key,
    name         varchar(255) not null,
    content_type varchar(255) not null,
    storage_uuid varchar(255) not null
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
