delete
from order_items
where id > 2;
delete
from orders
where id > 1;
delete
from instruments
where id > 3;
delete
from categories
where id > 3;
delete
from users
where id > 1;
delete
from brands
where brands.brand_id > 3;

insert into brands (brand_id, brand_name, description)
values (4, 'Huter', 'Инструменты фирмы Huter'),
       (5, 'Фиолент', 'Инструменты фирмы Фиолент'),
       (6, 'Интерскол', 'Инструменты фирмы Интерскол');

insert into categories (id, name, description)
VALUES (4, 'Пилы', 'Пилы'),
       (5, 'Электролобзики', 'Электролобзики'),
       (6, 'Фрезеры', 'Фрезеры'),
       (7, 'Болгарки', 'Болгарки'),
       (8, 'Шлифмашины', 'Шлифмашины'),
       (9, 'Перфораторы', 'Перфораторы'),
       (10, 'Шуруповерты', 'Шуроповерты'),
       (11, 'Иное', 'Все что не подходит в другие категории');

insert into users (id, first_name, email, username, password)
VALUES (2, 'Test1', 'test1@test.com', 'test1', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (3, 'Test2', 'test2@test.com', 'test2', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (4, 'Test3', 'test3@test.com', 'test3', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (5, 'Test4', 'test4@test.com', 'test4', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (6, 'Test5', 'test5@test.com', 'test5', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (7, 'Test6', 'test6@test.com', 'test6', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (8, 'Test7', 'test7@test.com', 'test7', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (9, 'Test8', 'test8@test.com', 'test8', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru'),
       (10, 'Test9', 'test9@test.com', 'test9', '$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru');

insert into instruments (id, title, category, description, price, fee, owner, brand_brand_id)
VALUES (4, 'Huter BS-52', 4, 'Бензопила Huter BS-52', 10000, 1000, 2, 4),
       (5, 'Интерскол ДП-190/1600М', 6, 'Дисковая пила Интерскол ДП-190/1600М', 8000, 800, 2, 6),
       (6, 'Huter BS-45', 4, 'Бензопила Huter BS-45', 10000, 1000, 2, 6),
       (7, 'Фиолент Б2-30 Professional', 4, 'Бороздодел Фиолент Б2-30 Professional', 11462, 1115, 2, 5),
       (8, 'Фиолент ПМ5-750Э М Мастер', 5, 'Лобзик Фиолент ПМ5-750Э М Мастер', 6294, 630, 3, 5),
       (9, 'Makita 4327', 5, 'Лобзик Makita 4327', 6516, 620, 3, 2),
       (10, 'Интерскол МП-65/550Э 101.1.1.00', 5, 'Лобзик Интерскол МП-65/550Э 101.1.1.00', 3700, 370, 3, 6),
       (11, 'Bosch POF 1200 AE 0.603.26A.100', 6, 'Фрезер Bosch POF 1200 AE 0.603.26A.100', 13000, 1300, 4, 1),
       (12, 'Makita 3709', 6, 'Фрезер Makita 3709', 9900, 990, 4, 2),
       (13, 'Фиолент МФ3-1100Э Professional', 6, 'Фрезер Фиолент МФ3-1100Э Professional', 7500, 750, 4, 5),
       (14, 'Интерскол 180/1800М', 7, 'Угловая шлифмашина Интерскол 180/1800М', 6000, 600, 5, 6),
       (15, 'Bosch X-lock GWX 17-125 S 06017C4002', 7, 'Угловая шлифмашина Bosch X-lock GWX 17-125 S 06017C4002', 18500,
        1850, 5, 1),
       (16, 'Makita GA4530', 7, 'Угловая шлифмашина Makita GA4530', 6500, 650, 5, 2),
       (17, 'Интерскол УПМ-180/1300Э', 8, 'Полировальная машина Интерскол УПМ-180/1300Э', 6200, 600, 6, 6),
       (18, 'Bosch EasySander 12 060397690B', 8, 'Шлифовальная машина Bosch EasySander 12 060397690B', 8000, 800, 6, 1),
       (19, 'Интерскол УПМ-180/1300ЭМ', 8, 'Полировальная машина Интерскол УПМ-180/1300ЭМ', 8500, 850, 6, 6),
       (20, 'Интерскол П-26/800ЭР 426.0.1.00', 9, 'Перфоратор Интерскол П-26/800ЭР 426.0.1.00', 5800, 580, 7, 6),
       (21, 'Bosch PBH 2100 RE 0.603.3A9.320', 9, 'Перфоратор Bosch PBH 2100 RE 0.603.3A9.320', 8500, 850, 7, 1),
       (22, 'Bosch GBH 2-24 DRE кейс 0.611.272.100', 9, 'Перфоратор Bosch GBH 2-24 DRE кейс 0.611.272.100', 11800, 1180,
        7, 1),
       (23, 'Bosch IXO Full 0603981022', 10, 'Шуруповерт Bosch IXO Full 0603981022', 7000, 700, 8, 1),
       (24, 'Bosch GSR 12V-15 FC кейс 06019F6000', 10, 'Дрель-шуруповерт Bosch GSR 12V-15 FC кейс 06019F6000', 24000,
        2400, 8, 1),
       (25, 'Фиолент ШВ3-6 РЭ', 10, 'Шуруповерт Фиолент ШВ3-6 РЭ', 4200, 420, 8, 5),
       (26, 'Makita GA9060', 7, 'Угловая шлифмашина Makita GA9060', 15102, 1500, 9, 2),
       (27, 'Makita RP 2301 FCX', 6, 'Фрезер Makita RP 2301 FCX', 42260, 4300, 9, 5);

insert into orders (id, renter, start_date, end_date, total_rent_price)
VALUES (2, 2, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '10 DAY', 20000),
       (3, 3, NOW() + INTERVAL '15 DAY', NOW() + INTERVAL '20 DAY', 10000),
       (4, 10, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '40 DAY', 172000),
       (5, 7, NOW() + INTERVAL '18 DAY', NOW() + INTERVAL '20 DAY', 3600),
       (6, 1, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '8 DAY', 6930),
       (7, 1, NOW() + INTERVAL '3 DAY', NOW() + INTERVAL '7 DAY', 7150),
       (8, 1, NOW() + INTERVAL '8 DAY', NOW() + INTERVAL '15 DAY', 5950),
       (9, 1, NOW() + INTERVAL '23 DAY', NOW() + INTERVAL '43 DAY', 18890);

insert into order_items (id, "order", start_date, end_date, instr_instr_id, rent_price)
VALUES (3, 2, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '10 DAY', 4, 10000),
       (4, 2, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '10 DAY', 5, 10000),
       (5, 3, NOW() + INTERVAL '15 DAY', NOW() + INTERVAL '20 DAY', 4, 5000),
       (6, 3, NOW() + INTERVAL '15 DAY', NOW() + INTERVAL '20 DAY', 5, 5000),
       (7, 4, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '40 DAY', 27, 172000),
       (8, 5,NOW() + INTERVAL '18 DAY', NOW() + INTERVAL '19 DAY', 26, 1500),
       (9, 5, NOW() +INTERVAL '18 DAY', NOW() + INTERVAL '20 DAY', 16, 1300),
       (10, 5,NOW() + INTERVAL '18 DAY', NOW() + INTERVAL '19 DAY', 17, 800),
       (11, 6, NOW() + INTERVAL '1 DAY', NOW() + INTERVAL '8 DAY', 12, 6930),
       (12, 7, NOW() + INTERVAL '4 DAY', NOW() + INTERVAL '5 DAY', 23, 1400),
       (13, 7, NOW() + INTERVAL '3 DAY', NOW() + INTERVAL '7 DAY', 18, 3200),
       (14, 7, NOW() + INTERVAL '3 DAY', NOW() + INTERVAL '6 DAY', 21, 2550),
       (15, 8, NOW() + INTERVAL '8 DAY', NOW() + INTERVAL '15 DAY', 21, 5950),
       (16, 9, NOW() + INTERVAL '23 DAY', NOW() + INTERVAL '33 DAY', 6, 10000),
       (17, 9, NOW() + INTERVAL '28 DAY', NOW() + INTERVAL '35 DAY', 9, 5040),
       (18, 9, NOW() + INTERVAL '30 DAY', NOW() + INTERVAL '32 DAY', 13, 2250),
       (19, 9, NOW() + INTERVAL '42 DAY', NOW() + INTERVAL '43 DAY', 18, 1600);
