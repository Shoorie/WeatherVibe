// vibe-rating-variants.jsx
// Karta "Jak oceniasz dzień?" na Home + Profile z pozycjami "Twój vibe" i share poster.
//
// Zmiana zakresu: ocena dotyczy dnia (nie AI-vibe'u). Krótka notka opcjonalnie.
// Po ocenie: CTA do ekranu "Twój vibe" + "Udostępnij poster".
// Trzy warianty różnicą skali/granularności ratingu (thumbs / faces / slider).

const { useState, useEffect, useMemo, useRef } = React;

// ═══════════════════════════════════════════════════════════════════
// COPY
// ═══════════════════════════════════════════════════════════════════
const VIBE_COPY = {
  pl: {
    // Hero pogodowy (referencja)
    sectionToday: "DZISIEJSZY VIBE",
    vibeTitle: "75/100 · Przyjemny klimat",
    vibeSub: "Spoko pogódka.",
    noAir: "Niezłe powietrze", pollen: "Bardzo wysokie pyłki",

    // Rating card
    rateTitle: "Jak oceniasz ten dzień?",
    rateSub: "Pomóż nam uczyć się Twoich preferencji.",
    addNote: "Dodaj krótką notkę", noteHint: "max 80 znaków",
    notePlaceholder: "Dzień na kawę w parku…",
    save: "Zapisz", cancel: "Anuluj",

    // After rated
    rated: "Oceniłeś dziś na",
    ratedShort: "Ocenione",
    editRating: "Zmień ocenę",
    sharePoster: "Udostępnij poster",
    seeYourVibe: "Zobacz Twój vibe",

    // "Not rated yet" small CTA to history for context
    exploreHistory: "Zobacz ostatnie oceny",

    // Ekran historii / Twój vibe
    historyTitle: "Twój vibe",
    historySub: "Jak oceniałeś ostatnie dni",
    calendarNav: "Kalendarz",
    favWeather: "Twoja ulubiona pogoda",
    avgByCond: "Średnia ocena wg pogody",
    ratingScale: ["Słaby", "Taki sobie", "OK", "Dobry", "Super"],
    legendLow: "słaby", legendHigh: "super",
    monthNames: ["Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień"],
    weekdaysShort: ["Pn","Wt","Śr","Cz","Pt","So","Nd"],

    // Profile
    profileTitle: "Profil",
    profileGreeting: "Cześć, Klaudiusz",
    profileSub: "Twoje konto i preferencje",
    profileSectionVibe: "TWÓJ VIBE",
    profileYourVibe: "Twój vibe",
    profileYourVibeSub: "Historia, kalendarz i ulubiona pogoda",
    profileSectionAcc: "KONTO",
    profileEmail: "Email", profileSub2: "Subskrypcja",
    profilePlan: "WeatherVibe Free",
    profileSectionPref: "PREFERENCJE",
    profileUnits: "Jednostki", profileUnitsVal: "Metryczne (°C, km/h)",
    profileNotif: "Powiadomienia", profileNotifVal: "Codzienny brief 7:30",
    profileLang: "Język", profileLangVal: "Polski",
    profileSectionApp: "APLIKACJA",
    profileHelp: "Pomoc i kontakt",
    profilePrivacy: "Prywatność",
    profileAbout: "O WeatherVibe",
    profileLogout: "Wyloguj",
    profileVersion: "WeatherVibe v1.0.2",
  },
  en: {
    sectionToday: "TODAY'S VIBE",
    vibeTitle: "75/100 · Pleasant",
    vibeSub: "Solid day.",
    noAir: "Clean-ish air", pollen: "Very high pollen",

    rateTitle: "How do you rate today?",
    rateSub: "Help us learn your preferences.",
    addNote: "Add a short note", noteHint: "max 80 chars",
    notePlaceholder: "Coffee in the park kind of day…",
    save: "Save", cancel: "Cancel",

    rated: "You rated today",
    ratedShort: "Rated",
    editRating: "Edit rating",
    sharePoster: "Share poster",
    seeYourVibe: "See your vibe",

    exploreHistory: "See recent ratings",

    historyTitle: "Your vibe",
    historySub: "How you rated recent days",
    calendarNav: "Calendar",
    favWeather: "Your favourite weather",
    avgByCond: "Avg rating by condition",
    ratingScale: ["Poor", "Meh", "OK", "Good", "Great"],
    legendLow: "poor", legendHigh: "great",
    monthNames: ["January","February","March","April","May","June","July","August","September","October","November","December"],
    weekdaysShort: ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"],

    profileTitle: "Profile",
    profileGreeting: "Hi, Klaudiusz",
    profileSub: "Your account and preferences",
    profileSectionVibe: "YOUR VIBE",
    profileYourVibe: "Your vibe",
    profileYourVibeSub: "History, calendar & favourite weather",
    profileSectionAcc: "ACCOUNT",
    profileEmail: "Email", profileSub2: "Subscription",
    profilePlan: "WeatherVibe Free",
    profileSectionPref: "PREFERENCES",
    profileUnits: "Units", profileUnitsVal: "Metric (°C, km/h)",
    profileNotif: "Notifications", profileNotifVal: "Daily brief 7:30am",
    profileLang: "Language", profileLangVal: "English",
    profileSectionApp: "APP",
    profileHelp: "Help & contact",
    profilePrivacy: "Privacy",
    profileAbout: "About WeatherVibe",
    profileLogout: "Log out",
    profileVersion: "WeatherVibe v1.0.2",
  }
};

