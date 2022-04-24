update users set password='$2a$10$bmA/gSfTE/VxWMpZlcYHuuXzgawtXS/5IqDNwGxwMZ5StKkITFuru' where id=1; -- password - 1111

insert into roles (name)
values ('USER_RENTER'),
       ('USER_OWNER'),
       ('ADMIN')