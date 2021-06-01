insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'TC2');
insert into tennis_court(id, name) values(null, 'TC3');
insert into tennis_court(id, name) values(null, 'TC4');
insert into tennis_court(id, name) values(null, 'TC5');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2021-12-20T20:00:00.0', '2021-12-20T21:00:00.0', 1);
insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2021-12-20T21:00:00.0', '2021-12-20T22:00:00.0', 1);
insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2021-12-20T22:00:00.0', '2021-12-20T23:00:00.0', 1);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2021-12-20T20:00:00.0', '2021-12-20T21:00:00.0', 2);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2021-12-20T21:00:00.0', '2021-12-20T22:00:00.0', 2);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2021-12-20T22:00:00.0', '2021-12-20T23:00:00.0', 2);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2021-12-20T20:00:00.0', '2021-12-20T21:00:00.0', 3);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2021-12-20T21:00:00.0', '2021-12-20T22:00:00.0', 3);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2021-12-20T22:00:00.0', '2021-12-20T23:00:00.0', 3);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2023-12-20T20:00:00.0', '2023-12-20T21:00:00.0', 3);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2023-12-20T21:00:00.0', '2023-12-20T22:00:00.0', 3);
insert
into
    schedule
(id, start_date_time, end_date_time, tennis_court_id)
values
(null, '2023-12-20T22:00:00.0', '2023-12-20T23:00:00.0', 3);