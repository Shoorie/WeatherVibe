---
---

# Polityka prywatności WeatherVibe

**Data wejścia w życie:** 8 maja 2026 r.
**Ostatnia aktualizacja:** 9 maja 2026 r.

Niniejsza polityka prywatności opisuje, w jaki sposób aplikacja mobilna **WeatherVibe** ("
Aplikacja") obsługuje dane użytkowników. Twórca aplikacji ("my", "nasz") szanuje Twoją prywatność i
ogranicza przetwarzanie danych do minimum niezbędnego do działania funkcji pogodowych.

W skrócie: **nie posiadamy własnych serwerów, Twoje dane osobowe (imię, ulubione lokalizacje,
historia ocen nastroju) pozostają wyłącznie na Twoim urządzeniu i nie są przez nas sprzedawane.**

Aplikacja korzysta z **Google AdMob** do wyświetlania reklam, **Firebase Remote Config** do
zdalnego sterowania funkcjami oraz **Firebase Analytics** do mierzenia podstawowych statystyk
użycia. Te usługi działają w oparciu o zgodę zgodnie z RODO — pierwszą decyzję podejmiesz przy
pierwszym uruchomieniu aplikacji w UE/EOG, a w każdej chwili możesz ją zmienić.

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
- Lokalizacja **nie jest udostępniana** sieciom reklamowym ani narzędziom analitycznym. Trafia
  wyłącznie do dostawców pogody wymienionych w sekcji 3.

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

### 2.4. Dane techniczne i analityczne (Firebase Analytics)

Aplikacja korzysta z **Firebase Analytics** firmy Google w celu mierzenia podstawowych metryk użycia
(np. liczba uruchomień aplikacji, awarie, wersja systemu). Firebase Analytics zbiera m.in.:

- **identyfikator instancji aplikacji** (Firebase installation ID),
- **identyfikator reklamowy Android (AAID)** — jeżeli wyraziłeś(-aś) zgodę i nie zresetowałeś(-aś)
  go w ustawieniach systemowych,
- model i język urządzenia, wersję systemu Android, kraj (na podstawie adresu IP),
- zdarzenia systemowe (uruchomienie aplikacji, czas trwania sesji, awarie).

Dane są wysyłane do Google na adresy `app-measurement.com`, `*.google-analytics.com`. Nie
przekazujemy Firebase Analytics Twojej nazwy użytkownika, listy ulubionych lokalizacji, ocen
nastroju ani treści generowanych przez AI.

Możesz w każdej chwili zresetować lub usunąć identyfikator reklamowy w ustawieniach systemu Android
→ Prywatność → Reklamy.

### 2.5. Zarządzanie zgodą reklamową (UMP)

Jeżeli korzystasz z aplikacji w **Unii Europejskiej, Wielkiej Brytanii lub Szwajcarii**, przy
pierwszym uruchomieniu aplikacja wyświetla formularz zgody zarządzany przez **Google User Messaging
Platform (UMP)**. Formularz pozwala:

- wyrazić zgodę na spersonalizowane reklamy oraz powiązane przetwarzanie danych przez Google AdMob,
- ograniczyć się do **niespersonalizowanych reklam (NPA)**,
- odmówić zgody (wówczas reklamy nie zostaną wyświetlone).

Decyzję możesz zmienić w dowolnym momencie z poziomu ekranu **Profil → Ustawienia prywatności**.
Decyzja jest przechowywana lokalnie przez UMP SDK; dopóki istnieje, formularz nie pojawia się
ponownie.

---

## 3. Komu udostępniamy dane

Aplikacja korzysta z następujących usług zewnętrznych. Każda z nich otrzymuje wyłącznie minimalny
zakres danych niezbędny do realizacji swojej funkcji.

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

### 3.3. Google AdMob (reklamy)

- **Cel:** wyświetlanie banerów reklamowych na wybranych ekranach (Strona główna, Lista lokalizacji,
  Planer aktywności).
- **Wysyłane dane:** identyfikator reklamowy Android (AAID), przybliżona lokalizacja na podstawie
  adresu IP, dane techniczne urządzenia (model, system, język), kontekst aplikacji, status zgody
  zebrany przez UMP (zob. 2.5).
- **Zakres przetwarzania zależy od zgody:**
    - **Zgoda na reklamy spersonalizowane** — Google AdMob może profilować Cię na podstawie
      zachowań w innych aplikacjach i witrynach.
    - **Brak zgody (NPA)** — wyświetlane są wyłącznie reklamy niespersonalizowane na podstawie
      kontekstu aplikacji oraz przybliżonej lokalizacji.
    - **Odmowa zgody** — reklamy nie są wyświetlane.
- **Adresy:** `googleads.g.doubleclick.net`, `pagead2.googlesyndication.com`,
  `csi.gstatic.com`, `fundingchoicesmessages.google.com`.
- **Polityka prywatności dostawcy:** https://policies.google.com/privacy
- **Jak Google używa danych z aplikacji:** https://policies.google.com/technologies/partner-sites

### 3.4. Firebase (Google) — Remote Config oraz Analytics

- **Cel:**
    - **Remote Config** — zdalne włączanie/wyłączanie funkcji aplikacji (np. dostępność reklam na
      poszczególnych ekranach) bez konieczności wydawania nowej wersji.
    - **Analytics** — zob. sekcja 2.4.
- **Wysyłane dane:** identyfikator instancji Firebase, dane techniczne urządzenia, AAID (jeśli
  wyrażono zgodę), zdarzenia systemowe.
- **Adresy:** `firebaseremoteconfig.googleapis.com`, `firebaseinstallations.googleapis.com`,
  `app-measurement.com`, `*.google-analytics.com`.
- **Polityka prywatności dostawcy:** https://firebase.google.com/support/privacy

### 3.5. Pozostali odbiorcy

Poza usługami opisanymi w sekcjach 3.1–3.4 nie udostępniamy danych żadnym brokerom danych ani
serwisom społecznościowym.

---

## 4. Przechowywanie danych

- Dane wymienione w sekcji 2.3 są przechowywane **wyłącznie lokalnie** na Twoim urządzeniu.
- Nie posiadamy serwera bazodanowego ani konta w chmurze, na którym przechowywane byłyby Twoje dane
  osobowe.
- Identyfikatory techniczne wykorzystywane przez Firebase Analytics oraz AdMob są przetwarzane przez
  Google zgodnie z ich politykami prywatności.
- Odinstalowanie aplikacji powoduje **trwałe usunięcie** wszystkich danych z urządzenia, w tym
  identyfikatorów Firebase oraz lokalnych decyzji UMP.
- Możesz w każdej chwili wyczyścić dane aplikacji przez Ustawienia systemu Android → Aplikacje →
  WeatherVibe → Przechowywanie → Wyczyść dane.

---

## 5. Okres przechowywania

Lokalne dane (preferencje, historia ocen nastroju, ulubione lokalizacje, cache pogody) przechowywane
są **bezterminowo** lub do momentu, w którym sam(a) je usuniesz albo odinstalujesz aplikację.

Dane przetwarzane przez Google (AdMob, Firebase Analytics, Remote Config) są przechowywane zgodnie z
politykami Google — domyślnie do 14 miesięcy dla zdarzeń Analytics.

---

## 6. Twoje prawa (RODO)

Ponieważ wszystkie dane osobowe wprowadzone w aplikacji pozostają na Twoim urządzeniu, masz nad nimi
pełną kontrolę. Możesz:

- **przeglądać** je w odpowiednich ekranach aplikacji (profil, lokalizacje, historia vibe),
- **modyfikować** je w aplikacji,
- **usunąć** je przez wyczyszczenie danych aplikacji lub jej odinstalowanie,
- **zmienić zgodę reklamową** w panelu UMP (Profil → Ustawienia prywatności),
- **zresetować identyfikator reklamowy AAID** w ustawieniach systemu Android,
- **wyłączyć personalizację reklam Google** na poziomie konta Google: https://myadcenter.google.com.

W odniesieniu do danych przetwarzanych przez Google możesz również skorzystać z mechanizmów
przewidzianych przez Google (export, usunięcie) na stronie https://myaccount.google.com.

Jeśli masz pytania dotyczące przetwarzania danych lub chcesz skorzystać z innych praw wynikających z
RODO (m.in. prawo do informacji, sprzeciwu, ograniczenia przetwarzania), skontaktuj się z nami: *
*weathervibe.support@gmail.com**.

W przypadku, w którym uznasz, że Twoje prawa zostały naruszone, możesz złożyć skargę do organu
nadzorczego — w Polsce jest nim **Prezes Urzędu Ochrony Danych Osobowych** (https://uodo.gov.pl).

---

## 7. Dzieci

Aplikacja nie jest skierowana do dzieci poniżej 13. roku życia i nie zbiera świadomie żadnych danych
od dzieci. Reklamy AdMob są skonfigurowane z parametrem `tagForUnderAgeOfConsent = false`, co
oznacza, że nie kierujemy treści do osób, dla których obowiązują dodatkowe restrykcje wiekowe. Jeśli
jesteś rodzicem lub opiekunem i sądzisz, że Twoje dziecko korzystało z aplikacji, skontaktuj się z
nami — pomożemy usunąć powiązane identyfikatory.

---

## 8. Bezpieczeństwo

- Komunikacja z usługami zewnętrznymi (Open-Meteo, Anthropic, Google AdMob, Firebase) odbywa się
  wyłącznie po **HTTPS**.
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
