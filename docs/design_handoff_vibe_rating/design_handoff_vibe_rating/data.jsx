// Shared data + i18n + condition presets for WeatherVibe prototype

const i18n = {
  pl: {
    // nav
    home: 'Dom', places: 'Miejsca', profile: 'Profil',
    // home
    feelsLike: 'Odczuwalna', search: 'Szukaj', settings: 'Ustawienia',
    todaysVibe: "DZISIEJSZY VIBE", hourly: 'PROGNOZA GODZINOWA', daily: 'PROGNOZA 7-DNIOWA',
    now: 'Teraz', today: 'Dzisiaj',
    details: 'SZCZEGÓŁY POGODY', viewAllDetails: 'Zobacz wszystkie szczegóły',
    discoverSounds: 'Odkryj dzisiejsze brzmienia',
    planActivity: 'Zaplanuj aktywność',
    planActivitySub: 'Najlepsze okna dla biegania, spaceru i rowerowania',
    sharePoster: 'Udostępnij poster',
    alert: 'UWAGA', pollenAlert: 'Wysokie stężenie pyłków brzozy',
    pollenAlertSub: 'Alergicy — weźcie leki przed wyjściem',
    // vibe
    vibeScore: 'Indeks nastroju', niceVibe: 'Przyjemny klimat',
    // detail tiles
    humidity: 'Wilgotność', windSpeed: 'Prędkość wiatru', uv: 'Indeks UV',
    precipitation: 'Opady', pressure: 'Ciśnienie', visibility: 'Widoczność',
    dewPoint: 'Punkt rosy', cloudCover: 'Zachmurzenie',
    sunrise: 'Wschód słońca', sunset: 'Zachód słońca', dayLength: 'Długość dnia',
    sunSection: 'Słońce', windSection: 'Wiatr', atmosphere: 'Atmosfera', conditions: 'Warunki',
    // places
    placesTitle: 'Moje miejsca',
    placesSub: 'Trzy miasta, trzy różne vibe\'y',
    addPlace: 'Dodaj miasto', compareAll: 'Porównaj',
    livingHere: 'Tu mieszkasz',
    // profile
    profile_hello: 'Cześć', daysWith: 'dni z WeatherVibe',
    quote: 'Pogoda to muzyka, którą słyszy świat. Słuchaj uważnie.',
    locations: 'Lokalizacje', morningBrief: 'Poranny brief', alerts: 'Alerty', on: 'Wł.', off: 'Wył.',
    moodJournal: 'Dziennik nastroju', soon: 'Wkrótce',
    moodJournalSub: 'Zapisuj swój nastrój każdego dnia i zobacz, jak pogoda wpływa na Twoje samopoczucie.',
    personalization: 'Personalizacja', personalizationSub: 'Ton briefu, jednostki, gatunki',
    notifications: 'Powiadomienia', notificationsSub: 'Poranny brief i alerty',
    privacy: 'Polityka prywatności', privacySub: 'Jak dbamy o Twoje dane',
    briefTone: 'Ton briefu',
    // poster
    poster_advice: 'Co założyć', poster_share: 'Udostępnij',
    // planner
    planner: 'Planer aktywności',
    running: 'Bieganie', walk: 'Spacer', cycling: 'Rower',
    bestMoment: 'Najlepszy moment dnia', timeline24: 'Oś czasu 24h',
    great: 'Świetne', good: 'Dobre', ok: 'Przeciętne', bad: 'Słabe',
    temperature: 'Temperatura', wind: 'Wiatr',
    cold: 'Chłodno', low: 'Niski', calm: 'Bezwietrznie',
    // search
    searchCity: 'Szukaj miasta…',
    recentSearches: 'Ostatnio wyszukiwane',
    recentSub: 'Wróć do miejsca, które już sprawdzałeś',
    // conditions
    cond_sunny: 'Słonecznie', cond_cloudy: 'Pochmurno', cond_partly: 'Częściowe zachmurzenie',
    cond_rain: 'Deszcz', cond_snow: 'Śnieg', cond_night: 'Pogodna noc',
    cond_overcast: 'Zachmurzenie całkowite',
    // Vibe copy per condition
    vibe_partly: 'Przyjemny klimat', vibe_rain: 'Cosy i kontemplacyjny',
    vibe_sunny: 'Promienny i lekki', vibe_cold: 'Mroźny i czysty',
    // dress
    dress: 'Co założyć',
    briefTitle: 'BRIEF POGODOWY',
    // quote and phrases
    greetings: ['Dzień dobry', 'Cześć', 'Hej'],
  },
  en: {
    home: 'Home', places: 'Places', profile: 'Profile',
    feelsLike: 'Feels like', search: 'Search', settings: 'Settings',
    todaysVibe: "TODAY'S VIBE", hourly: 'HOURLY FORECAST', daily: '7-DAY FORECAST',
    now: 'Now', today: 'Today',
    details: 'WEATHER DETAILS', viewAllDetails: 'View all details',
    discoverSounds: "Discover today's sounds",
    planActivity: 'Plan your day',
    planActivitySub: 'Best windows for running, walks, and cycling',
    sharePoster: 'Share poster',
    alert: 'HEADS UP', pollenAlert: 'High birch pollen',
    pollenAlertSub: 'Allergy folks — take meds before heading out',
    vibeScore: 'Vibe index', niceVibe: 'Pleasant vibe',
    humidity: 'Humidity', windSpeed: 'Wind speed', uv: 'UV index',
    precipitation: 'Precipitation', pressure: 'Pressure', visibility: 'Visibility',
    dewPoint: 'Dew point', cloudCover: 'Cloud cover',
    sunrise: 'Sunrise', sunset: 'Sunset', dayLength: 'Day length',
    sunSection: 'Sun', windSection: 'Wind', atmosphere: 'Atmosphere', conditions: 'Conditions',
    placesTitle: 'Your places',
    placesSub: 'Three cities, three different moods',
    addPlace: 'Add city', compareAll: 'Compare',
    livingHere: 'You live here',
    profile_hello: 'Hello', daysWith: 'days with WeatherVibe',
    quote: "Weather is the world's soundtrack. Listen closely.",
    locations: 'Locations', morningBrief: 'Morning brief', alerts: 'Alerts', on: 'On', off: 'Off',
    moodJournal: 'Mood journal', soon: 'Soon',
    moodJournalSub: 'Log your mood daily and see how weather shapes how you feel.',
    personalization: 'Personalization', personalizationSub: 'Brief tone, units, genres',
    notifications: 'Notifications', notificationsSub: 'Morning brief & alerts',
    privacy: 'Privacy policy', privacySub: 'How we care for your data',
    briefTone: 'Brief tone',
    poster_advice: 'What to wear', poster_share: 'Share',
    planner: 'Activity planner',
    running: 'Running', walk: 'Walk', cycling: 'Cycling',
    bestMoment: 'Best moment today', timeline24: '24h timeline',
    great: 'Great', good: 'Good', ok: 'Okay', bad: 'Poor',
    temperature: 'Temperature', wind: 'Wind',
    cold: 'Cool', low: 'Low', calm: 'Calm',
    searchCity: 'Search city…',
    recentSearches: 'Recent searches',
    recentSub: 'Jump back to a place you\'ve checked',
    cond_sunny: 'Sunny', cond_cloudy: 'Cloudy', cond_partly: 'Partly cloudy',
    cond_rain: 'Rain', cond_snow: 'Snow', cond_night: 'Clear night',
    cond_overcast: 'Overcast',
    vibe_partly: 'Pleasant vibe', vibe_rain: 'Cosy & contemplative',
    vibe_sunny: 'Radiant & light', vibe_cold: 'Crisp & frozen',
    dress: 'What to wear',
    briefTitle: 'WEATHER BRIEF',
    greetings: ['Good morning', 'Hello', 'Hey'],
  }
};

