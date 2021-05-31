insert into guest(id, name) values(1, 'Roger Federer');
insert into guest(id, name) values(2, 'Rafael Nadal');

insert into tennis_court(id, name) values(1, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(2, 'Court 2 - example2');
insert into tennis_court(id, name) values(3, 'Court 3 - example3');
insert into tennis_court(id, name) values(4, 'Court 4 - example4');


insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (1, '2021-05-31T08:00:00.0', '2021-05-31T09:00:00.0', 1);
insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (2, '2021-05-31T10:00:00.0', '2021-05-31T11:00:00.0', 2);
insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (3, '2021-05-31T00:00:00.0', '2021-06-01T01:00:00.0', 3);
insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (4, '2021-05-30T21:25:00.0', '2021-05-30T22:25:00.0', 4);
