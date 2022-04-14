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
    category BIGINT       NOT NULL REFERENCES category,
    description varchar(255),
    price       numeric,
    fee         numeric,
    owner     BIGINT       NOT NULL REFERENCES "user"
);

CREATE TABLE IF NOT EXISTS "order"
(
    id          BIGSERIAL                   NOT NULL primary key,
    renter      BIGINT                      NOT NULL REFERENCES "user",
    date_start  timestamp without time zone not null,
    date_finish timestamp without time zone not null
);

CREATE TABLE IF NOT EXISTS order_item
(
    id          BIGSERIAL                   NOT NULL primary key,
    "order"       BIGINT                      NOT NULL REFERENCES "order",
    date_start  timestamp without time zone not null,
    date_finish timestamp without time zone not null
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

CREATE TABLE IF NOT EXISTS user_order
(
    user_id BIGINT NOT NULL REFERENCES "user",
    order_id BIGINT NOT NULL REFERENCES "order",
    PRIMARY KEY (user_id, order_id)
);

CREATE TABLE IF NOT EXISTS instrument_picture
(
    instrument_id BIGINT NOT NULL REFERENCES instrument,
    picture_id    BIGINT NOT NULL REFERENCES picture,
    PRIMARY KEY (instrument_id, picture_id)
);

CREATE TABLE IF NOT EXISTS instrument_orderItem
(
    instrument_id BIGINT NOT NULL REFERENCES instrument,
    orderItem_id    BIGINT NOT NULL REFERENCES order_item,
    PRIMARY KEY (instrument_id, orderItem_id)
);

CREATE TABLE IF NOT EXISTS order_orderItem
(
    order_id BIGINT NOT NULL REFERENCES "order",
    orderItem_id    BIGINT NOT NULL REFERENCES order_item,
    PRIMARY KEY (order_id, orderItem_id)
);
