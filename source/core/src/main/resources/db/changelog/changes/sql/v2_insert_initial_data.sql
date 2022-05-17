insert into brands (brand_name, description)
VALUES ('BOSH', 'Инструменты фирмы BOSH'),
       ('Makita', 'Инструменты фирмы Makita'),
       ('DeWalt', 'Инструменты фирмы DeWalt');

insert into categories (name, description)
values ('Ручной', 'Ручной инструмент'),
       ('Электрический', 'Электрический инструмент'),
       ('Дрели', 'Дрели');

insert into users (first_name, second_name, last_name, email, username, password)
values ('Test', 'Testovich', 'Testov', 'test@test.com', 'test', '1111');

insert into instruments (title, category, description, price, fee, owner, brand_brand_id)
values ('Шуруповерт', 3, 'Крутит, сверлит', 1234.56, 12.23, 1, 1),
       ('Лобзик', 2, 'Вроде как пилит', 345, 23.12, 1, 2),
       ('Пила', 1, 'Вроде как пилит', 500, 56.12, 1, 3);