---
---

# Polityka prywatności WeatherVibe

**Data wejścia w życie:** 8 maja 2026 r.
**Ostatnia aktualizacja:** 8 maja 2026 r.

Niniejsza polityka prywatności opisuje, w jaki sposób aplikacja mobilna **WeatherVibe** ("
Aplikacja") obsługuje dane użytkowników. Twórca aplikacji ("my", "nasz") szanuje Twoją prywatność i
ogranicza przetwarzanie danych do minimum niezbędnego do działania funkcji pogodowych.

W skrócie: **nie posiadamy własnych serwerów, nie zbieramy danych analitycznych, nie sprzedajemy
informacji o użytkownikach i nie wyświetlamy reklam.**

---

## 1. Administrator danych

- **Administrator:** Adrian Zalewski (twórca aplikacji)
- **Kontakt:** weathervibe.support@gmail.com

---

## 2. Jakie dane Aplikacja przetwarza

### 2.1. Lokalizacja urządzenia

Aplikacja prosi o uprawnienia `ACCESS_FINE_LOCATION` oraz `ACCESS_COARSE_LOCATION`, aby pobrać
prognozę pogody dla Twojego aktualnego położenia.

- Lokalizacja jest odczytywana **wyłącznie na żądanie** (po Twojej zgodzie systemowej oraz ręcznym
  odświeżeniu pogody).
- Współrzędne (`szerokość`/`długość geograficzna`) są wysyłane do dostawcy danych pogodowych (zob.
  sekcja 3) wyłącznie w celu pobrania prognozy.
- **Nie zapisujemy historii lokalizacji.** W bazie lokalnej urządzenia przechowujemy jedynie
  współrzędne lokalizacji, które **sam(a) ręcznie dodasz** do listy ulubionych, aby ich pogodę można
  było wyświetlić ponownie bez ponownego wyszukiwania.
- Lokalizacja **nie jest udostępniana** żadnym podmiotom poza dostawcami pogody wymienionymi w
  sekcji 3.

### 2.2. Powiadomienia

Aplikacja prosi o uprawnienie `POST_NOTIFICATIONS`, aby wysyłać Ci lokalne powiadomienia (np.
poranny brief pogodowy, alerty o pyłkach, przypomnienia o ocenie nastroju).

- Wszystkie powiadomienia generowane są **lokalnie na urządzeniu**.
- Nie używamy push notifications ani serwerów typu Firebase Cloud Messaging.

### 2.3. Dane wprowadzane przez użytkownika

W Aplikacji możesz wprowadzić:

- **Nazwę użytkownika** (np. imię) — wykorzystywaną wyłącznie do personalizacji powitania na ekranie
  profilu.
- **Ocenę nastroju** (vibe rating) — zapisaną lokalnie do śledzenia osobistej historii.
- **Preferencje** (motyw, ton briefu, ulubione lokalizacje, preferencje powiadomień) — zapisane
  lokalnie.

Wszystkie powyższe dane są przechowywane **wyłącznie na Twoim urządzeniu** w bazie Room oraz Proto
DataStore. Nie są wysyłane na żadne serwery, z wyjątkiem przypadków opisanych w sekcji 3.

### 2.4. Dane techniczne

Aplikacja **nie korzysta** z narzędzi analitycznych takich jak Firebase Analytics, Google Analytics,
Crashlytics, Mixpanel ani podobnych. Nie zbieramy informacji o:

- modelu urządzenia,
- wersji systemu,
- crashach,
- czasie spędzonym w aplikacji,
- klikniętych ekranach.

---

## 3. Komu udostępniamy dane

Aplikacja korzysta z następujących usług zewnętrznych. Każda z nich otrzymuje wyłącznie minimalny
zakres danych niezbędny do realizacji funkcji.

### 3.1. Open-Meteo (dostawca prognoz pogody)

- **Cel:** pobieranie prognozy pogody, danych o jakości powietrza oraz wyszukiwanie lokalizacji po
  nazwie.
- **Wysyłane dane:** współrzędne geograficzne (`szerokość`/`długość`) lub nazwa miejscowości wpisana
  w wyszukiwarce.
- **Adresy:** `api.open-meteo.com`, `air-quality-api.open-meteo.com`,
  `geocoding-api.open-meteo.com`.
- **Polityka prywatności dostawcy:** https://open-meteo.com/en/terms

Open-Meteo nie wymaga konta ani identyfikatora użytkownika. Nie wysyłamy do nich Twojej nazwy,
e-maila ani identyfikatora urządzenia.

### 3.2. Anthropic (dostawca generatywnej AI)

- **Cel:** generowanie spersonalizowanego briefu pogodowego ("AI brief"), w którym język i ton są
  dostosowane do Twoich preferencji.
- **Wysyłane dane:** prompt tekstowy zawierający:
    - aktualne dane pogodowe (temperatura, wiatr, opady, jakość powietrza),
    - wybrany przez Ciebie ton briefu (np. *humorystyczny*, *formalny*),
    - opcjonalnie ocenę nastroju, jeśli ją podałeś(-aś).
- **Adres:** `api.anthropic.com`
- **Polityka prywatności dostawcy:** https://www.anthropic.com/legal/privacy

**Nie wysyłamy** Twojej nazwy użytkownika, e-maila, lokalizacji w postaci współrzędnych,
identyfikatora urządzenia ani żadnych innych danych pozwalających na bezpośrednią identyfikację.
Zgodnie z polityką Anthropic, treści przesłane do API komercyjnego nie są wykorzystywane do
trenowania modeli.

### 3.3. Brak innych odbiorców

Nie udostępniamy danych żadnym sieciom reklamowym, brokerom danych, narzędziom analitycznym ani
serwisom społecznościowym.

---

## 4. Przechowywanie danych

- Dane wymienione w sekcji 2.3 są przechowywane **wyłącznie lokalnie** na Twoim urządzeniu.
- Nie posiadamy serwera bazodanowego ani konta w chmurze, na którym przechowywane byłyby Twoje dane.
- Odinstalowanie aplikacji powoduje **trwałe usunięcie** wszystkich danych z urządzenia.
- Możesz w każdej chwili wyczyścić dane aplikacji przez Ustawienia systemu Android → Aplikacje →
  WeatherVibe → Przechowywanie → Wyczyść dane.

---

## 5. Okres przechowywania

Lokalne dane (preferencje, historia ocen nastroju, ulubione lokalizacje, cache pogody) przechowywane
są **bezterminowo** lub do momentu, w którym sam(a) je usuniesz albo odinstalujesz aplikację.

---

## 6. Twoje prawa (RODO)

Ponieważ wszystkie dane osobowe pozostają na Twoim urządzeniu, masz nad nimi pełną kontrolę. Możesz:

- **przeglądać** je w odpowiednich ekranach aplikacji (profil, lokalizacje, historia vibe),
- **modyfikować** je w aplikacji,
- **usunąć** je przez wyczyszczenie danych aplikacji lub jej odinstalowanie.

Jeśli masz pytania dotyczące przetwarzania danych lub chcesz skorzystać z innych praw wynikających z
RODO (m.in. prawo do informacji, sprzeciwu, ograniczenia przetwarzania), skontaktuj się z nami: *
*weathervibe.support@gmail.com**.

W przypadku, w którym uznasz, że Twoje prawa zostały naruszone, możesz złożyć skargę do organu
nadzorczego — w Polsce jest nim **Prezes Urzędu Ochrony Danych Osobowych** (https://uodo.gov.pl).

---

## 7. Dzieci

Aplikacja nie jest skierowana do dzieci poniżej 13. roku życia i nie zbiera świadomie żadnych danych
od dzieci. Jeśli jesteś rodzicem lub opiekunem i sądzisz, że Twoje dziecko podało nam dane,
skontaktuj się z nami — usuniemy je niezwłocznie.

---

## 8. Bezpieczeństwo

- Komunikacja z usługami zewnętrznymi (Open-Meteo, Anthropic) odbywa się wyłącznie po **HTTPS**.
- Lokalne dane są chronione izolacją procesów Androida (sandbox).
- Nie wymagamy od Ciebie tworzenia konta ani podawania hasła.

---

## 9. Zmiany w polityce

Możemy aktualizować tę politykę w przyszłości (np. po dodaniu nowych funkcji). Aktualna wersja
będzie zawsze dostępna pod tym samym adresem URL, z datą ostatniej aktualizacji w nagłówku. Istotne
zmiany ogłosimy w opisie aplikacji w Google Play.

---

## 10. Kontakt

W sprawach dotyczących prywatności pisz na: **weathervibe.support@gmail.com**.
