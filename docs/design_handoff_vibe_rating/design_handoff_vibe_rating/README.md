# Handoff: Vibe Rating & Twój Vibe

## Overview

Ficzer **Vibe Rating** pozwala użytkownikowi ocenić nastrój/jakość dnia w skali 1–5 z poziomu ekranu
głównego. Każda ocena jest zapisywana razem z **aktualnym snapshotem pogody** (temperatura, warunek,
wilgotność, wiatr, jakość powietrza), co z czasem buduje historię i umożliwia spersonalizowane
insighty ("Twoja ulubiona pogoda to słonecznie + 20°C").

Składa się z trzech części:

1. **Karta oceny dnia** na ekranie Home (3 warianty UI do wyboru — rekomendowany V3)
2. **Ekran "Twój vibe"** — kalendarz miesięczny z nawigacją prev/next, szczegóły dnia po kliknięciu,
   ulubiona pogoda, ranking warunków
3. **Wpięcie w ekran Profilu** — pierwsza sekcja pod awatarem, podświetlona kolorem akcentowym

---

## About the Design Files

Pliki w tym bundle to **referencje designowe stworzone w HTML/React** — prototypy pokazujące
zamierzony wygląd i zachowanie. To nie jest kod produkcyjny do bezpośredniego skopiowania.

Twoim zadaniem jest **odtworzyć te designy w Android Studio** używając istniejącej architektury
projektu. Dla nowego Androida oznacza to prawdopodobnie:

- **Jetpack Compose** (zalecane) — deklaratywne UI, dobrze mapuje się na strukturę React z
  prototypów
- **Material 3** jako bazę + customowe design tokeny wyciągnięte z tego README
- Architektura: **MVVM / MVI** z `ViewModel` + `StateFlow`/`State` + `Repository` do zapisu ocen

Jeśli projekt używa już innego wzorca (View system/XML, inny pattern architektoniczny) — trzymaj się
go, traktując pliki HTML tylko jako wizualną referencję.

---

## Fidelity

**High-fidelity (hifi).** Kolory, typografia, spacing, border radius i interakcje są finalne.
Odtworzyć 1:1 używając bibliotek Androida (Compose/Material 3). Placeholdery emoji (😊, 👍, ⛅) warto
zastąpić własnymi ikonami wektorowymi lub assetami z projektu jeśli są dostępne.

---

## Screens / Views

### 1. Home — karta oceny dnia

**Cel:** user widzi hero pogodowy + AI-generowany blurb nastroju dnia + kartę "Jak oceniasz ten
dzień?". Po ocenie karta zmienia stan na podsumowanie z akcjami (Udostępnij poster, Zobacz Twój
vibe).

**Struktura (od góry):**

```
ScrollView (pionowy, paddingBottom = 120dp dla bottom nav)
├── Hero Card                    (margin horizontal 16dp, top 60dp od status bara)
├── "Dzisiejszy Vibe" blurb      (margin top 18dp)
├── Rating Card — wariant        (margin top 18dp)
└── (reszta zawartości ekranu Home)
```

#### Hero Card

- Full width minus 32dp (16dp side margins)
- Padding: 18dp horizontal, 18dp top, 16dp bottom
- Border radius: **22dp**
- Background: linear gradient 160° — `#4F46E5` → `#6366F1` (60%) → `#818CF8`
- Text color: biały
- Zawartość:
    - Nazwa miasta (Manrope 600, 26sp, letterSpacing -0.4sp)
    - Data w formacie "piątek, 24 kwietnia" (Manrope 400, 12sp, alpha 0.85)
    - Temperatura (Manrope 200, 64sp, letterSpacing -2.5sp)
    - "Odczuwalna 15°" (12sp, alpha 0.85)
    - Opis warunku "Częściowe zachmurzenie" (Manrope 600, 14sp)
    - Glyph pogody po prawej (56sp emoji lub ikona wektorowa)

#### Vibe Blurb Card

