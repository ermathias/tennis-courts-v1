insert into guest(id, name, balance) values(null, 'Roger Federer', 20);
insert into guest(id, name, balance) values(null, 'Rafael Nadal', 10);
insert into guest(id, name, balance) values(null, 'Novak Djokovic', 5);
insert into guest(id, name, balance) values(null, 'Gustavo Kuerten', 100);

insert into admin(id, name, username, password) values (null, 'Tennis Court Admin', 'admin', '123456');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'SESI - Tennis Court #2');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2020-12-20T20:00:00.0', '2020-02-20T21:00:00.0', 1);

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2021-06-10T08:00:00.0', '2021-06-10T09:00:00.0', 2);