// Condition presets used across directions.
const CONDITIONS = {
  partly: {
    key: 'partly', emoji: '⛅',
    tempC: 12, feelsC: 9, hiC: 18, loC: 2,
    skyLight: ['#E0E7FF', '#F0F3FF', '#FAFBFF'],
    skyDark:  ['#1E1B4B', '#2D2A6B', '#4F46E5'],
    skyEditorial: ['#F5E6D3', '#E8D5B7', '#F7F0E5'],
    accentHex: '#4F46E5',
    dress_pl: 'Sweter, jeans, lekka kurtka',
    dress_en: 'Sweater, jeans, light jacket',
    vibe_pl: 'Spoko pogódka, dzień taki do wszystkiego.',
    vibe_en: 'Solid day — good for anything really.',
    brief_pl: 'Wieczór przyniósł nam pochmurne niebo i chłodnawe powietrze — idealna pora, żeby przytulić się w domu z czymś ciepłym do picia i dobrą książką.',
    brief_en: 'Evening brought an overcast sky and crisp cool air — perfect moment to settle in with something warm and a good book.',
  },
  sunny: {
    key: 'sunny', emoji: '☀️',
    tempC: 21, feelsC: 22, hiC: 24, loC: 12,
    skyLight: ['#FEF3C7', '#FFE4B5', '#FDF6E3'],
    skyDark:  ['#7C2D12', '#EA580C', '#FCD34D'],
    skyEditorial: ['#FFF5E1', '#FFD896', '#FFE8BA'],
    accentHex: '#D97706',
    dress_pl: 'Lekka kurtka, długie spodnie, sneakersy',
    dress_en: 'Light jacket, long pants, sneakers',
    vibe_pl: 'Promienny dzień — wyciąg balkon, zostań na zewnątrz.',
    vibe_en: 'Radiant day — grab a patio, stay outside.',
    brief_pl: 'Słońce świeci pełną gębą, a powietrze jest ciepłe i suche — idealny moment, żeby wyjść z czterech ścian i poczuć, że wiosna naprawdę jest.',
    brief_en: 'Sun is out in full force, air warm and dry — time to escape the walls and feel that spring is actually here.',
  },
  rain: {
    key: 'rain', emoji: '🌧️',
    tempC: 9, feelsC: 6, hiC: 12, loC: 5,
    skyLight: ['#CBD5E1', '#E2E8F0', '#F1F5F9'],
    skyDark:  ['#0F172A', '#1E293B', '#334155'],
    skyEditorial: ['#E5E7EB', '#D1D5DB', '#F3F4F6'],
    accentHex: '#0284C7',
    dress_pl: 'Trencz, parasol, kalosze',
    dress_en: 'Trench, umbrella, rubber boots',
    vibe_pl: 'Cosy i kontemplacyjny — herbata i playlista lo-fi.',
    vibe_en: 'Cosy & contemplative — tea and lo-fi.',
    brief_pl: 'Miękki deszcz pada bez wytchnienia od rana. Miasto pachnie asfaltem i pierwszą kawą. Idealne tło do zwolnienia tempa.',
    brief_en: 'Soft rain has been falling since morning. The city smells of asphalt and first coffees. A perfect backdrop to slow down.',
  },
  snow: {
    key: 'snow', emoji: '❄️',
    tempC: -3, feelsC: -7, hiC: 0, loC: -9,
    skyLight: ['#DBEAFE', '#EFF6FF', '#F8FAFC'],
    skyDark:  ['#1E3A8A', '#3730A3', '#60A5FA'],
    skyEditorial: ['#EAF3FC', '#D6E7F8', '#F4F9FE'],
    accentHex: '#0284C7',
    dress_pl: 'Gruba kurtka, szalik, rękawiczki',
    dress_en: 'Heavy coat, scarf, gloves',
    vibe_pl: 'Mroźny i czysty — idealny moment na hot choc.',
    vibe_en: 'Crisp & frozen — hot choc weather.',
    brief_pl: 'Śnieg pada powoli, cicho i bez pośpiechu. Miasto wygląda jak pocztówka — otul się szalikiem i wyjdź tylko na chwilę.',
    brief_en: 'Snow falling slow, quiet, unhurried. The city looks like a postcard — scarf up and head out, briefly.',
  },
  night: {
    key: 'night', emoji: '🌙',
    tempC: 7, feelsC: 5, hiC: 11, loC: 6,
    skyLight: ['#1E1B4B', '#312E81', '#4338CA'],
    skyDark:  ['#020617', '#0F172A', '#1E293B'],
    skyEditorial: ['#1A1A2E', '#16213E', '#0F3460'],
    accentHex: '#818CF8',
    dress_pl: 'Ciepła bluza, dżinsy, adidasy',
    dress_en: 'Warm hoodie, jeans, sneakers',
    vibe_pl: 'Cicha, gwiaździsta noc — spacer po mieście byłby idealny.',
    vibe_en: 'Quiet starry night — a city walk would be perfect.',
    brief_pl: 'Niebo oczyściło się po zachodzie słońca. W powietrzu chłodek, a ulice prawie puste — miasto należy do spacerowiczów.',
    brief_en: 'Sky cleared after sunset. Cool in the air, streets nearly empty — the city belongs to the walkers tonight.',
  },
  cloudy: {
    key: 'cloudy', emoji: '☁️',
    tempC: 10, feelsC: 8, hiC: 14, loC: 4,
    skyLight: ['#E5E7EB', '#F1F2F4', '#F8FAFC'],
    skyDark:  ['#374151', '#4B5563', '#6B7280'],
    skyEditorial: ['#E6E4DE', '#D7D3C8', '#F0EDE4'],
    accentHex: '#64748B',
    dress_pl: 'Sweter, cienka kurtka, buty zamknięte',
    dress_en: 'Sweater, light jacket, closed shoes',
    vibe_pl: 'Szare, miękkie, filmowe — dobry dzień na kino i cafe.',
    vibe_en: 'Grey, soft, cinematic — a good day for film and café.',
    brief_pl: 'Chmury ciągną się nisko i równo od rana. Światło jest miękkie, rozproszone, fotograficznie piękne — idealne na spacer z aparatem lub kawiarnię z dużym oknem.',
    brief_en: 'Clouds hang low and even from morning. Light is soft, diffused, photograph-pretty — perfect for a camera walk or a café with big windows.',
  },
};

