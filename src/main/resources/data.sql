
--It was created a boolen column at Guest Table for checking user role purposes.
insert into guest(id, name, admin) values(null,'Roger Federer','true');
insert into guest(id, name, admin) values(null, 'Rafael Nadal','false');

insert into tennis_court(id, name) values(null, 'Roland Garros - Court Philippe-Chatrier');

insert
    into
        schedule
        (id, start_date_time, end_date_time, tennis_court_id)
    values
        (null, '2020-12-20T20:00:00.0', '2020-02-20T21:00:00.0', 1);