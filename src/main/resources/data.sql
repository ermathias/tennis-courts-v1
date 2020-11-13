insert into guest(id, name) values(null, 'Novak Djokovic');
insert into guest(id, name) values(null, 'Rafael Nadal');
insert into guest(id, name) values(null, 'Roger Federer');
insert into guest(id, name) values(null, 'Pete Sampras');
insert into guest(id, name) values(null, 'Andre Agassi');
insert into guest(id, name) values(null, 'Boris Becker');
insert into guest(id, name) values(null, 'Mats Wilander');
insert into guest(id, name) values(null, 'Ivan Lendl');
insert into guest(id, name) values(null, 'John Mc Enroe');
insert into guest(id, name) values(null, 'Jimmy Connors');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values(null, 'Roland Garros - Simonne-Mathieu');
insert into tennis_court(id, name) values(null, 'Roland Garros - Suzanne-Lenglen');

insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2021-12-20T20:00:00.0', '2021-12-20T21:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2021-12-20T19:00:00.0', '2021-12-20T20:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2021-12-20T18:00:00.0', '2021-12-20T19:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-12-20T17:00:00.0', '2020-12-20T18:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-12-20T16:00:00.0', '2020-12-20T17:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-10-20T20:00:00.0', '2020-10-20T21:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-10-20T19:00:00.0', '2020-10-20T20:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-10-20T18:00:00.0', '2020-10-20T19:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-10-20T17:00:00.0', '2020-10-20T18:00:00.0', 1);
insert into schedule(id, start_date_time, end_date_time, tennis_court_id) values (null, '2020-10-20T16:00:00.0', '2020-10-20T17:00:00.0', 1);

insert into reservation(id, date_create, date_update, ip_number_create, ip_number_update, user_create, user_update, guest_id, refund_value, reservation_status, schedule_id, value)
    values (null, '2020-11-08T09:42:56.304', '2020-11-08T09:42:56.304', '192.168.18.11', '192.168.18.11', 1, 1, 1, 10, 'READY_TO_PLAY', 1, 10);
insert into reservation(id, date_create, date_update, ip_number_create, ip_number_update, user_create, user_update, guest_id, refund_value, reservation_status, schedule_id, value)
    values (null, '2020-11-08T09:42:56.304', '2020-11-08T09:42:56.304', '192.168.18.11', '192.168.18.11', 1, 1, 1, 10, 'READY_TO_PLAY', 2, 10);
insert into reservation(id, date_create, date_update, ip_number_create, ip_number_update, user_create, user_update, guest_id, refund_value, reservation_status, schedule_id, value)
    values (null, '2020-11-08T09:42:56.304', '2020-11-08T09:42:56.304', '192.168.18.11', '192.168.18.11', 1, 1, 1, 10, 'READY_TO_PLAY', 3, 10);
insert into reservation(id, date_create, date_update, ip_number_create, ip_number_update, user_create, user_update, guest_id, refund_value, reservation_status, schedule_id, value)
    values (null, '2020-11-08T09:42:56.304', '2020-11-08T09:42:56.304', '192.168.18.11', '192.168.18.11', 1, 1, 2, 10, 'READY_TO_PLAY', 4, 10);
insert into reservation(id, date_create, date_update, ip_number_create, ip_number_update, user_create, user_update, guest_id, refund_value, reservation_status, schedule_id, value)
    values (null, '2020-11-08T09:42:56.304', '2020-11-08T09:42:56.304', '192.168.18.11', '192.168.18.11', 1, 1, 3, 10, 'READY_TO_PLAY', 5, 10);