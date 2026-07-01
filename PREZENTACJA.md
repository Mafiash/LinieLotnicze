# Plan Prezentacji — LinieLotnicze REST API
> Przedmiot: TAKE | 6 semestr | Spring Boot 4 + H2 + JPA + HATEOAS + Swagger

---

## 1. Wstęp — co to jest i jak uruchomić

**Projekt:** REST API systemu linii lotniczych.  
**Stack:** Spring Boot 4, Spring Data JPA, H2 (in-memory), HATEOAS, springdoc OpenAPI (Swagger UI).

**Uruchomienie:**
```bash
cd LinieLotnicze
mvn spring-boot:run
```

**Dostępne endpointy po starcie:**
- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

---

## 2. Model danych

| Encja | Tabela | Kluczowe pola |
|---|---|---|
| `Airplane` | `AIRPLANE` | id, model, capacity |
| `Airport` | `AIRPORT` | id, name, code, city |
| `Passenger` | `PASSENGER` | id, firstName, lastName, email |
| `Flight` | `FLIGHT` | id, flightNumber, departureTime, arrivalTime, airplane, origin, destination |
| `Reservation` | `RESERVATION` | id, seatNumber, price, passenger, flight |

**Dane startowe (data.sql):**
- 4 samoloty (Boeing 737, A320, 787 Dreamliner, Embraer 190)
- 4 lotniska (WAW, LHR, LGW, KRK)
- 4 pasażerowie (Kowalski, Nowak, Adamski, Kowalska)
- 3 loty (LO123, LO125, BA456)
- 4 rezerwacje (ID: 1, 3, 4, 5 — ID 2 celowo wolne dla scenariusza POST)

---

## 3. Scenariusze demonstracyjne

---

### SCENARIUSZ 1 — POST: Dodanie nowej rezerwacji (ID = 2)

**Co pokazuje:** operacja CREATE, zwrócenie zapisanego obiektu z nadanym ID.

**Cel:** Pasażer Anna Nowak (id=2) rezerwuje miejsce 7C na locie LO123 (id=1) za 320 zł.

```http
POST http://localhost:8080/reservation
Content-Type: application/json

{
  "seatNumber": "7C",
  "price": 320.00,
  "passenger": { "id": 2 },
  "flight": { "id": 1 }
}
```

**Oczekiwana odpowiedź:**
```json
{
  "id": 2,
  "seatNumber": "7C",
  "price": 320.0,
  "passenger": { "id": 2, "firstName": "Anna", ... },
  "flight": { "id": 1, "flightNumber": "LO123", ... }
}
```

---

### SCENARIUSZ 2 — GET z filtrowaniem: Tablica odlotów z Warszawy

**Co pokazuje:** wyszukiwanie po mieście odlotu, relacja Flight → Airport.

```http
GET http://localhost:8080/flight/search/origin?city=Warszawa
```

**Oczekiwana odpowiedź:** loty LO123 i LO125 (oba odlatują z WAW).

---

### SCENARIUSZ 3 — GET z sortowaniem: Chronologiczna tablica odlotów

**Co pokazuje:** sortowanie po dacie odlotu — jak prawdziwa tablica na lotnisku.

```http
GET http://localhost:8080/flight/sorted
```

**Oczekiwana kolejność:**
1. LO123 — 2026-05-15 10:30
2. LO125 — 2026-05-16 15:00
3. BA456 — 2026-06-01 08:00

---

### SCENARIUSZ 4 — GET z sortowaniem: Lista miejsc na locie LO123

**Co pokazuje:** sortowanie rezerwacji rosnąco po numerze fotela.

```http
GET http://localhost:8080/reservation/search/flight/sorted?flightNumber=LO123
```

**Oczekiwana kolejność foteli:** 02B (VIP 500 zł) → 15A (250 zł) → 18C (150 zł)

---

### SCENARIUSZ 5 — GET finansowy: Tanie bilety (poniżej 200 zł)

**Co pokazuje:** filtrowanie po cenie — np. szukanie promocji.

```http
GET http://localhost:8080/reservation/search/price?maxPrice=200
```

**Oczekiwana odpowiedź:** rezerwacja ID=3 (Anna Nowak, fotel 18C, cena 150 zł) i ID=5 (Adam Adamski, 199.99 zł).

---

### SCENARIUSZ 6 — GET po nazwisku: Wszyscy Kowalscy

**Co pokazuje:** wyszukiwanie case-insensitive po nazwisku.

```http
GET http://localhost:8080/reservation/search/passenger/lastname?lastName=kowalski
```

