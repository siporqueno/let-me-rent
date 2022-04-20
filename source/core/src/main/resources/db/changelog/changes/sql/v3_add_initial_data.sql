insert into orders (renter, start_date, end_date, total_rent_price)
values (1, to_date('20.04.2022 20:30:00', 'DD.MM.YYYY HH24:MI:ss'),
        to_date('20.05.2022 20:30:00', 'DD.MM.YYYY HH24:MI:ss'), 2000);

insert into order_items ("order", start_date, end_date, instr_instr_id, rent_price)
values (1, to_date('20.04.2022 20:30:00', 'DD.MM.YYYY HH24:MI:ss'),
        to_date('20.05.2022 20:30:00', 'DD.MM.YYYY HH24:MI:ss'),
        1, 1000),
       (1, to_date('20.04.2022 20:30:00', 'DD.MM.YYYY HH24:MI:ss'),
        to_date('20.05.2022 20:30:00', 'DD.MM.YYYY HH24:MI:ss'),
        2, 1000);