insert into guest(id, name) values(1, 'Roger Federer');
insert into guest(id, name) values(2, 'Rafael Nadal');

insert into tennis_court(id, name) values(1, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(2, 'Roland Garros - Court Philippe-Chatrier_2');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (1, '2021-12-20T20:00:00.0', '2021-12-20T21:00:00.0', 1);

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (2, '2021-12-20T21:00:00.0', '2021-12-20T22:00:00.0', 2);


insert into reservation (ID, RESERVATION_STATUS, VALUE, SCHEDULE_ID,GUEST_ID  ) values(1, 0, 10, 1,1);