- Border radius **20dp**, background `#EEF2FF` (indigo-50)
- Padding: 16dp
- Label nad kartą "DZISIEJSZY VIBE" (Manrope 500, 11sp, uppercase, letterSpacing 1.4sp, color
  `#64748B`, margin bottom 10dp)
- Zawartość:
    - Avatar 44dp (circle, background `#FCD34D`/żółty, emoji 😊 centered 22sp)
    - Tytuł "75/100 · Przyjemny klimat" (Manrope 600, 15sp, color `#0F172A`)
    - Subtytuł "Spoko pogódka." (Manrope 400, 13sp, color `#475569`)
    - Chipy statusowe (margin top 10dp):
        - "Niezłe powietrze" — background `#FEF3C7`, text `#78350F`, orange dot 8dp
        - "🌿 Bardzo wysokie pyłki" — background `#DCFCE7`, text `#14532D`
        - Padding chipa: 5dp vertical, 10dp horizontal, border radius 999dp (pill), Manrope 500 12sp

#### Rating Card — WARIANT V3 (rekomendowany)

**Label nad kartą:** "TWÓJ VIBE · DZIŚ" (styl jak wyżej)

**Karta:**

- Background białe `#FFFFFF`, border radius **20dp**, padding 18dp
- Border 1dp `rgba(15,23,42,.04)`, shadow bardzo subtelny (`0 1dp 2dp rgba(15,23,42,.04)`)

**Stan 1 — nieocenione:**

1. Nagłówek:
    - "Jak oceniasz ten dzień?" (Manrope 600, 15sp, `#0F172A`)
    - "Pomóż nam uczyć się Twoich preferencji." (Manrope 400, 12sp, `#64748B`)
2. Row: MoodFace po lewej (36dp) + label po prawej ("OK", `#94A3B8` dopóki nie tknięty slidera,
   potem kolor rating)
3. Slider 1–5:
    - Track: wysokość 10dp, border radius 999dp
    - Background track: linear gradient poziomy `#CBD5E1 → #94A3B8 → #818CF8 → #6366F1 → #4F46E5`
    - Tick marks: 5 białych kropek 3dp na pozycjach 0/25/50/75/100%
    - Thumb: 24dp circle, białe wypełnienie, border 3dp (kolor rating lub indigo), shadow + aureola
      indigo 4dp alpha 0.08
    - Container: 14dp top/bottom padding, 13dp left/right padding (żeby thumb nie wystawał)
4. Labels pod slidem (5 słów): "Słaby · Taki sobie · OK · Dobry · Super" (Manrope 500, 10sp,
   `#94A3B8`); aktywny label → bold + kolor rating
5. Primary button "Zapisz":
    - Full width, height ~44dp, border radius 12dp
    - Disabled (nieruszony): bg `#E2E8F0`, text `#94A3B8`
    - Enabled: bg `#4F46E5`, text `#FFFFFF`, Manrope 600 13sp
6. Secondary link "Zobacz ostatnie oceny →" (Manrope 500, 12sp, `#4F46E5`, text button)

**Stan 2 — po ocenie:**

1. Summary row:
    - Background: `linear-gradient(135deg, {ratingColor}18, {ratingColor}06)`, border
      `{ratingColor}33` 1dp, radius 14dp, padding 10/12dp
    - MoodFace 38dp (active=true) + label "Ocenione / Dobry · 4/5" (Manrope 700, 15sp, kolor rating)
    - Przycisk "Zmień ocenę" po prawej (text button, 11sp, `#64748B`)
2. Action row — dwa przyciski side by side, gap 8dp, margin top 10dp:
    - **Zobacz Twój vibe** (primary, flex: 1) — bg `#4F46E5`, text biały, ikonka kalendarza 14dp
    - **Udostępnij poster** (secondary) — bg `#FFFFFF`, text `#4F46E5`, border 1dp indigo-25, ikona
      upload
    - Oba: radius 12dp, padding 11dp vertical, Manrope 600 13sp

### 2. Ekran "Twój vibe"