const CITIES = [
  { id: 'torun',   name_pl: 'Toruń',     name_en: 'Toruń',     region_pl: 'kujawsko-pomorskie',    region_en: 'Kuyavian-Pomeranian, Poland', cond: 'partly', tempC: 12, hiC: 18, loC: 2,  home: true, tz: '+01:00', localTime: '21:15', aqi: 42,  wind: 9,  humidity: 68 },
  { id: 'palermo', name_pl: 'Palermo',   name_en: 'Palermo',   region_pl: 'Sycylia, Włochy',        region_en: 'Sicily, Italy',               cond: 'sunny',  tempC: 21, hiC: 24, loC: 15, tz: '+01:00', localTime: '21:15', aqi: 28,  wind: 14, humidity: 55 },
  { id: 'reyk',    name_pl: 'Reykjavik', name_en: 'Reykjavik', region_pl: 'Stolica, Islandia',      region_en: 'Capital, Iceland',            cond: 'snow',   tempC: -3, hiC: 0,  loC: -9, tz: '+00:00', localTime: '20:15', aqi: 12,  wind: 26, humidity: 82 },
  { id: 'tokyo',   name_pl: 'Tokio',     name_en: 'Tokyo',     region_pl: 'Kantō, Japonia',         region_en: 'Kantō, Japan',                cond: 'rain',   tempC: 9,  hiC: 12, loC: 5,  tz: '+09:00', localTime: '05:15', aqi: 58,  wind: 11, humidity: 91 },
  { id: 'krakow',  name_pl: 'Kraków',    name_en: 'Kraków',    region_pl: 'małopolskie',            region_en: 'Lesser Poland',               cond: 'cloudy', tempC: 11, hiC: 15, loC: 4,  tz: '+01:00', localTime: '21:15', aqi: 74,  wind: 7,  humidity: 72 },
  { id: 'lisbon',  name_pl: 'Lizbona',   name_en: 'Lisbon',    region_pl: 'Portugalia',             region_en: 'Portugal',                    cond: 'partly', tempC: 18, hiC: 21, loC: 13, tz: '+00:00', localTime: '20:15', aqi: 35,  wind: 18, humidity: 63 },
  { id: 'berlin',  name_pl: 'Berlin',    name_en: 'Berlin',    region_pl: 'Niemcy',                 region_en: 'Germany',                     cond: 'rain',   tempC: 8,  hiC: 11, loC: 3,  tz: '+01:00', localTime: '21:15', aqi: 40,  wind: 15, humidity: 85 },
  { id: 'nyc',     name_pl: 'Nowy Jork', name_en: 'New York',  region_pl: 'USA',                    region_en: 'USA',                         cond: 'night',  tempC: 7,  hiC: 11, loC: 6,  tz: '-04:00', localTime: '15:15', aqi: 51,  wind: 12, humidity: 58 },
  { id: 'bali',    name_pl: 'Bali',      name_en: 'Bali',      region_pl: 'Indonezja',              region_en: 'Indonesia',                   cond: 'sunny',  tempC: 29, hiC: 31, loC: 25, tz: '+08:00', localTime: '04:15', aqi: 22,  wind: 10, humidity: 78 },
  { id: 'oslo',    name_pl: 'Oslo',      name_en: 'Oslo',      region_pl: 'Norwegia',               region_en: 'Norway',                      cond: 'cloudy', tempC: 4,  hiC: 7,  loC: -1, tz: '+01:00', localTime: '21:15', aqi: 18,  wind: 8,  humidity: 76 },
];

