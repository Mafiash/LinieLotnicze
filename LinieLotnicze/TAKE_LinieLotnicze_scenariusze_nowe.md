# Nowe Scenariusze Użycia REST API - Linie Lotnicze (TAKE)

Dokument ten przedstawia uaktualnione scenariusze użycia dla systemu rezerwacji linii lotniczych po przeprowadzonej refaktoryzacji API.


## ⚡ Scenariusze Użycia (1 - 29)

### 1. Rezerwacja Lotu
* **Żądanie:**
  `POST http://localhost:8080/reservations`
  ```json
  { 
      "seatNumber": "12B", 
      "price": 300.00, 
      "passenger": { "id": 1 }, 
      "flight": { "id": 1 } 
  }
  ```
* **Status odpowiedzi:** `201 Created`
* **Nagłówki odpowiedzi:**
  `Location: http://localhost:8080/reservations/6`
* **Ciało odpowiedzi:**
  ```json
  { 
      "id": 6, 
      "seatNumber": "12B", 
      "price": 300.0,
      "_links": {
          "self": { "href": "http://localhost:8080/reservations/6" },
          "passenger": { "href": "http://localhost:8080/reservations/6/passenger" },
          "flight": { "href": "http://localhost:8080/reservations/6/flight" }
      }
  }
  ```

---

### 2. Modyfikacja Rezerwacji (zmiana miejsca i ceny)
* **Żądanie:**
  `PUT http://localhost:8080/reservations/1`
  ```json
  { 
      "seatNumber": "12F", 
      "price": 250.00, 
      "passenger": { "id": 1 }, 
      "flight": { "id": 1 } 
  }
  ```
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  { 
      "id": 1, 
      "seatNumber": "12F", 
      "price": 250.0,
      "_links": {
          "self": { "href": "http://localhost:8080/reservations/1" },
          "passenger": { "href": "http://localhost:8080/reservations/1/passenger" },
          "flight": { "href": "http://localhost:8080/reservations/1/flight" }
      }
  }
  ```

---

### 3. Wyświetlenie rezerwacji użytkownika po jego emailu
* **Żądanie:**
  `GET http://localhost:8080/reservations?email=jan.kowalski@test.pl`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      { 
          "id": 1, 
          "seatNumber": "12F", 
          "price": 250.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/1" },
              "passenger": { "href": "http://localhost:8080/reservations/1/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/1/flight" }
          }
      },
      { 
          "id": 6, 
          "seatNumber": "12B", 
          "price": 300.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/6" },
              "passenger": { "href": "http://localhost:8080/reservations/6/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/6/flight" }
          }
      }
  ]
  ```

---

### 4. Anulowanie rezerwacji
* **Żądanie:**
  `DELETE http://localhost:8080/reservations/1`
* **Status odpowiedzi:** `204 No Content`
* **Ciało odpowiedzi:** *(puste)*

---

