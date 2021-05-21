insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Rafael Nadal');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');

insert into schedule(id, start_date_time, end_date_time, tennis_court_id)
    values (null, '2020-12-20T20:00:00.0', '2020-02-20T21:00:00.0', 1),
           (null, '2020-12-20T21:00:00.0', '2020-02-20T22:00:00.0', 1),
           (null, '2030-12-20T20:00:00.0', '2030-02-20T21:00:00.0', 1),
           (null, '2030-12-20T21:00:00.0', '2030-02-20T22:00:00.0', 1);

insert into reservation(reservation_status, value, guest_id, schedule_id)
    values ('READY_TO_PLAY', 10, 1, 1),
           ('READY_TO_PLAY', 10, 1, 3);