const HOURLY = [
  { t: 'now', tempC: 13, cond: 'partly' },
  { t: '22:00', tempC: 11, cond: 'cloudy' },
  { t: '23:00', tempC: 10, cond: 'cloudy' },
  { t: '00:00', tempC: 9,  cond: 'cloudy' },
  { t: '01:00', tempC: 8,  cond: 'partly' },
  { t: '02:00', tempC: 8,  cond: 'partly' },
  { t: '03:00', tempC: 7,  cond: 'cloudy' },
  { t: '04:00', tempC: 7,  cond: 'cloudy' },
];

const DAILY = [
  { d_pl: 'Dzisiaj', d_en: 'Today', cond: 'partly', lo: 2,  hi: 18 },
  { d_pl: 'czw.',    d_en: 'Thu',   cond: 'cloudy', lo: 5,  hi: 15 },
  { d_pl: 'pt.',     d_en: 'Fri',   cond: 'cloudy', lo: 7,  hi: 17 },
  { d_pl: 'sob.',    d_en: 'Sat',   cond: 'rain',   lo: 7,  hi: 16 },
  { d_pl: 'niedz.',  d_en: 'Sun',   cond: 'rain',   lo: 3,  hi: 12 },
  { d_pl: 'pon.',    d_en: 'Mon',   cond: 'cloudy', lo: 0,  hi: 15 },
  { d_pl: 'wt.',     d_en: 'Tue',   cond: 'cloudy', lo: 0,  hi: 14 },
];