**Cel:** historia ocen w formie kalendarza, eksploracja dni + agregaty pogodowe.

**Struktura:**

```
ScrollView (paddingBottom = 110dp)
├── Header                            (padding 68dp top, 22dp horizontal, 18dp bottom)
│   ├── Back button + tytuł
│   └── Summary stats grid (2 kolumny)
├── Kalendarz + Day detail            (padding 22dp horizontal, 20dp bottom)
├── Karta "Twoja ulubiona pogoda"     (padding 22dp horizontal)
└── Ranking warunków (Top Conditions)
```

#### Header

- Back button (circle 36dp, chevron left 20dp, `#0F172A`)
- Label "Jak oceniałeś ostatnie dni" (styl label)
- Tytuł "Twój vibe" (Manrope 700, 26sp, letterSpacing -0.4sp, `#0F172A`)

#### Summary stats (grid 2 kolumny, gap 8dp)

- Karta 1 — Średnia: wartość (Manrope 300, 28sp, kolor rating) + "/5" (12sp subdued) + label "twoja
  średnia"
- Karta 2 — Liczba dni: wartość (Manrope 300, 28sp, `#0F172A`) + label "ocenionych dni"
- Każda karta: bg biały, radius 14dp, padding 14dp, subtelna shadow

#### Kalendarz miesięczny

**Header nawigacji (margin bottom 10dp, padding 4dp horizontal):**

- Button prev 32dp circle, bg `#F1F5F9`, chevron left
- Middle — "Kwiecień 2026" (Manrope 600, 15sp, letterSpacing -0.2sp)
- Button next 32dp circle — disabled kiedy view === bieżący miesiąc (bg `#F8FAFC`, chevron
  `#CBD5E1`)

**Grid:**

- Card biały, radius 18dp, padding 14dp, subtelna shadow
- Weekday labels row (Pn/Wt/Śr/Cz/Pt/So/Nd, 500 10sp, uppercase, `#94A3B8`) — 7 kolumn, gap 4dp
- Cells grid: 7 kolumn × 6 rzędów, gap 4dp, aspect ratio 1:1
- Cell states:
    - **Pusta** (przed początkiem miesiąca / po): nic (przezroczyste)
    - **Przyszłość** (disabled): bg `#F8FAFC`, text `#CBD5E1`, cursor not-allowed
    - **Brak oceny**: bg `#F1F5F9`, text `#94A3B8`, weight 500
    - **Oceniony**: bg = `ratingColor(rating)`, text biały, weight 700
    - **Dziś**: border 2dp `#4F46E5` (niezależnie od innych stanów)
    - **Wybrany** (po kliknięciu): border 2dp `#0F172A`
- Radius cell: 10dp

**Rating color scale:**

- 1 → `#CBD5E1`
- 2 → `#94A3B8`
- 3 → `#818CF8`
- 4 → `#6366F1`
- 5 → `#4F46E5`

**Legend pod kalendarzem:**

- "słaby" → 5 kwadratów 14dp z kolorami rating → "super" (Manrope 500, 10sp, `#64748B`)

#### Day Detail (pokazuje się po kliknięciu celki)

- Margin top 12dp, padding 16dp, radius 18dp
- Background: jeśli oceniony — `linear-gradient(135°, {ratingColor}22, {ratingColor}08)`, border
  `{ratingColor}55`; w przeciwnym razie białe