// Rating color (1..5 → indigo gradient)
function ratingColor(r) {
  if (r === null || r === undefined) return '#E2E8F0';
  const colors = ['#CBD5E1', '#94A3B8', '#818CF8', '#6366F1', '#4F46E5'];
  return colors[Math.max(0, Math.min(4, Math.round(r) - 1))];
}

// Mood face SVG (1..5)
function MoodFace({ value, size = 40, active = false }) {
  const s = size;
  const fillBg = active ? ratingColor(value) : '#F1F5F9';
  const fillFg = active ? '#fff' : '#64748B';
  const eyeY = 13;
  const mouths = {
    1: `M 10 22 Q 16 18 22 22`,
    2: `M 10 20 Q 16 19 22 20`,
    3: `M 10 20 L 22 20`,
    4: `M 10 20 Q 16 23 22 20`,
    5: `M 9 19 Q 16 26 23 19`,
  };
  const eyes = value >= 4 ? (
    <>
      <path d={`M 10 ${eyeY-1} Q 11.5 ${eyeY-3} 13 ${eyeY-1}`} stroke={fillFg} strokeWidth="1.6" fill="none" strokeLinecap="round"/>
      <path d={`M 19 ${eyeY-1} Q 20.5 ${eyeY-3} 22 ${eyeY-1}`} stroke={fillFg} strokeWidth="1.6" fill="none" strokeLinecap="round"/>
    </>
  ) : (
    <>
      <circle cx="11.5" cy={eyeY} r="1.4" fill={fillFg}/>
      <circle cx="20.5" cy={eyeY} r="1.4" fill={fillFg}/>
    </>
  );
  return (
    <svg width={s} height={s} viewBox="0 0 32 32">
      <circle cx="16" cy="16" r="15" fill={fillBg}/>
      {eyes}
      <path d={mouths[value] || mouths[3]} stroke={fillFg} strokeWidth="2" fill="none" strokeLinecap="round"/>
    </svg>
  );
}

// Section label primitive
function Lbl({ children, color, style }) {
  return (
    <div style={{
      font: '500 11px/14px Manrope', letterSpacing: '1.4px',
      textTransform: 'uppercase', color: color || '#64748B', ...style,
    }}>{children}</div>
  );
}