const CONDITION_EMOJI = {
  partly: '⛅', cloudy: '☁️', rain: '🌧️', snow: '❄️', sunny: '☀️', night: '🌙', overcast: '☁️',
};

// Temperature-range bar helper: given lo/hi and global min/max over the week, return left% and width%.
function rangePct(lo, hi, minT = -10, maxT = 30) {
  const span = maxT - minT;
  return { left: ((lo - minT) / span) * 100, width: ((hi - lo) / span) * 100 };
}

// Color interp from cool (#0284C7) → warm (#D97706) across a temp band.
function tempColor(t, minT = -10, maxT = 30) {
  const ratio = Math.max(0, Math.min(1, (t - minT) / (maxT - minT)));
  // blend 0284C7 → 64748B (mid slate) → D97706
  const lerp = (a,b,k) => Math.round(a + (b-a)*k);
  const c1 = [2,132,199];
  const c2 = [100,116,139];
  const c3 = [217,119,6];
  let r,g,b;
  if (ratio < 0.5) {
    const k = ratio * 2;
    r = lerp(c1[0], c2[0], k); g = lerp(c1[1], c2[1], k); b = lerp(c1[2], c2[2], k);
  } else {
    const k = (ratio - 0.5) * 2;
    r = lerp(c2[0], c3[0], k); g = lerp(c2[1], c3[1], k); b = lerp(c2[2], c3[2], k);
  }
  return `rgb(${r},${g},${b})`;
}

Object.assign(window, { i18n, CONDITIONS, CITIES, HOURLY, DAILY, CONDITION_EMOJI, rangePct, tempColor });
