
INSERT INTO airplane(id, model, capacity) VALUES (1, 'Boeing 737', 189);

INSERT INTO airport(id, name, code, city) VALUES (1, 'Lotnisko Chopina', 'WAW', 'Warszawa');
INSERT INTO airport(id, name, code, city) VALUES (2, 'Heathrow', 'LHR', 'Londyn');

INSERT INTO passenger(id, first_name, last_name, email) VALUES (1, 'Jan', 'Kowalski', 'jan.kowalski@test.pl');

INSERT INTO flight(id, flight_number, departure_time, arrival_time, airplane_id, origin_id, destination_id) 
VALUES (1, 'LO123', '2026-05-15 10:30:00', '2026-05-15 12:05:00', 1, 1, 2);

INSERT INTO reservation(id, seat_number, price, passenger_id, flight_id) 
VALUES (1, '15A', 250.00, 1, 1);