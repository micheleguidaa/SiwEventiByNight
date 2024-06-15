INSERT INTO Event (id, name, theme, start_date_time, end_date_time, cost, n_max_participants, url_image) VALUES (nextval('event_seq'), 'Event1', 'Technology', '2024-07-01 10:00:00', '2024-07-01 18:00:00', 50, 100, '{/images/event/HotelButterfly.jpg}');
INSERT INTO Event (id, name, theme, start_date_time, end_date_time, cost, n_max_participants, url_image) VALUES (nextval('event_seq'), 'Event2', 'Art', '2024-08-15 09:00:00', '2024-08-15 17:00:00', 30, 50, '{/images/event/piper.jpg}');
INSERT INTO Event (id, name, theme, start_date_time, end_date_time, cost, n_max_participants, url_image) VALUES (nextval('event_seq'), 'Event3', 'Music', '2024-09-10 14:00:00', '2024-09-10 22:00:00', 40, 200,'{/images/event/spazio900.jpg}');

INSERT INTO Local (id, name, address) VALUES (nextval('local_seq'), 'Location1', '123 Main St, Cityville');
INSERT INTO Local (id, name, address) VALUES (nextval('local_seq'), 'Location2', '456 Elm St, Townsville');
INSERT INTO Local (id, name, address) VALUES (nextval('local_seq'),'Location3', '789 Oak St, Villageville');

