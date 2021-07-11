insert into guest (id, name) values (null, 'Roger Federer');
insert into guest (id, name) values (null, 'Rafael Nadal');

insert into tennis_court(id, name) values (null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values (null, 'Roland Garros - Court Simonne-Mathieu');
insert into tennis_court(id, name) values (null, 'Roland Garros - Court Suzanne-Lenglen');

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-01-10T20:00:00.0', '2020-02-10T21:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-04-10T20:00:00.0', '2020-09-10T21:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2021-03-10T20:00:00.0', '2021-07-10T21:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-02-15T20:00:00.0', '2020-03-30T21:00:00.0', 2);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-03-15T20:00:00.0', '2020-04-30T21:00:00.0', 3);