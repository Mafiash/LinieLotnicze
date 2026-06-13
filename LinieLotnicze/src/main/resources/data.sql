-- ==========================================
-- 1. FLOTA (Samoloty)
-- ==========================================
INSERT INTO airplane(id, model, capacity) VALUES (1, 'Boeing 737', 189);
INSERT INTO airplane(id, model, capacity) VALUES (2, 'Airbus A320', 160);
INSERT INTO airplane(id, model, capacity) VALUES (3, 'Boeing 787 Dreamliner', 252);
INSERT INTO airplane(id, model, capacity) VALUES (4, 'Embraer 190', 106);

-- ==========================================
-- 2. SIATKA POŁĄCZEŃ (Lotniska)
-- ==========================================
INSERT INTO airport(id, name, code, city) VALUES (1, 'Lotnisko Chopina', 'WAW', 'Warszawa');
INSERT INTO airport(id, name, code, city) VALUES (2, 'Heathrow', 'LHR', 'Londyn');
-- Dodatkowe lotnisko w Londynie do testów wyszukiwania po mieście:
INSERT INTO airport(id, name, code, city) VALUES (3, 'Gatwick', 'LGW', 'Londyn');
-- Dodatkowe lotnisko w Polsce:
INSERT INTO airport(id, name, code, city) VALUES (4, 'Balice', 'KRK', 'Krakow');

-- ==========================================
-- 3. BAZA KLIENTÓW (Pasażerowie)
-- ==========================================
INSERT INTO passenger(id, first_name, last_name, email) VALUES (1, 'Jan', 'Kowalski', 'jan.kowalski@test.pl');
INSERT INTO passenger(id, first_name, last_name, email) VALUES (2, 'Anna', 'Nowak', 'anna.nowak@test.pl');
-- Osoba na 'A' do testów sortowania alfabetycznego:
INSERT INTO passenger(id, first_name, last_name, email) VALUES (3, 'Adam', 'Adamski', 'adam.adamski@firma.com');
-- Druga osoba do wyszukiwania wszystkich 'Kowalskich':
INSERT INTO passenger(id, first_name, last_name, email) VALUES (4, 'Katarzyna', 'Kowalska', 'katarzyna.k@test.pl');

-- ==========================================
-- 4. REJSY (Loty)
-- ==========================================
-- Główny lot z Twoich scenariuszy:
INSERT INTO flight(id, flight_number, departure_time, arrival_time, airplane_id, origin_id, destination_id) 
VALUES (1, 'LO123', '2026-05-15 10:30:00', '2026-05-15 12:05:00', 1, 1, 2);

-- Lot późniejszy (do testów sortowania chronologicznego na tablicy odlotów):
INSERT INTO flight(id, flight_number, departure_time, arrival_time, airplane_id, origin_id, destination_id) 
VALUES (2, 'LO125', '2026-05-16 15:00:00', '2026-05-16 16:30:00', 3, 1, 3);

-- Lot z innego miasta (do testów filtrowania Skąd/Dokąd):
INSERT INTO flight(id, flight_number, departure_time, arrival_time, airplane_id, origin_id, destination_id) 
VALUES (3, 'BA456', '2026-06-01 08:00:00', '2026-06-01 09:45:00', 2, 4, 2);

-- ==========================================
-- 5. SPRZEDANE BILETY (Rezerwacje)
-- ==========================================
-- Rezerwacja nr 1 (Główna, którą będziesz edytować i usuwać w scenariuszach POST/DELETE)
INSERT INTO reservation(id, seat_number, price, passenger_id, flight_id) 
VALUES (1, '15A', 250.00, 1, 1);

-- UWAGA: Celowo omijamy ID 2, ponieważ Scenariusz 1 (POST) utworzy właśnie rezerwację nr 2!

-- Bilet promocyjny poniżej 200 zł (Scenariusz finansowy)
INSERT INTO reservation(id, seat_number, price, passenger_id, flight_id) 
VALUES (3, '18C', 150.00, 2, 1);

-- Bilet VIP z niskim numerem fotela (do udowodnienia sortowania rosnącego po fotelach na locie LO123)
INSERT INTO reservation(id, seat_number, price, passenger_id, flight_id) 
VALUES (4, '02B', 500.00, 4, 1);

-- Bilet na inny lot
INSERT INTO reservation(id, seat_number, price, passenger_id, flight_id) 
VALUES (5, '10F', 199.99, 3, 2);

-- ==========================================
-- 6. RESETOWANIE LICZNIKÓW ID (Dla operacji POST)
-- ==========================================
ALTER TABLE airplane ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM airplane);
ALTER TABLE airport ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM airport);
ALTER TABLE passenger ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM passenger);
ALTER TABLE flight ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM flight);
ALTER TABLE reservation ALTER COLUMN id RESTART WITH (SELECT MAX(id) + 1 FROM reservation);