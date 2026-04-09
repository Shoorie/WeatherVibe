1. TimeProvider w feature:home — masz rację, to błąd ❌

TimeProvider jest internal w com.weather.vibe.feature.home.presentation — czyli nikt inny go nie zobaczy.
Gorzej: jest używany NIEKONSEKWENTNIE nawet w obrębie samego feature:home:

- HomeStateFactory.kt:124 → LocalDate.now() (formatDate)
- HomeStateFactory.kt:136 → LocalDate.now() (formatDayLabel — "dzisiaj")
- HomeViewModel.kt:106 → LocalTime.now().hour (computeWeatherKey)
- data/weather/.../DefaultWeatherSuggestionCache.kt:49 → System.currentTimeMillis()
- data/weather/mapper/WeatherMapper.kt:113 → System.currentTimeMillis()
- data/location/mapper/LocationMapper.kt:35 → System.currentTimeMillis()
- domain/weather/model/CachedWeatherSuggestion.kt:16 → System.currentTimeMillis()

Czyli wprowadziliśmy abstrakcję w jednym miejscu (SunriseSunsetStateFactory) i nie pociągnęliśmy jej
konsekwentnie. Cache TTL, weatherKey, "czy dzień to dzisiaj" — wszystko to wymaga testowalnego źródła
czasu.

Gdzie powinno być: nowy moduł core:time (albo core:common) z TimeProvider + RealTimeProvider. Wymagane
bindy: LocalDateTime, LocalDate, LocalTime, epoch millis. Albo jeden fun now(): Instant i konwersje po
wywołaniu.

  ---
2. weatherVibeTest w FeaturePlugin — zgoda, to przeregulowanie ❌

Obecnie FeaturePlugin.kt:27 wymusza apply(plugin = weatherVibeTest) dla każdego feature'a. Problemy:

1. feature:splash prawdopodobnie nie potrzebuje mockk/strikt/turbine — to jest pusty splash.
2. data:*, domain:*, core:* też potrzebują testów (to tam siedzi logika!), ale nie są feature'ami — z
   obecną konfiguracją musiałyby duplikować testImplementation ręcznie.
3. Gubimy opt-in: TestPlugin ma sens dokładnie dlatego, że można go aplikować selektywnie — w
   FeaturePlugin traci całą tę wartość.

Poprawnie: TestPlugin zostaje jako osobny convention plugin (weathervibe.android.test), każdy moduł który
chce testować, aplikuje go u siebie. Wywalić linię 27 z FeaturePlugin.

Dodatkowo, skoro już przy tym jesteśmy — TestPlugin dodaje 5 bibliotek hurtem. Może warto rozważyć
rozbicie na weathervibe.android.test (junit + strikt — minimum dla pure-function testów) oraz
weathervibe.android.test.coroutines (dorzuca turbine + coroutines-test) i weathervibe.android.test.mockk.
Ale to już kosmetyka, nie muszę teraz tego robić.

  ---
3. BaseTest w docs/ai-rules/testing.md — słuszna uwaga ❌

testing.md:228-239 pcha do rules konkretny helper class (BaseTest), plus zastrzeżenie "test classes that
use no mocks do not need to extend BaseTest". To projektuje pół-rozwiązanie na poziomie rules:

1. Rules powinien mówić zasadę, nie implementację. Właściwa zasada: "MockK state musi być czyszczony po
   każdym teście (np. @After unmockkAll())". Koniec. Bez dziedziczenia, bez helpera.
2. Dziedziczenie tylko po to, żeby wywołać jedną linijkę @After jest antywzorcem — kompozycja >
   dziedziczenie (jest Ci to bliskie, już to zapisałem w pamięci). Prosty @After fun tearDown() =
   unmockkAll() inline robi dokładnie to samo i jest trywialny.
3. Skill gen-unit-tests też zna BaseTest — przez co promuje projekt-specific wzorzec w "generycznym"
   skillu. Czyli skill nie jest generyczny.

Rekomendacja: wywalić sekcję 5 o BaseTest z testing.md. Zostawić jednolinijkową zasadę: "Classes that use
MockK must clear global state via unmockkAll() in @After". Skill gen-unit-tests nie zna BaseTest, po
prostu dodaje @After tearDown() gdy wykrywa mockk().

  ---
4. Pokrywanie się testing.md i gen-unit-tests/SKILL.md — zgoda ❌

Porównując docs/ai-rules/testing.md z .claude/skills/gen-unit-tests/SKILL.md — 70% treści się powtarza.
Sekcje 1, 2, 3, 4 testing.md + Quality gates = praktycznie to samo co Step 5, 6, 7 w skillu. To niedobry
stan, bo:

- Rules ≠ skill. Rules to co (normy projektu), skill to jak (procedura wykonania).
- Kopiowanie -> drift. Jedno się zmieni, drugie zostanie i kłamie.
- TimeProvider jako przykład w sekcji 6.3 testing.md ("Test Determinism") to właśnie ten rodzaj pomyłki:
  generyczna zasada dostaje projekt-specific instancjonal. Powinno być: "Never depend on wall-clock or
  random sources in assertions — inject them", koniec. Bez przykładu z konkretnym interfejsem.