### 5. Wyszukiwarka połączeń (bezpośrednia trasa z Warszawy do Londynu)
* **Żądanie:**
  `GET http://localhost:8080/flights?origin=Warszawa&destination=Londyn`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "flightNumber": "LO123",
          "departureTime": "2026-05-15T10:30:00",
          "arrivalTime": "2026-05-15T12:05:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/1" },
              "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
              "origin": { "href": "http://localhost:8080/flights/1/origin" },
              "destination": { "href": "http://localhost:8080/flights/1/destination" }
          }
      },
      {
          "id": 2,
          "flightNumber": "LO125",
          "departureTime": "2026-05-16T15:00:00",
          "arrivalTime": "2026-05-16T16:30:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/2" },
              "airplane": { "href": "http://localhost:8080/flights/2/airplane" },
              "origin": { "href": "http://localhost:8080/flights/2/origin" },
              "destination": { "href": "http://localhost:8080/flights/2/destination" }
          }
      }
  ]
  ```

---

### 6. Wyszukiwarka lotów do danego miasta (np. Londyn)
* **Żądanie:**
  `GET http://localhost:8080/flights?destination=Londyn`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "flightNumber": "LO123",
          "departureTime": "2026-05-15T10:30:00",
          "arrivalTime": "2026-05-15T12:05:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/1" },
              "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
              "origin": { "href": "http://localhost:8080/flights/1/origin" },
              "destination": { "href": "http://localhost:8080/flights/1/destination" }
          }
      },
      {
          "id": 2,
          "flightNumber": "LO125",
          "departureTime": "2026-05-16T15:00:00",
          "arrivalTime": "2026-05-16T16:30:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/2" },
              "airplane": { "href": "http://localhost:8080/flights/2/airplane" },
              "origin": { "href": "http://localhost:8080/flights/2/origin" },
              "destination": { "href": "http://localhost:8080/flights/2/destination" }
          }
      },
      {
          "id": 3,
          "flightNumber": "BA456",
          "departureTime": "2026-06-01T08:00:00",
          "arrivalTime": "2026-06-01T09:45:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/3" },
              "airplane": { "href": "http://localhost:8080/flights/3/airplane" },
              "origin": { "href": "http://localhost:8080/flights/3/origin" },
              "destination": { "href": "http://localhost:8080/flights/3/destination" }
          }
      }
  ]
  ```

---

### 7. Szukanie profilu klienta (wyszukiwanie pasażera po nazwisku)
* **Żądanie:**
  `GET http://localhost:8080/passengers?lastName=Kowalski`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "firstName": "Jan",
          "lastName": "Kowalski",
          "email": "jan.kowalski@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/1" },
              "reservations": { "href": "http://localhost:8080/reservations?email=jan.kowalski@test.pl" }
          }
      }
  ]
  ```

---

### 8. Lista rezerwacji dla danego lotu (np. LO123)
* **Żądanie:**
  `GET http://localhost:8080/reservations?flightNumber=LO123`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "seatNumber": "12F",
          "price": 250.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/1" },
              "passenger": { "href": "http://localhost:8080/reservations/1/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/1/flight" }
          }
      },
      {
          "id": 3,
          "seatNumber": "18C",
          "price": 150.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/3" },
              "passenger": { "href": "http://localhost:8080/reservations/3/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/3/flight" }
          }
      },
      {
          "id": 4,
          "seatNumber": "02B",
          "price": 500.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/4" },
              "passenger": { "href": "http://localhost:8080/reservations/4/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/4/flight" }
          }
      },
      {
          "id": 6,
          "seatNumber": "12B",
          "price": 300.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/6" },
              "passenger": { "href": "http://localhost:8080/reservations/6/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/6/flight" }
          }
      }
  ]
  ```

---

### 9. Szukanie rezerwacji po docelowym miejscu (np. rezerwacje na loty do Londynu)
* **Żądanie:**
  `GET http://localhost:8080/reservations?city=Londyn`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "seatNumber": "12F",
          "price": 250.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/1" },
              "passenger": { "href": "http://localhost:8080/reservations/1/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/1/flight" }
          }
      },
      {
          "id": 3,
          "seatNumber": "18C",
          "price": 150.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/3" },
              "passenger": { "href": "http://localhost:8080/reservations/3/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/3/flight" }
          }
      },
      {
          "id": 4,
          "seatNumber": "02B",
          "price": 500.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/4" },
              "passenger": { "href": "http://localhost:8080/reservations/4/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/4/flight" }
          }
      },
      {
          "id": 5,
          "seatNumber": "10F",
          "price": 199.99,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/5" },
              "passenger": { "href": "http://localhost:8080/reservations/5/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/5/flight" }
          }
      },
      {
          "id": 6,
          "seatNumber": "12B",
          "price": 300.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/6" },
              "passenger": { "href": "http://localhost:8080/reservations/6/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/6/flight" }
          }
      }
  ]
  ```

---

### 10. Wyświetlenie liczby zajętych miejsc w samolocie dla danego lotu
* **Żądanie:**
  `GET http://localhost:8080/reservations/count?flightNumber=LO123`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  `4`

---