// ═══════════════════════════════════════════════════════════════════
// VIBE CARD — stały pogodowy blurb na górze (nie-zmienny)
// ═══════════════════════════════════════════════════════════════════
function VibeBlurb({ lang }) {
  const T = VIBE_COPY[lang];
  return (
    <div>
      <Lbl>{T.sectionToday}</Lbl>
      <div style={{
        marginTop: 10, padding: '16px 16px',
        background: '#EEF2FF', borderRadius: 20,
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <div style={{
            width: 44, height: 44, borderRadius: '50%', background: '#FCD34D',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
            fontSize: 22, flexShrink: 0,
          }}>😊</div>
          <div style={{ flex: 1, minWidth: 0 }}>
            <div style={{ font: '600 15px/20px Manrope', color: '#0F172A' }}>{T.vibeTitle}</div>
            <div style={{ font: '400 13px/18px Manrope', color: '#475569', marginTop: 2 }}>{T.vibeSub}</div>
          </div>
        </div>
        <div style={{ display: 'flex', gap: 8, marginTop: 10, flexWrap: 'wrap' }}>
          <span style={{
            display: 'inline-flex', alignItems: 'center', gap: 6,
            padding: '5px 10px', borderRadius: 999, background: '#FEF3C7',
            font: '500 12px/16px Manrope', color: '#78350F',
          }}>
            <span style={{ width: 8, height: 8, borderRadius: '50%', background: '#F59E0B' }}/>
            {T.noAir}
          </span>
          <span style={{
            display: 'inline-flex', alignItems: 'center', gap: 6,
            padding: '5px 10px', borderRadius: 999, background: '#DCFCE7',
            font: '500 12px/16px Manrope', color: '#14532D',
          }}>🌿 {T.pollen}</span>
        </div>
      </div>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════
// SHARED — post-rating strip: ocena + share poster + CTA to history
// ═══════════════════════════════════════════════════════════════════
function RatedActions({ lang, rating, onEdit, onShare, onSeeHistory }) {
  const T = VIBE_COPY[lang];
  return (
    <div style={{ animation: 'wvFade .25s ease-out' }}>
      {/* summary row */}
      <div style={{
        display: 'flex', alignItems: 'center', gap: 12,
        padding: '10px 12px', borderRadius: 14,
        background: `linear-gradient(135deg, ${ratingColor(rating)}18 0%, ${ratingColor(rating)}06 100%)`,
        border: `1px solid ${ratingColor(rating)}33`,
      }}>
        <MoodFace value={rating} size={38} active={true}/>
        <div style={{ flex: 1, minWidth: 0 }}>
          <div style={{ font: '500 11px/14px Manrope', color: '#64748B', letterSpacing: '.2px' }}>
            {T.ratedShort}
          </div>
          <div style={{
            font: '700 15px/20px Manrope', color: ratingColor(rating),
          }}>
            {T.ratingScale[rating - 1]} · {rating}/5
          </div>
        </div>
        <button onClick={onEdit} style={{
          padding: '6px 10px', borderRadius: 8, border: 0, cursor: 'pointer',
          background: 'rgba(255,255,255,.7)', color: '#64748B',
          font: '500 11px/14px Manrope',
        }}>{T.editRating}</button>
      </div>

      {/* action row */}
      <div style={{ display: 'flex', gap: 8, marginTop: 10 }}>
        <button onClick={onSeeHistory} style={{
          flex: 1, padding: '11px 12px', borderRadius: 12, border: 0, cursor: 'pointer',
          background: '#4F46E5', color: '#fff',
          font: '600 13px/16px Manrope',
          display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 8,
        }}>
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
            <rect x="3" y="4" width="18" height="18" rx="3"/>
            <path d="M3 10 h18 M8 2 v4 M16 2 v4"/>
          </svg>
          {T.seeYourVibe}
        </button>
        <button onClick={onShare} style={{
          padding: '11px 12px', borderRadius: 12, border: 0, cursor: 'pointer',
          background: '#fff', color: '#4F46E5',
          font: '600 13px/16px Manrope',
          display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 8,
          boxShadow: '0 0 0 1px rgba(79,70,229,.25)',
        }}>
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
            <path d="M4 12 v7 a2 2 0 0 0 2 2 h12 a2 2 0 0 0 2 -2 v-7"/>
            <path d="M16 6 L12 2 L8 6"/>
            <path d="M12 2 v14"/>
          </svg>
          {T.sharePoster}
        </button>
      </div>
    </div>
  );
}

// Shared un-rated wrapper — label + subtitle
function RateHeader({ lang }) {
  const T = VIBE_COPY[lang];
  return (
    <div style={{ marginBottom: 12 }}>
      <div style={{
        font: '600 15px/20px Manrope', color: '#0F172A',
      }}>{T.rateTitle}</div>
      <div style={{
        font: '400 12px/16px Manrope', color: '#64748B', marginTop: 2,
      }}>{T.rateSub}</div>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════
// RATING CARD — zewnętrzna karta, "niezależna" od vibe blurba
// ═══════════════════════════════════════════════════════════════════
function RatingCardShell({ children }) {
  return (
    <div style={{
      padding: 18, borderRadius: 20, background: '#fff',
      boxShadow: '0 1px 2px rgba(15,23,42,.04)',
      border: '1px solid rgba(15,23,42,.04)',
    }}>
      {children}
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════
// WARIANT 1 — Thumbs 👍/🤷/👎 → sync to rating 5/3/1
// ═══════════════════════════════════════════════════════════════════
function V1_Thumbs({ lang = 'pl', initialRating = null, onSeeHistory }) {
  const T = VIBE_COPY[lang];
  const [rating, setRating] = useState(initialRating);
  const [note, setNote] = useState('');
  const [showNote, setShowNote] = useState(false);

  const Btn = ({ v, icon, label, color }) => (
    <button onClick={() => setRating(v)} style={{
      flex: 1, padding: '12px 6px', borderRadius: 14, border: 0, cursor: 'pointer',
      background: '#F8FAFC', color: '#475569',
      font: '600 12px/16px Manrope',
      display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 6,
      border: '1px solid #EEF2FF',
      transition: 'all .18s',
    }}
    onMouseOver={(e) => { e.currentTarget.style.background = color + '12'; e.currentTarget.style.borderColor = color + '55'; }}
    onMouseOut={(e) => { e.currentTarget.style.background = '#F8FAFC'; e.currentTarget.style.borderColor = '#EEF2FF'; }}>
      <span style={{ fontSize: 24 }}>{icon}</span>
      <span>{label}</span>
    </button>
  );

  return (
    <div>
      <Lbl>{T.historyTitle.toUpperCase()} · {lang === 'pl' ? 'DZIŚ' : 'TODAY'}</Lbl>
      <div style={{ marginTop: 10 }}>
        <RatingCardShell>
          {rating === null ? (
            <>
              <RateHeader lang={lang}/>
              <div style={{ display: 'flex', gap: 8 }}>
                <Btn v={1} icon="👎" label={lang === 'pl' ? 'Słaby' : 'Bad'} color="#94A3B8"/>
                <Btn v={3} icon="🤷" label={lang === 'pl' ? 'OK' : 'Meh'} color="#64748B"/>
                <Btn v={5} icon="👍" label={lang === 'pl' ? 'Dobry' : 'Good'} color="#4F46E5"/>
              </div>
              <button onClick={onSeeHistory} style={{
                width: '100%', marginTop: 12, padding: '10px', borderRadius: 10, border: 0,
                background: 'transparent', color: '#4F46E5',
                font: '500 12px/16px Manrope', cursor: 'pointer',
                display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 6,
              }}>
                {T.exploreHistory}
                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
                  <path d="M5 12 h14 M13 6 l6 6 -6 6"/>
                </svg>
              </button>
            </>
          ) : (
            <RatedActions lang={lang} rating={rating}
              onEdit={() => setRating(null)}
              onShare={() => alert(T.sharePoster)}
              onSeeHistory={onSeeHistory}/>
          )}
        </RatingCardShell>
      </div>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════
// WARIANT 2 — Faces 5 (granularny)
// ═══════════════════════════════════════════════════════════════════
function V2_Faces({ lang = 'pl', initialRating = null, onSeeHistory }) {
  const T = VIBE_COPY[lang];
  const [rating, setRating] = useState(initialRating);

  return (
    <div>
      <Lbl>{T.historyTitle.toUpperCase()} · {lang === 'pl' ? 'DZIŚ' : 'TODAY'}</Lbl>
      <div style={{ marginTop: 10 }}>
        <RatingCardShell>
          {rating === null ? (
            <>
              <RateHeader lang={lang}/>
              <div style={{ display: 'flex', justifyContent: 'space-between', gap: 6 }}>
                {[1,2,3,4,5].map(v => (
                  <button key={v} onClick={() => setRating(v)} style={{
                    flex: 1, padding: 6, borderRadius: 14, border: 0, background: 'transparent',
                    cursor: 'pointer',
                    display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 4,
                    transition: 'transform .15s',
                  }}
                  onMouseOver={(e) => e.currentTarget.style.transform = 'scale(1.08)'}
                  onMouseOut={(e) => e.currentTarget.style.transform = 'scale(1)'}>
                    <MoodFace value={v} size={44} active={false}/>
                    <span style={{ font: '500 10px/14px Manrope', color: '#94A3B8' }}>
                      {T.ratingScale[v-1]}
                    </span>
                  </button>
                ))}
              </div>
              <button onClick={onSeeHistory} style={{
                width: '100%', marginTop: 10, padding: '10px', borderRadius: 10, border: 0,
                background: 'transparent', color: '#4F46E5',
                font: '500 12px/16px Manrope', cursor: 'pointer',
                display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 6,
              }}>
                {T.exploreHistory}
                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
                  <path d="M5 12 h14 M13 6 l6 6 -6 6"/>
                </svg>
              </button>
            </>
          ) : (
            <RatedActions lang={lang} rating={rating}
              onEdit={() => setRating(null)}
              onShare={() => alert(T.sharePoster)}
              onSeeHistory={onSeeHistory}/>
          )}
        </RatingCardShell>
      </div>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════
// WARIANT 3 — Slider 1-5
// ═══════════════════════════════════════════════════════════════════
function V3_SliderContext({ lang = 'pl', initialRating = null, onSeeHistory }) {
  const T = VIBE_COPY[lang];
  const [rating, setRating] = useState(initialRating);
  const [draft, setDraft] = useState(3);
  const [touched, setTouched] = useState(false);
  const label = T.ratingScale[Math.round(draft) - 1];
  const color = ratingColor(draft);

  return (
    <div>
      <Lbl>{T.historyTitle.toUpperCase()} · {lang === 'pl' ? 'DZIŚ' : 'TODAY'}</Lbl>
      <div style={{ marginTop: 10 }}>
        <RatingCardShell>
          {rating === null ? (
            <>
              <RateHeader lang={lang}/>

              <div style={{
                display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10,
              }}>
                <MoodFace value={Math.round(draft)} size={36} active={touched}/>
                <div style={{ textAlign: 'right' }}>
                  <div style={{ font: `600 14px/18px Manrope`, color: touched ? color : '#94A3B8' }}>{label}</div>
                  <div style={{ font: `400 10px/14px Manrope`, color: '#94A3B8' }}>{Math.round(draft)}/5</div>
                </div>
              </div>

              {/* Track: 13px horizontal padding reserves space for half-thumb (11px) + safety,
                  so thumb at 0% / 100% sits flush inside the card without clipping. */}
              <div style={{ position: 'relative', padding: '14px 13px' }}>
                <div style={{
                  position: 'relative', height: 10, borderRadius: 999,
                  background: 'linear-gradient(90deg, #CBD5E1, #94A3B8, #818CF8, #6366F1, #4F46E5)',
                }}>
                  {/* tick marks on the track */}
                  {[0, 25, 50, 75, 100].map((pct, i) => (
                    <div key={i} style={{
                      position: 'absolute', top: '50%', left: `${pct}%`,
                      width: 3, height: 3, borderRadius: '50%',
                      background: 'rgba(255,255,255,.7)',
                      transform: 'translate(-50%, -50%)',
                    }}/>
                  ))}
                  {/* thumb — centered by translate(-50%) so 0% aligns with left track edge */}
                  <div style={{
                    position: 'absolute', top: '50%',
                    left: `${((draft - 1) / 4) * 100}%`,
                    width: 24, height: 24, borderRadius: '50%',
                    background: '#fff', border: `3px solid ${touched ? color : '#4F46E5'}`,
                    transform: 'translate(-50%, -50%)',
                    boxShadow: '0 2px 8px rgba(15,23,42,.18), 0 0 0 4px rgba(79,70,229,.08)',
                    pointerEvents: 'none',
                    transition: 'border-color .2s, left .15s ease-out',
                  }}/>
                </div>
                <input
                  type="range" min="1" max="5" step="1" value={draft}
                  onChange={(e) => { setDraft(+e.target.value); setTouched(true); }}
                  style={{
                    position: 'absolute', inset: 0, width: '100%', height: '100%',
                    margin: 0, opacity: 0, cursor: 'pointer',
                  }}/>
              </div>
              {/* tick labels under the track, aligned via same padding */}
              <div style={{
                display: 'flex', justifyContent: 'space-between',
                padding: '0 4px', marginTop: 2,
                font: '500 10px/14px Manrope', color: '#94A3B8',
              }}>
                {T.ratingScale.map((s, i) => (
                  <span key={s} style={{
                    color: touched && Math.round(draft) === i + 1 ? color : '#94A3B8',
                    font: `${touched && Math.round(draft) === i + 1 ? 700 : 500} 10px/14px Manrope`,
                    transition: 'color .15s',
                  }}>{s}</span>
                ))}
              </div>

              <button onClick={() => setRating(Math.round(draft))} disabled={!touched} style={{
                width: '100%', marginTop: 14, padding: '11px', borderRadius: 12, border: 0,
                cursor: touched ? 'pointer' : 'not-allowed',
                background: touched ? '#4F46E5' : '#E2E8F0',
                color: touched ? '#fff' : '#94A3B8',
                font: '600 13px/16px Manrope',
              }}>{T.save}</button>

              <button onClick={onSeeHistory} style={{
                width: '100%', marginTop: 6, padding: '8px', borderRadius: 10, border: 0,
                background: 'transparent', color: '#4F46E5',
                font: '500 12px/16px Manrope', cursor: 'pointer',
                display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 6,
              }}>
                {T.exploreHistory}
                <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
                  <path d="M5 12 h14 M13 6 l6 6 -6 6"/>
                </svg>
              </button>
            </>
          ) : (
            <RatedActions lang={lang} rating={rating}
              onEdit={() => { setRating(null); setTouched(false); setDraft(3); }}
              onShare={() => alert(T.sharePoster)}
              onSeeHistory={onSeeHistory}/>
          )}
        </RatingCardShell>
      </div>
    </div>
  );
}

Object.assign(window, {
  V1_Thumbs, V2_Faces, V3_SliderContext,
  VibeBlurb, MoodFace, ratingColor, VIBE_COPY, Lbl,
});