Rekomendacja — podział ról:

Plik: testing.md (rules)
Rola: Co projekt uznaje za normę: nazewnictwo GWT, Strikt (nie JUnit), fixtury z named constants,
one-reason-to-fail, determinizm, opt-in TestPlugin
Czego NIE zawiera: Konkretne interfejsy (TimeProvider), konkretne klasy bazowe (BaseTest), step-by-step
procedura
────────────────────────────────────────
Plik: SKILL.md
Rola: Jak poprowadzić generator: krok po kroku — zidentyfikuj zależności, poszukaj fixtur, stwórz
brakujące, napisz testy, uruchom ./gradlew :module:testDebugUnitTest
Czego NIE zawiera: Powtarzanie zasad z rules — zamiast tego: "przeczytaj docs/ai-rules/testing.md zanim
zaczniesz" (to już zresztą jest w Step 2)

Sonnet zrobił tutaj wspólny hybrid — trzeba to rozdzielić.

  ---
5. Chude factories, gruba domena — tak, to jest duży refactor

Zgadzam się w 100%, ale najpierw najciekawsza obserwacja: docs/ai-rules/architecture.md sam siebie
kontredyktuje:

- linia 3: "Clean Architecture approach with Passive ViewModels and Fat Domains"
- sekcja 9, linia 197: *"StateFactory Pattern (Fat Factory)"* — "The factory is responsible for ALL data
  transformation from domain models to display-ready UI models"

Te dwa stwierdzenia się wykluczają. Jeżeli domena jest "fat", to factory nie może być "fat". Pierwsza
rzecz do zrobienia — rozstrzygnąć. Zakładam że chcesz Fat Domain + Thin Factory (mapper).

Co obecnie robią factories, a czego nie powinny

Przeanalizowałem wszystkie factories w feature:home:

SunriseSunsetStateFactory (najgorzej):

┌───────────────────────────────────────────────────┬─────────────────────────────────────────────────┐
│                      Obecnie                      │                Właściwa warstwa                 │
├───────────────────────────────────────────────────┼─────────────────────────────────────────────────┤
│ parsowanie "2026-04-08T12:00" → LocalDateTime     │ data mapper — String nie ma prawa opuścić       │
│ (runCatching)                                     │ warstwy data                                    │
├───────────────────────────────────────────────────┼─────────────────────────────────────────────────┤
│ calculateSunProgress(sunrise, sunset, now)        │ domain use case — CalculateSunProgress zwraca   │
│                                                   │ Float                                           │
├───────────────────────────────────────────────────┼─────────────────────────────────────────────────┤
│ formatDayLength(duration)                         │ domena oblicza Duration; factory formatuje      │
│                                                   │ string z resources                              │
├───────────────────────────────────────────────────┼─────────────────────────────────────────────────┤
│ formatSunTime                                     │ presentation (pure formatowanie)                │
└───────────────────────────────────────────────────┴─────────────────────────────────────────────────┘

HomeStateFactory:

┌─────────────────────────────────────────────┬───────────────────────────────────────────────────────┐
│                   Obecnie                   │                   Właściwa warstwa                    │
├─────────────────────────────────────────────┼───────────────────────────────────────────────────────┤
│                                             │ bug + logika domenowa — obecnie zawsze pierwszy       │
│ createHourlyForecast(hours).isCurrentHour = │ element jest "current", co jest fałszem, jeśli API    │
│  index == 0                                 │ zwraca np. od 00:00. Powinno być use case             │
│                                             │ FindCurrentHourIndex(hours, now)                      │
├─────────────────────────────────────────────┼───────────────────────────────────────────────────────┤
│ formatDayLabel(date).if (parsed ==          │ domain use case — IsToday(date, today) zwraca Boolean │
│ LocalDate.now())                            │                                                       │
├─────────────────────────────────────────────┼───────────────────────────────────────────────────────┤
│ runCatching { LocalDateTime.parse(time) }   │ data mapper                                           │
├─────────────────────────────────────────────┼───────────────────────────────────────────────────────┤
│                                             │ już hybrydowe — domena zwraca String z symbolem       │
│ convertTemperature(celsius, unit) zwraca    │ stopni. Czy to jest OK? Jeśli Fat Domain, to domena   │
│ String                                      │ zwraca Temperature(value: Double, unit) a factory     │
│                                             │ formatuje do stringa                                  │
└─────────────────────────────────────────────┴───────────────────────────────────────────────────────┘

MetricsStateFactory:

┌───────────────────────────────────┬─────────────────────────────────────────────────────────────────┐
│              Obecnie              │                        Właściwa warstwa                         │
├───────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
│ formatDirection(degrees) →        │ domain use case — WindDirection enum (N, NE, E...) +            │
│ "N"/"NE"/...                      │ ComputeWindDirection(degrees)                                   │
├───────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
│ konwersja m→km z progiem 1.0      │ presentation (format) — OK                                      │
├───────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
│ formatPercent, formatSpeed        │ presentation — OK                                               │
├───────────────────────────────────┼─────────────────────────────────────────────────────────────────┤
│ fallbacki ?: DEFAULT_WIND_SPEED   │ te defaulty powinny być w domain modelu                         │
└───────────────────────────────────┴─────────────────────────────────────────────────────────────────┘