**Oczekiwana odpowiedź:** rezerwacje dla Jan Kowalski i Katarzyna Kowalska.

---

### SCENARIUSZ 7 — GET relacja zagnieżdżona: Samolot na locie

**Co pokazuje:** nawigacja po relacjach — `/flight/{id}/airplane`.

```http
GET http://localhost:8080/flight/1/airplane
```

**Oczekiwana odpowiedź:** `{"id":1,"model":"Boeing 737","capacity":189}`

Analogicznie: `/flight/1/origin`, `/flight/1/destination`

---

### SCENARIUSZ 8 — HATEOAS: Rezerwacja z linkami

**Co pokazuje:** standard HATEOAS — odpowiedź zawiera linki do powiązanych zasobów.

```http
GET http://localhost:8080/reservation/1/hateoas
```

**Oczekiwana odpowiedź** zawiera pole `_links` z linkami do samej rezerwacji, pasażera i lotu.

Analogicznie działa: `/flight/1/hateoas`, `/passenger/1/hateoas`, `/airport/1/hateoas`, `/airplane/1/hateoas`

---

### SCENARIUSZ 9 — PUT: Aktualizacja ceny biletu

**Co pokazuje:** operacja UPDATE — zmiana ceny rezerwacji ID=1.

```http
PUT http://localhost:8080/reservation
Content-Type: application/json

{
  "id": 1,
  "seatNumber": "15A",
  "price": 199.00,
  "passenger": { "id": 1 },
  "flight": { "id": 1 }
}
```

**Weryfikacja:** `GET http://localhost:8080/reservation/1` — cena powinna wynosić 199.00.

---

### SCENARIUSZ 10 — DELETE + obsługa błędu

**Co pokazuje:** usunięcie zasobu i poprawne zachowanie przy próbie dostępu do nieistniejącego ID.

```http
DELETE http://localhost:8080/reservation/1
```

Następnie:
```http
GET http://localhost:8080/reservation/1
```

**Oczekiwana odpowiedź:** błąd `404 Not Found` (obsługiwany przez `GlobalExceptionHandler`).

---

### SCENARIUSZ 11 — GET zliczanie: Ile miejsc sprzedano na locie?

**Co pokazuje:** agregacja — count w repozytorium.

```http
GET http://localhost:8080/reservation/count?flightNumber=LO123
```

**Oczekiwana odpowiedź:** `3` (przed scenariuszem DELETE), `4` po dodaniu rezerwacji w Scenariuszu 1.

---

### SCENARIUSZ 12 — Swagger UI (opcjonalnie)

**Co pokazuje:** automatyczna dokumentacja API.

`http://localhost:8080/swagger-ui/index.html`

Można pokazać listę wszystkich endpointów i wykonać testowe zapytanie bezpośrednio z przeglądarki.

---

## 4. Dodatkowe endpointy (do wspomnienia)

| Endpoint | Opis |
|---|---|
| `GET /airport/search/city?city=Londyn` | Dwa lotniska w Londynie (LHR + LGW) |
| `GET /airport/search/code?code=WAW` | Lotnisko po kodzie IATA |
| `GET /passenger/sorted` | Pasażerowie alfabetycznie (Adamski → Kowalska → Kowalski → Nowak) |
| `GET /passenger/search/email?emailFragment=test.pl` | Pasażerowie po fragmencie emaila |
| `GET /airplane/search/capacity?minCapacity=200` | Duże samoloty (Boeing 787 Dreamliner) |
| `GET /airplane/sorted-by-capacity` | Flota wg pojemności malejąco |
| `GET /flight/search/route?origin=Warszawa&destination=Londyn` | Połączenia na trasie |

---

## 5. Kolejność prezentacji (sugerowana)

1. Uruchom aplikację, pokaż Swagger UI — 1 min
2. Krótko opisz model danych i dane startowe — 2 min
3. Scenariusz 2 (filtrowanie) + Scenariusz 3 (sortowanie) — tablica odlotów — 3 min
4. Scenariusz 1 (POST) → Scenariusz 11 (count zmieniony) — operacja tworzenia — 2 min
5. Scenariusz 4 (sortowanie foteli) + Scenariusz 5 (filtr ceny) — 2 min
6. Scenariusz 8 (HATEOAS) — 1 min
7. Scenariusz 9 (PUT) + Scenariusz 10 (DELETE + błąd) — 2 min
8. Podsumowanie / pytania — 2 min

**Łącznie: ~15 minut**