- Animacja wejścia: fade + translateY(4dp) 200ms easeOut
- Zawartość:
    - Row top: data "środa, 22 kwietnia" (11sp subdued) + title warunku "Częściowe zachmurzenie ·
      18°" (Manrope 600, 16sp) + close "×" button
    - Row bottom (margin top 12dp): weather glyph 40dp + MoodFace 40dp + opis oceny ("Dobry / 4/5 ·
      zapisane automatycznie")

#### Karta "Twoja ulubiona pogoda"

- Padding 18dp, radius 20dp
- Background `linear-gradient(160°, #FEF3C7, #FED7AA)` (amber → orange soft)
- Label "TWOJA ULUBIONA POGODA" (styl label, color `#78350F`)
- Ikona 56dp (rounded 16dp, bg `rgba(255,255,255,.5)`) + glyph sunny 36dp
- "20°C, słonecznie" (Manrope 700, 20sp, `#78350F`)
- "**4.3/5** · 47 ocen" (Manrope 500, 12sp, `#9A3412`)

#### Ranking warunków ("Średnia ocena wg pogody")

- Lista; każdy wiersz:
    - Weather glyph 28dp
    - Kolumna flex: nazwa ("Słonecznie", 600 13sp) + "18 dni" (500 11sp subdued) po prawej; progress
      bar 6dp (bg `#F1F5F9`) z wypełnieniem kolorem rating
    - Wartość "4.3" po prawej (Manrope 600, 15sp, kolor rating)
- Karta: bg biały, radius 18dp, padding 16dp, gap między wierszami 14dp

### 3. Ekran Profilu (z wpięciem "Twój vibe")

**Cel:** pokazać gdzie użytkownik znajduje wejście do Twój vibe.

**Struktura:**

```
ScrollView
├── Tytuł "Profil"                      (padding 68dp top, 22dp horizontal)
├── Avatar card                         (margin top 18dp, 16dp horizontal)
├── Sekcja TWÓJ VIBE  ← HIGHLIGHTED
├── Sekcja KONTO
├── Sekcja PREFERENCJE
├── Sekcja APLIKACJA
├── Button "Wyloguj"
└── Wersja appki (footer)
```

**Wzór sekcji:**

- Label uppercase (22dp padding horizontal, Manrope 500, 10sp, letterSpacing 1.4sp, `#94A3B8`,
  margin bottom 8dp)
- Card: margin 16dp horizontal, bg biały, radius 16dp, subtelna shadow, overflow hidden
- Każdy row w karcie: full width button, padding 14/16dp, border-bottom `1dp #F1F5F9` (oprócz
  ostatniego)
    - Ikona 36dp (rounded 10dp) — `#F8FAFC` bg / `#64748B` fg, dla highlighted wiersza `#EEF2FF` /
      `#4F46E5`
    - Label (Manrope 500 14sp) + opcjonalny value (400 12sp, `#94A3B8`)
    - Chevron right 16dp, `#CBD5E1`

**Sekcja TWÓJ VIBE (HIGHLIGHTED):**

- Label "TWÓJ VIBE"
- Jeden row: ikona kalendarza 18dp, label **"Twój vibe"** (600 14sp, `#4F46E5`), value "Historia,
  kalendarz i ulubiona pogoda"

---

## Interactions & Behavior

### Rating Card V3

- Slider domyślnie na 3, thumb szary dopóki user nie ruszy
- Po dotknięciu (`touched = true`) → thumb + label zmieniają kolor na rating; button "Zapisz" staje
  się aktywny
- Kliknięcie "Zapisz" → zapis do bazy (ocena + snapshot pogody, patrz Data Model) + przełączenie
  karty w stan 2 (po ocenie)
- "Zmień ocenę" w stanie 2 → reset do stanu 1
- "Zobacz Twój vibe" → nav do Fragment/Destination `VibeHistoryScreen`
- "Udostępnij poster" → generowanie posteru (zakres osobny, nie objęty tym handoffem)

### Kalendarz

- Prev/Next month — nawigacja widoku, next jest disabled kiedy user na bieżącym miesiącu (nie
  pozwalamy iść w przyszłość)
- Kliknięcie w celkę oceniona/pusta ALE nie z przyszłości → pokazuje Day Detail card pod
  kalendarzem; stan `selectedKey`
- Animacja wejścia Detail card — fade + translateY 200ms
- Kliknięcie "×" w Detail card → `selectedKey = null`, karta znika

### Tranzycje

- Żadnych fancy animacji — wszędzie 150-250ms ease-out na transform/opacity/background-color
- Respect `prefers-reduced-motion` — w Compose użyj `LocalDensity` + disable przez
  `AnimationSpec.Snap` jeśli ustawione

---

## State Management

### Rating Card state (per-day)

```kotlin
data class DailyRatingState(
  val date: LocalDate,                 // dziś
  val rating: Int? = null,             // 1..5, null = nieocenione
  val note: String = "",               // opcjonalna krótka notka
  val isEditing: Boolean = false,      // stan 1 vs 2
  val sliderDraft: Int = 3,            // podgląd przed zapisem (V3)
  val sliderTouched: Boolean = false,  // czy user ruszył slider
)
```

### VibeHistoryScreen state

```kotlin
data class VibeHistoryState(
  val viewMonth: YearMonth,            // który miesiąc pokazujemy
  val entries: Map<LocalDate, RatingEntry>,  // załadowane z bazy
  val selectedDay: LocalDate? = null,  // kliknięta celka
  val stats: VibeStats,                // avg, condAvg[], total, favWeather
)
```

### ViewModel flow

```kotlin
class VibeViewModel(private val repo: VibeRepository, private val weatherRepo: WeatherRepository) :
  ViewModel() {
  val homeState: StateFlow<DailyRatingState>
  val historyState: StateFlow<VibeHistoryState>

  fun onRatingSave(rating: Int, note: String) = viewModelScope.launch {
    // 1. Pobierz aktualny snapshot pogody (już w cache'u WeatherRepo dla Home)
    val snapshot = weatherRepo.currentSnapshot()
    // 2. Zapisz atomowo ocena + snapshot
    repo.saveRating(
      RatingEntry(
        date = LocalDate.now(),
        rating = rating,
        note = note,
        weather = snapshot,  // <-- zamrożone zdjęcie pogody!
      )
    )
  }

  fun onMonthChange(direction: Int) {
    ...
  }
  fun onDaySelected(date: LocalDate) {
    ...
  }
}
```

---

## Data Model — KRYTYCZNY

**Kluczowa zasada:** ocena jest zapisywana razem z **kopią pogody z momentu zapisu**. Nigdy nie
pobieramy pogody historycznej z API.

```kotlin
data class RatingEntry(
  val id: Long = 0,                    // PK, autoincrement
  val date: LocalDate,                 // YYYY-MM-DD (unique — max 1 ocena/dzień)
  val rating: Int,                     // 1..5
  val note: String = "",               // max 80 znaków
  val weather: WeatherSnapshot,        // zamrożone w chwili zapisu
  val createdAt: Instant,              // dokładny timestamp
)

data class WeatherSnapshot(
  val tempC: Double,                   // np. 18.0
  val feelsLikeC: Double,              // np. 15.0
  val condition: Condition,            // enum: SUNNY, PARTLY, CLOUDY, RAIN, SNOW, NIGHT
  val humidity: Int,                   // %
  val windKph: Double,
  val pressureHpa: Int,
  val airQuality: AirQuality?,         // opcjonalne, jeśli API zwraca
  val pollen: PollenLevel?,            // low/med/high/veryhigh
)

enum class Condition { SUNNY, PARTLY, CLOUDY, RAIN, SNOW, NIGHT }
```

### Room schema

```kotlin
@Entity(tableName = "rating_entries", indices = [Index(value = ["date"], unique = true)])
data class RatingEntryEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val date: LocalDate,
  val rating: Int,
  val note: String,
  @Embedded(prefix = "w_") val weather: WeatherSnapshotEmbedded,
  val createdAt: Instant,
)
```

### FAQ

**"A jeśli user oceni dzień wieczorem?"** — snapshot z momentu kliknięcia "Zapisz". MVP = prostota.
Docelowo można liczyć średnią dzienną z tego co pobraliśmy przez dzień.

**"Co ze starymi dniami sprzed ficzera?"** — **forward-only**. Nie pokazujemy historycznej pogody,
puste dni zostają szare "brak oceny".

**"Unikalność?"** — max 1 ocena / data. Kolejna ocena w tym samym dniu = UPDATE (upsert).

---

## Aggregations (Twoje insighty)

Liczone w pamięci przy ładowaniu ekranu "Twój vibe" z pełnej listy `RatingEntry`:

```kotlin
fun computeStats(entries: List<RatingEntry>): VibeStats {
  val avg = entries.map { it.rating }.average()
  val byCond = entries.groupBy { it.weather.condition }
    .mapValues { (_, list) ->
      ConditionStat(
        avg = list.map { it.rating }.average(),
        count = list.size,
      )
    }
    .toList()
    .sortedByDescending { it.second.avg }
  val favWeather = byCond.firstOrNull()?.first
  return VibeStats(avg = avg, condAvg = byCond, favWeather = favWeather, total = entries.size)
}
```

Dla małych N (< 100 ocen) nie warto optymalizować — policz to w ViewModel za każdym razem.

---

## Design Tokens

Stwórz `ui/theme/VibeTheme.kt` lub wpnij w istniejący `MaterialTheme`:

### Kolory

```kotlin
object VibeColors {
  // Brand
  val Indigo600 = Color(0xFF4F46E5)
  val Indigo500 = Color(0xFF6366F1)
  val Indigo400 = Color(0xFF818CF8)
  val Indigo50 = Color(0xFFEEF2FF)
  val IndigoInk = Color(0xFF1E1B4B)

  // Neutrals (slate)
  val Slate900 = Color(0xFF0F172A)  // ink primary
  val Slate700 = Color(0xFF334155)
  val Slate600 = Color(0xFF475569)  // ink secondary
  val Slate500 = Color(0xFF64748B)  // ink tertiary / subdued
  val Slate400 = Color(0xFF94A3B8)
  val Slate300 = Color(0xFFCBD5E1)
  val Slate200 = Color(0xFFE2E8F0)
  val Slate100 = Color(0xFFF1F5F9)
  val Slate50 = Color(0xFFF8FAFC)
  val Bg = Color(0xFFFAFBFF)  // app background

  // Semantic
  val Amber100 = Color(0xFFFEF3C7)
  val Amber900 = Color(0xFF78350F)
  val Orange300 = Color(0xFFFED7AA)
  val Green100 = Color(0xFFDCFCE7)
  val Green900 = Color(0xFF14532D)
  val Red600 = Color(0xFFDC2626)

  // Rating scale (1..5)
  val Rating1 = Slate300
  val Rating2 = Slate400
  val Rating3 = Indigo400
  val Rating4 = Indigo500
  val Rating5 = Indigo600
}

fun ratingColor(rating: Int): Color = when (rating.coerceIn(1, 5)) {
  1 -> VibeColors.Rating1
  2 -> VibeColors.Rating2
  3 -> VibeColors.Rating3
  4 -> VibeColors.Rating4
  else -> VibeColors.Rating5
}
```

### Typografia (Manrope)

Pobierz font (Google Fonts) i dodaj do `res/font/`:

```kotlin
val Manrope = FontFamily(
  Font(R.font.manrope_200, FontWeight.ExtraLight),
  Font(R.font.manrope_400, FontWeight.Normal),
  Font(R.font.manrope_500, FontWeight.Medium),
  Font(R.font.manrope_600, FontWeight.SemiBold),
  Font(R.font.manrope_700, FontWeight.Bold),
)

object VibeType {
  val DisplayLarge =
    TextStyle(Manrope, FontWeight.ExtraLight, 64.sp, 64.sp, letterSpacing = (-2.5).sp)
  val DisplayMedium = TextStyle(Manrope, FontWeight.Light, 28.sp, 30.sp, letterSpacing = (-0.5).sp)
  val Headline = TextStyle(Manrope, FontWeight.Bold, 26.sp, 30.sp, letterSpacing = (-0.4).sp)
  val TitleLarge = TextStyle(Manrope, FontWeight.SemiBold, 20.sp, 24.sp, letterSpacing = (-0.3).sp)
  val TitleMedium = TextStyle(Manrope, FontWeight.SemiBold, 16.sp, 22.sp)
  val BodyLarge = TextStyle(Manrope, FontWeight.SemiBold, 15.sp, 20.sp)
  val BodyMedium = TextStyle(Manrope, FontWeight.Normal, 13.sp, 18.sp)
  val BodySmall = TextStyle(Manrope, FontWeight.Normal, 12.sp, 16.sp)
  val Label =
    TextStyle(Manrope, FontWeight.Medium, 11.sp, 14.sp, letterSpacing = 1.4.sp)  // uppercase
  val Button = TextStyle(Manrope, FontWeight.SemiBold, 13.sp, 16.sp)
  val Caption = TextStyle(Manrope, FontWeight.Normal, 11.sp, 14.sp)
}
```

### Spacing

```kotlin
object VibeSpacing {
  val xs = 4.dp
  val s = 8.dp
  val m = 12.dp
  val l = 16.dp
  val xl = 18.dp
  val xxl = 22.dp
  val xxxl = 32.dp
}
```

### Border radius

```kotlin
object VibeRadius {
  val Chip = 999.dp       // pill
  val Cell = 10.dp        // calendar cells
  val Small = 12.dp       // buttons
  val Medium = 14.dp      // mini cards
  val Large = 18.dp       // cards
  val XLarge = 20.dp      // major cards
  val Hero = 22.dp        // hero
}
```

### Shadow

```kotlin
// Subtle (większość kart)
Modifier.shadow(elevation = 1.dp, shape = RoundedCornerShape(...), ambientColor = Color(0x0F0F172A))

// Tweaks panel / popover
elevation = 20.dp
```

---

## Assets

- **Ikony**: używaj Material Icons (Compose Material Icons Extended) lub własnych wektorów XML/SVG
- **Pogoda**: emoji (⛅, ☀️, 🌧️) jako placeholder; docelowo zastąp animowanymi wektorami lub assetami
- **Font Manrope**: Google Fonts — https://fonts.google.com/specimen/Manrope

---

## Compose Implementation Hints

### Slider (V3) w Compose

Nie używaj `Slider` z Material 3 — dostosowanie gradientowego tracka + kropek jest bardziej robione
ręcznie. Użyj `Canvas` lub layered `Box` + `pointerInput { detectDragGestures }` + własny thumb jako
`Box`.

### Kalendarz

`LazyVerticalGrid(columns = GridCells.Fixed(7))` lub zwykły `Column` + 6× `Row` (7 celek) — prostsze
dla stałej siatki. Oblicz `YearMonth.atDay(1).dayOfWeek.value` dla offsetu startowego (
Poniedziałek-first → użyj `(dayOfWeek.value - 1) % 7`).

### Animacje

- Day Detail entry:
  `AnimatedVisibility(enter = fadeIn() + slideInVertically(initialOffsetY = { it / 10 }))`
- Cell selection: `animateColorAsState` na border/background

### Dark mode

Nie był projektowany — ale paleta slate/indigo dobrze mapuje się na dark theme. Przed implementacją
zapytaj PM-a czy potrzebny dark mode w tej iteracji.

---

## Files

Referencyjne pliki HTML w tym handoffie:

- **Vibe Rating.html** — główny prototyp (wszystkie 3 warianty + kalendarz + profil + diagram "jak
  to działa")
- **vibe-rating-variants.jsx** — 3 warianty Rating Card (V1 Thumbs / V2 Faces / V3 Slider —
  REKOMENDOWANY)
- **vibe-history-screen.jsx** — kalendarz + Day Detail + ulubiona pogoda + ranking warunków
- **primitives.jsx** — dzielone: `SectionLabel`, `TempRange`, (w places-variants.jsx) `Glyph` dla
  pogody
- **shell.jsx** — `PhoneShell`, `BottomNav` — te chromy są tylko do prezentacji, w Androidzie
  używasz natywnego `NavigationBar`/`Scaffold`

Otwórz `Vibe Rating.html` w przeglądarce żeby zobaczyć interakcje na żywo.