PlaylistStateFactory:

┌───────────────────────────┬─────────────────────────────────────────────────────────────────────────┐
│          Obecnie          │                            Właściwa warstwa                             │
├───────────────────────────┼─────────────────────────────────────────────────────────────────────────┤
│ budowanie                 │ domain use case — BuildPlaylistQuery(suggestion) zwraca                 │
│ "spotify:search:rock      │ PlaylistQuery(spotify: String, ytMusic: String). To czyste URI building │
│ jazz"                     │  — logika, nie format                                                   │
├───────────────────────────┼─────────────────────────────────────────────────────────────────────────┤
│ trim gatunków, wybór      │ domena                                                                  │
│ pierwszego                │                                                                         │
└───────────────────────────┴─────────────────────────────────────────────────────────────────────────┘

Plan refactoru — szkielet kroków

Plan rozbiłbym na 6 faz, każda jako osobny PR. Kolejność ma znaczenie — domena najpierw, factories
ostatnie.

Faza 0 — fundament
- Utworzyć core:time z TimeProvider, RealTimeProvider. Bind now(): Instant + extension
  toLocalDateTime(zone) (testowalna strefa).
- Podłączyć w data:weather, data:location, feature:home (ViewModel computeWeatherKey, Factory
  formatDate/formatDayLabel).
- Rozwiązuje równocześnie punkt #1.

Faza 1 — domain models przestają używać String na dane czasowe
- DailyWeather.sunrise/sunset/date: String → Instant/LocalDate.
- Parsowanie stringów ląduje w data/weather/mapper/WeatherMapper. Serializacja też — jeśli potrzebny
  @Serializable, custom serializer.
- Efekt: SunriseSunsetStateFactory nie ma już parseDateTime.

Faza 2 — nowe use cases w domain:weather
- CalculateSunProgress(sunrise: Instant, sunset: Instant, now: Instant): Float
- CalculateDayLength(sunrise: Instant, sunset: Instant): Duration
- FindCurrentHourIndex(hours: List<HourlyWeather>, now: Instant): Int
- ComputeWindDirection(degrees: Double): WindDirection (enum)
- IsToday(date: LocalDate, today: LocalDate): Boolean
- BuildPlaylistQuery(suggestion: WeatherSuggestion): PlaylistQuery
- Każdy ma test jednostkowy (mały, <10 linii).

Faza 3 — StateFactories staja się mapperami
- SunriseSunsetStateFactory.create(progress: Float, duration: Duration, sunrise: Instant, sunset: Instant)
  — przyjmuje już obliczone wartości z use case'ów, tylko formatuje do stringa.
- HomeStateFactory — wywołuje use case'y w ViewModelu, factory dostaje gotowe dane.
- Alternatywnie: factory może trzymać referencje do use case'ów (obecne podejście). Wybrać konwencję — ja
  bym powiedział że factory dostaje już obliczone dane, a orkiestruje ViewModel. Czysty pasywny ViewModel
  już tego nie jest, ale factory staje się 100% pure-funkcyjne i triwialnie testowalne.

Faza 4 — rewrite ConvertTemperature
- Dziś zwraca String z ° — projekt wymaga podjęcia decyzji. Albo:
    - Domain zwraca Temperature(value: Double, unit: Unit), presentation formatuje.
    - Albo zostawiamy jak jest i akceptujemy "hybrydowy" use case (ale wtedy "Fat Domain" to kłamstwo).

Faza 5 — aktualizacja architecture.md
- Usuwamy sekcję 9 "Fat Factory". Wprowadzamy "Thin Factory / Dumb Mapper" pattern.
- Self-verification checklist: dodaj pytanie "Does the factory contain any conditional logic, parsing, or
  calculation? If yes → extract to use case."

Faza 6 — pozostałe feature'y
- feature:search, feature:settings — sprawdzić pod tym samym kątem, naprawić jeśli trzeba.

  ---
Co proponuję zrobić teraz

Cała ta lista to ~5–6 osobnych PR-ów. Żeby ruszyć, zasugeruję kolejność małych kroków:

1. Najpierw wyczyścić dług z obecnego PR-a (punkty 1–4):
   - wywalić weatherVibeTest z FeaturePlugin;
   - przenieść TimeProvider do core:time (nowy moduł);
   - przeciągnąć go konsekwentnie: HomeStateFactory.formatDate/formatDayLabel,
   HomeViewModel.computeWeatherKey, a potem cache'e w data;
   - rozdzielić testing.md (rules) od SKILL.md (procedura), wywalić BaseTest, wywalić przykład z
   TimeProviderem z rules, wywalić testImplementation helper który jest zduplikowany ze skillem.
2. Osobny PR — rozstrzygnąć Fat Domain vs Fat Factory w architecture.md. Zaktualizować rules na Fat
   Domain.
3. Dopiero potem — fazy 0–6 refactoru domenowego, jako seria PR-ów.
