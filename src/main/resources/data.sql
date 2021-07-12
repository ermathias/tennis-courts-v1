insert into guest (id, name) values (null, 'Roger Federer');
insert into guest (id, name) values (null, 'Rafael Nadal');

insert into tennis_court(id, name) values (null, 'Roland Garros - Court Philippe-Chatrier');
insert into tennis_court(id, name) values (null, 'Roland Garros - Court Simonne-Mathieu');
insert into tennis_court(id, name) values (null, 'Roland Garros - Court Suzanne-Lenglen');

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-01-10T08:00:00.0', '2020-01-10T20:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-01-11T08:00:00.0', '2020-01-11T20:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-01-12T08:00:00.0', '2020-01-12T20:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-03-29T08:00:00.0', '2020-03-29T20:00:00.0', 1);

insert into schedule (id, start_date_time, end_date_time, tennis_court_id)
values (null, '2020-06-21T08:00:00.0', '2020-06-21T20:00:00.0', 1);