### 11. Zmiana samolotu dla danego lotu
* **Żądanie:**
  `PUT http://localhost:8080/flights/1`
  ```json
  { 
      "flightNumber": "LO123", 
      "departureTime": "2026-05-15T10:30:00", 
      "arrivalTime": "2026-05-15T12:05:00", 
      "airplane": { "id": 2 },  
      "origin": { "id": 1 }, 
      "destination": { "id": 2 } 
  }
  ```
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  {
      "id": 1,
      "flightNumber": "LO123",
      "departureTime": "2026-05-15T10:30:00",
      "arrivalTime": "2026-05-15T12:05:00",
      "_links": {
          "self": { "href": "http://localhost:8080/flights/1" },
          "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
          "origin": { "href": "http://localhost:8080/flights/1/origin" },
          "destination": { "href": "http://localhost:8080/flights/1/destination" }
      }
  }
  ```

---

### 12. Wyszukiwanie samolotów o minimalnej pojemności
* **Żądanie:**
  `GET http://localhost:8080/airplanes?minCapacity=180`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "model": "Boeing 737",
          "capacity": 189,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/1" } }
      },
      {
          "id": 3,
          "model": "Boeing 787 Dreamliner",
          "capacity": 252,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/3" } }
      }
  ]
  ```

---

### 13. Wyszukiwanie odlotów z danego miasta
* **Żądanie:**
  `GET http://localhost:8080/flights?origin=Warszawa`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "flightNumber": "LO123",
          "departureTime": "2026-05-15T10:30:00",
          "arrivalTime": "2026-05-15T12:05:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/1" },
              "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
              "origin": { "href": "http://localhost:8080/flights/1/origin" },
              "destination": { "href": "http://localhost:8080/flights/1/destination" }
          }
      },
      {
          "id": 2,
          "flightNumber": "LO125",
          "departureTime": "2026-05-16T15:00:00",
          "arrivalTime": "2026-05-16T16:30:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/2" },
              "airplane": { "href": "http://localhost:8080/flights/2/airplane" },
              "origin": { "href": "http://localhost:8080/flights/2/origin" },
              "destination": { "href": "http://localhost:8080/flights/2/destination" }
          }
      }
  ]
  ```

---

### 14. Sprawdzanie informacji o danym locie (po numerze lotu)
* **Żądanie:**
  `GET http://localhost:8080/flights?flightNumber=LO123`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "flightNumber": "LO123",
          "departureTime": "2026-05-15T10:30:00",
          "arrivalTime": "2026-05-15T12:05:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/1" },
              "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
              "origin": { "href": "http://localhost:8080/flights/1/origin" },
              "destination": { "href": "http://localhost:8080/flights/1/destination" }
          }
      }
  ]
  ```

---

### 15. Posortowane loty po dacie wylotu
* **Żądanie:**
  `GET http://localhost:8080/flights?sortBy=departureTime`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "flightNumber": "LO123",
          "departureTime": "2026-05-15T10:30:00",
          "arrivalTime": "2026-05-15T12:05:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/1" },
              "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
              "origin": { "href": "http://localhost:8080/flights/1/origin" },
              "destination": { "href": "http://localhost:8080/flights/1/destination" }
          }
      },
      {
          "id": 2,
          "flightNumber": "LO125",
          "departureTime": "2026-05-16T15:00:00",
          "arrivalTime": "2026-05-16T16:30:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/2" },
              "airplane": { "href": "http://localhost:8080/flights/2/airplane" },
              "origin": { "href": "http://localhost:8080/flights/2/origin" },
              "destination": { "href": "http://localhost:8080/flights/2/destination" }
          }
      },
      {
          "id": 3,
          "flightNumber": "BA456",
          "departureTime": "2026-06-01T08:00:00",
          "arrivalTime": "2026-06-01T09:45:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/3" },
              "airplane": { "href": "http://localhost:8080/flights/3/airplane" },
              "origin": { "href": "http://localhost:8080/flights/3/origin" },
              "destination": { "href": "http://localhost:8080/flights/3/destination" }
          }
      }
  ]
  ```

---

### 16. Posortowane loty z danym miejscem przylotu (do Londynu, posortowane chronologicznie)
* **Żądanie:**
  `GET http://localhost:8080/flights?destination=Londyn&sortBy=departureTime`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "flightNumber": "LO123",
          "departureTime": "2026-05-15T10:30:00",
          "arrivalTime": "2026-05-15T12:05:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/1" },
              "airplane": { "href": "http://localhost:8080/flights/1/airplane" },
              "origin": { "href": "http://localhost:8080/flights/1/origin" },
              "destination": { "href": "http://localhost:8080/flights/1/destination" }
          }
      },
      {
          "id": 2,
          "flightNumber": "LO125",
          "departureTime": "2026-05-16T15:00:00",
          "arrivalTime": "2026-05-16T16:30:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/2" },
              "airplane": { "href": "http://localhost:8080/flights/2/airplane" },
              "origin": { "href": "http://localhost:8080/flights/2/origin" },
              "destination": { "href": "http://localhost:8080/flights/2/destination" }
          }
      },
      {
          "id": 3,
          "flightNumber": "BA456",
          "departureTime": "2026-06-01T08:00:00",
          "arrivalTime": "2026-06-01T09:45:00",
          "_links": {
              "self": { "href": "http://localhost:8080/flights/3" },
              "airplane": { "href": "http://localhost:8080/flights/3/airplane" },
              "origin": { "href": "http://localhost:8080/flights/3/origin" },
              "destination": { "href": "http://localhost:8080/flights/3/destination" }
          }
      }
  ]
  ```

---

### 17. Wyszukiwanie rodziny samolotów (np. Boeing)
* **Żądanie:**
  `GET http://localhost:8080/airplanes?modelFragment=boeing`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "model": "Boeing 737",
          "capacity": 189,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/1" } }
      },
      {
          "id": 3,
          "model": "Boeing 787 Dreamliner",
          "capacity": 252,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/3" } }
      }
  ]
  ```

---

### 18. Samoloty posortowane według wielkości (od największego)
* **Żądanie:**
  `GET http://localhost:8080/airplanes?sortBy=capacity`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 3,
          "model": "Boeing 787 Dreamliner",
          "capacity": 252,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/3" } }
      },
      {
          "id": 1,
          "model": "Boeing 737",
          "capacity": 189,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/1" } }
      },
      {
          "id": 2,
          "model": "Airbus A320",
          "capacity": 160,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/2" } }
      },
      {
          "id": 4,
          "model": "Embraer 190",
          "capacity": 106,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/4" } }
      }
  ]
  ```

---

### 19. Dobór samolotu odpowiedniej wielkości (zakres pojemności od 150 do 170)
* **Żądanie:**
  `GET http://localhost:8080/airplanes?minCapacity=150&maxCapacity=170`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 2,
          "model": "Airbus A320",
          "capacity": 160,
          "_links": { "self": { "href": "http://localhost:8080/airplanes/2" } }
      }
  ]
  ```

---

### 20. Wyświetlanie danych lotniska po kodzie IATA (np. WAW)
* **Żądanie:**
  `GET http://localhost:8080/airports?code=WAW`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "name": "Lotnisko Chopina",
          "code": "WAW",
          "city": "Warszawa",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/1" },
              "departures": { "href": "http://localhost:8080/flights?origin=Warszawa" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Warszawa" }
          }
      }
  ]
  ```

---

### 21. Wyszukiwanie lotnisk w danym mieście (np. Londyn)
* **Żądanie:**
  `GET http://localhost:8080/airports?city=Londyn`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 2,
          "name": "Heathrow",
          "code": "LHR",
          "city": "Londyn",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/2" },
              "departures": { "href": "http://localhost:8080/flights?origin=Londyn" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Londyn" }
          }
      },
      {
          "id": 3,
          "name": "Gatwick",
          "code": "LGW",
          "city": "Londyn",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/3" },
              "departures": { "href": "http://localhost:8080/flights?origin=Londyn" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Londyn" }
          }
      }
  ]
  ```

---

### 22. Lista lotnisk ułożona alfabetycznie (po mieście)
* **Żądanie:**
  `GET http://localhost:8080/airports?sortBy=city`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 4,
          "name": "Balice",
          "code": "KRK",
          "city": "Krakow",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/4" },
              "departures": { "href": "http://localhost:8080/flights?origin=Krakow" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Krakow" }
          }
      },
      {
          "id": 2,
          "name": "Heathrow",
          "code": "LHR",
          "city": "Londyn",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/2" },
              "departures": { "href": "http://localhost:8080/flights?origin=Londyn" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Londyn" }
          }
      },
      {
          "id": 3,
          "name": "Gatwick",
          "code": "LGW",
          "city": "Londyn",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/3" },
              "departures": { "href": "http://localhost:8080/flights?origin=Londyn" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Londyn" }
          }
      },
      {
          "id": 1,
          "name": "Lotnisko Chopina",
          "code": "WAW",
          "city": "Warszawa",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/1" },
              "departures": { "href": "http://localhost:8080/flights?origin=Warszawa" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Warszawa" }
          }
      }
  ]
  ```

---

### 23. Wyszukiwanie lotniska po fragmencie nazwy (np. chopina)
* **Żądanie:**
  `GET http://localhost:8080/airports?nameFragment=chopina`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "name": "Lotnisko Chopina",
          "code": "WAW",
          "city": "Warszawa",
          "_links": {
              "self": { "href": "http://localhost:8080/airports/1" },
              "departures": { "href": "http://localhost:8080/flights?origin=Warszawa" },
              "arrivals": { "href": "http://localhost:8080/flights?destination=Warszawa" }
          }
      }
  ]
  ```

---

### 24. Wyszukiwanie biletów w zadanej cenie (np. do 200.00 PLN)
* **Żądanie:**
  `GET http://localhost:8080/reservations?maxPrice=200.0`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 3,
          "seatNumber": "18C",
          "price": 150.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/3" },
              "passenger": { "href": "http://localhost:8080/reservations/3/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/3/flight" }
          }
      },
      {
          "id": 5,
          "seatNumber": "10F",
          "price": 199.99,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/5" },
              "passenger": { "href": "http://localhost:8080/reservations/5/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/5/flight" }
          }
      }
  ]
  ```

---

### 25. Lista rezerwacji według nazwiska danej osoby (np. kowalski)
* **Żądanie:**
  `GET http://localhost:8080/reservations?lastName=kowalski`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "seatNumber": "12F",
          "price": 250.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/1" },
              "passenger": { "href": "http://localhost:8080/reservations/1/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/1/flight" }
          }
      },
      {
          "id": 6,
          "seatNumber": "12B",
          "price": 300.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/6" },
              "passenger": { "href": "http://localhost:8080/reservations/6/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/6/flight" }
          }
      }
  ]
  ```

---

### 26. Lista zarezerwowanych miejsc dla danego lotu posortowana rosnąco po numerach siedzeń
* **Żądanie:**
  `GET http://localhost:8080/reservations?flightNumber=LO123&sortBy=seatNumber`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 4,
          "seatNumber": "02B",
          "price": 500.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/4" },
              "passenger": { "href": "http://localhost:8080/reservations/4/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/4/flight" }
          }
      },
      {
          "id": 6,
          "seatNumber": "12B",
          "price": 300.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/6" },
              "passenger": { "href": "http://localhost:8080/reservations/6/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/6/flight" }
          }
      },
      {
          "id": 1,
          "seatNumber": "12F",
          "price": 250.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/1" },
              "passenger": { "href": "http://localhost:8080/reservations/1/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/1/flight" }
          }
      },
      {
          "id": 3,
          "seatNumber": "18C",
          "price": 150.0,
          "_links": {
              "self": { "href": "http://localhost:8080/reservations/3" },
              "passenger": { "href": "http://localhost:8080/reservations/3/passenger" },
              "flight": { "href": "http://localhost:8080/reservations/3/flight" }
          }
      }
  ]
  ```

---

### 27. Wyszukiwanie pasażera (po imieniu LUB nazwisku)
* **Żądanie:**
  `GET http://localhost:8080/passengers?firstName=Kowalski&lastName=Kowalski`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "firstName": "Jan",
          "lastName": "Kowalski",
          "email": "jan.kowalski@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/1" },
              "reservations": { "href": "http://localhost:8080/reservations?email=jan.kowalski@test.pl" }
          }
      }
  ]
  ```

---

### 28. Zwracanie profili o danym mailu (np. zawierające @test.pl)
* **Żądanie:**
  `GET http://localhost:8080/passengers?emailFragment=@test.pl`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 1,
          "firstName": "Jan",
          "lastName": "Kowalski",
          "email": "jan.kowalski@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/1" },
              "reservations": { "href": "http://localhost:8080/reservations?email=jan.kowalski@test.pl" }
          }
      },
      {
          "id": 2,
          "firstName": "Anna",
          "lastName": "Nowak",
          "email": "anna.nowak@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/2" },
              "reservations": { "href": "http://localhost:8080/reservations?email=anna.nowak@test.pl" }
          }
      },
      {
          "id": 4,
          "firstName": "Katarzyna",
          "lastName": "Kowalska",
          "email": "katarzyna.k@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/4" },
              "reservations": { "href": "http://localhost:8080/reservations?email=katarzyna.k@test.pl" }
          }
      }
  ]
  ```

---

### 29. Wyświetlenie pasażerów posortowanych po nazwisku
* **Żądanie:**
  `GET http://localhost:8080/passengers?sortBy=lastName`
* **Status odpowiedzi:** `200 OK`
* **Ciało odpowiedzi:**
  ```json
  [
      {
          "id": 3,
          "firstName": "Adam",
          "lastName": "Adamski",
          "email": "adam.adamski@firma.com",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/3" },
              "reservations": { "href": "http://localhost:8080/reservations?email=adam.adamski@firma.com" }
          }
      },
      {
          "id": 4,
          "firstName": "Katarzyna",
          "lastName": "Kowalska",
          "email": "katarzyna.k@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/4" },
              "reservations": { "href": "http://localhost:8080/reservations?email=katarzyna.k@test.pl" }
          }
      },
      {
          "id": 1,
          "firstName": "Jan",
          "lastName": "Kowalski",
          "email": "jan.kowalski@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/1" },
              "reservations": { "href": "http://localhost:8080/reservations?email=jan.kowalski@test.pl" }
          }
      },
      {
          "id": 2,
          "firstName": "Anna",
          "lastName": "Nowak",
          "email": "anna.nowak@test.pl",
          "_links": {
              "self": { "href": "http://localhost:8080/passengers/2" },
              "reservations": { "href": "http://localhost:8080/reservations?email=anna.nowak@test.pl" }
          }
      }
  ]
  ```
