// vibe-history-screen.jsx
// Ekran "Twój vibe" — historia ocen w postaci kalendarza miesięcznego.
// Nawigacja prev/next month, klik w dzień → tooltip/detail z pogodą + ocena.

const { useState: useStateH, useMemo: useMemoH } = React;

// ─── Fake data: generujemy oceny dla dni w 2026 roku (luty-kwiecień)
function makeHistoryMap() {
  const conds = ['partly', 'sunny', 'rain', 'cloudy', 'partly', 'rain', 'sunny', 'partly', 'cloudy', 'sunny', 'rain'];
  const baseByCond = { sunny: 4.3, partly: 3.8, cloudy: 3.0, rain: 3.3, snow: 2.8, night: 3.4 };
  const map = {}; // "YYYY-MM-DD" → { cond, tempC, rating, note }
  // pokryj okres ~80 dni wstecz od 24.04.2026
  const today = new Date(2026, 3, 24); // Apr 24 2026 (month 0-indexed)
  for (let i = 0; i < 80; i++) {
    const d = new Date(today); d.setDate(d.getDate() - i);
    const key = `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')}`;
    const seed = (d.getDate() * 37 + d.getMonth() * 13) % 100;
    const cond = conds[(d.getDate() + d.getMonth()) % conds.length];
    const noise = ((seed % 10) - 5) / 10;
    const hasRating = (i + 2) % 5 !== 0 && i !== 0; // dziś nieocenione
    const rating = hasRating ? Math.max(1, Math.min(5, Math.round(baseByCond[cond] + noise))) : null;
    const tempC = 5 + (seed % 20);
    map[key] = { cond, tempC, rating, date: new Date(d) };
  }
  return map;
}
const HIST_MAP = makeHistoryMap();

function dayKey(d) {
  return `${d.getFullYear()}-${String(d.getMonth()+1).padStart(2,'0')}-${String(d.getDate()).padStart(2,'0')}`;
}

function getStats() {
  const entries = Object.values(HIST_MAP).filter(e => e.rating !== null);
  const avg = entries.reduce((s, e) => s + e.rating, 0) / entries.length;
  const byCond = {};
  entries.forEach(e => {
    byCond[e.cond] = byCond[e.cond] || { sum: 0, n: 0 };
    byCond[e.cond].sum += e.rating;
    byCond[e.cond].n++;
  });
  const condAvg = Object.entries(byCond).map(([cond, { sum, n }]) => ({
    cond, avg: sum / n, n,
  })).sort((a, b) => b.avg - a.avg);
  return { avg, condAvg, total: entries.length };
}

// ─── Nawigowalny kalendarz miesięczny z tooltipem
function MonthCalendar({ lang, onDayTap, selectedKey }) {
  const T = VIBE_COPY[lang];
  const [viewDate, setViewDate] = useStateH(new Date(2026, 3, 1)); // April 2026

  const y = viewDate.getFullYear(), m = viewDate.getMonth();
  const firstOfMonth = new Date(y, m, 1);
  const daysInMonth = new Date(y, m + 1, 0).getDate();
  // Monday-start grid: getDay()=0 is Sunday → map to 6
  const startWeekday = (firstOfMonth.getDay() + 6) % 7;
  const today = new Date(2026, 3, 24);
  const todayKey = dayKey(today);

  const cells = [];
  for (let i = 0; i < startWeekday; i++) cells.push(null);
  for (let d = 1; d <= daysInMonth; d++) cells.push(new Date(y, m, d));
  while (cells.length % 7 !== 0) cells.push(null);
  while (cells.length < 42) cells.push(null);

  const monthName = T.monthNames[m];
  const prev = () => setViewDate(new Date(y, m - 1, 1));
  const next = () => setViewDate(new Date(y, m + 1, 1));

  // disable next if we'd go past current month
  const canNext = (y < 2026) || (y === 2026 && m < 3);

  return (
    <div>
      {/* Header with nav */}
      <div style={{
        display: 'flex', justifyContent: 'space-between', alignItems: 'center',
        padding: '0 4px 10px',
      }}>
        <button onClick={prev} style={{
          width: 32, height: 32, borderRadius: '50%', border: 0, cursor: 'pointer',
          background: '#F1F5F9', color: '#475569',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
        }}>
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round">
            <path d="M15 18 L9 12 L15 6"/>
          </svg>
        </button>
        <div style={{
          font: '600 15px/20px Manrope', color: '#0F172A', letterSpacing: '-.2px',
        }}>{monthName} {y}</div>
        <button onClick={next} disabled={!canNext} style={{
          width: 32, height: 32, borderRadius: '50%', border: 0,
          cursor: canNext ? 'pointer' : 'not-allowed',
          background: canNext ? '#F1F5F9' : '#F8FAFC',
          color: canNext ? '#475569' : '#CBD5E1',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
        }}>
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round">
            <path d="M9 18 L15 12 L9 6"/>
          </svg>
        </button>
      </div>

      <div style={{
        padding: 14, background: '#fff', borderRadius: 18,
        boxShadow: '0 1px 2px rgba(15,23,42,.04)',
      }}>
        {/* weekday labels (Pon-Nd) */}
        <div style={{
          display: 'grid', gridTemplateColumns: 'repeat(7, 1fr)', gap: 4, marginBottom: 6,
        }}>
          {T.weekdaysShort.map((w, i) => (
            <div key={i} style={{
              font: '600 10px/14px Manrope', color: '#94A3B8',
              textAlign: 'center', textTransform: 'uppercase',
            }}>{w}</div>
          ))}
        </div>

        {/* cells */}
        <div style={{
          display: 'grid', gridTemplateColumns: 'repeat(7, 1fr)', gap: 4,
        }}>
          {cells.map((date, i) => {
            if (!date) return <div key={i} style={{ aspectRatio: '1 / 1' }}/>;
            const key = dayKey(date);
            const entry = HIST_MAP[key];
            const isToday = key === todayKey;
            const isFuture = date > today;
            const isSelected = key === selectedKey;
            const bg = isFuture ? '#F8FAFC'
              : entry?.rating != null ? ratingColor(entry.rating)
              : '#F1F5F9';
            const fg = isFuture ? '#CBD5E1'
              : entry?.rating != null ? '#fff'
              : '#94A3B8';
            return (
              <button key={i}
                onClick={() => !isFuture && onDayTap(key, entry, date)}
                disabled={isFuture}
                style={{
                  aspectRatio: '1 / 1', borderRadius: 10,
                  background: bg, color: fg,
                  border: isSelected ? '2px solid #0F172A' : (isToday ? '2px solid #4F46E5' : '2px solid transparent'),
                  cursor: isFuture ? 'default' : 'pointer',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  font: `${entry?.rating != null ? 700 : 500} 13px/16px Manrope`,
                  padding: 0, position: 'relative',
                  transition: 'all .15s',
                }}>
                {date.getDate()}
                {entry?.rating != null && isToday && (
                  <div style={{
                    position: 'absolute', bottom: 3, right: 3,
                    width: 5, height: 5, borderRadius: '50%', background: '#fff',
                  }}/>
                )}
              </button>
            );
          })}
        </div>

        {/* legend */}
        <div style={{
          marginTop: 12, display: 'flex', alignItems: 'center', gap: 6,
          font: '500 10px/14px Manrope', color: '#64748B',
        }}>
          <span>{T.legendLow}</span>
          {[1,2,3,4,5].map(r => (
            <span key={r} style={{
              width: 14, height: 14, borderRadius: 4, background: ratingColor(r),
            }}/>
          ))}
          <span>{T.legendHigh}</span>
        </div>
      </div>
    </div>
  );
}

// ─── Detail card that shows after tapping a day (inline tooltip style)
function DayDetail({ lang, dateKey, entry, date, onClose }) {
  const T = VIBE_COPY[lang];
  const condLabels = {
    sunny: lang === 'pl' ? 'Słonecznie' : 'Sunny',
    partly: lang === 'pl' ? 'Częściowe zachmurzenie' : 'Partly cloudy',
    rain: lang === 'pl' ? 'Deszcz' : 'Rain',
    cloudy: lang === 'pl' ? 'Pochmurno' : 'Cloudy',
    snow: lang === 'pl' ? 'Śnieg' : 'Snow',
    night: lang === 'pl' ? 'Pogodna noc' : 'Clear night',
  };
  const dayNamesLong = lang === 'pl'
    ? ['niedziela', 'poniedziałek', 'wtorek', 'środa', 'czwartek', 'piątek', 'sobota']
    : ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  const monthsLong = lang === 'pl'
    ? ['stycznia','lutego','marca','kwietnia','maja','czerwca','lipca','sierpnia','września','października','listopada','grudnia']
    : T.monthNames;
  const title = `${dayNamesLong[date.getDay()]}, ${date.getDate()} ${monthsLong[date.getMonth()]}`;

  return (
    <div style={{
      marginTop: 12, padding: 16, borderRadius: 18,
      background: entry?.rating != null
        ? `linear-gradient(135deg, ${ratingColor(entry.rating)}22 0%, ${ratingColor(entry.rating)}08 100%)`
        : '#fff',
      border: entry?.rating != null
        ? `1px solid ${ratingColor(entry.rating)}55`
        : '1px solid #E2E8F0',
      animation: 'wvFade .2s ease-out',
    }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', gap: 10 }}>
        <div style={{ flex: 1, minWidth: 0 }}>
          <div style={{
            font: '500 11px/14px Manrope', color: '#64748B', letterSpacing: '.2px',
          }}>{title}</div>
          <div style={{
            font: '600 16px/22px Manrope', color: '#0F172A', marginTop: 2,
          }}>
            {entry ? `${condLabels[entry.cond]} · ${entry.tempC}°` : (lang === 'pl' ? 'Brak danych' : 'No data')}
          </div>
        </div>
        <button onClick={onClose} style={{
          width: 26, height: 26, borderRadius: '50%', border: 0, cursor: 'pointer',
          background: 'rgba(255,255,255,.6)', color: '#64748B',
          fontSize: 16, lineHeight: 1, padding: 0,
        }}>×</button>
      </div>

      <div style={{
        marginTop: 12, display: 'flex', alignItems: 'center', gap: 12,
      }}>
        <Glyph cond={entry?.cond || 'cloudy'} size={40}/>
        {entry?.rating != null ? (
          <>
            <MoodFace value={entry.rating} size={40} active={true}/>
            <div style={{ flex: 1 }}>
              <div style={{
                font: '700 15px/20px Manrope', color: ratingColor(entry.rating),
              }}>
                {T.ratingScale[entry.rating - 1]}
              </div>
              <div style={{ font: '400 11px/15px Manrope', color: '#64748B' }}>
                {entry.rating}/5 · {lang === 'pl' ? 'zapisane automatycznie' : 'auto-saved'}
              </div>
            </div>
          </>
        ) : (
          <div style={{ flex: 1, font: '400 13px/18px Manrope', color: '#64748B' }}>
            {dayKey(new Date(2026, 3, 24)) === dateKey
              ? (lang === 'pl' ? 'Dziś jeszcze nie oceniłeś.' : "You haven't rated today yet.")
              : (lang === 'pl' ? 'Ten dzień nie został oceniony.' : 'Not rated.')
            }
          </div>
        )}
      </div>
    </div>
  );
}

function TopConditions({ lang, stats }) {
  const T = VIBE_COPY[lang];
  const condLabels = {
    sunny: lang === 'pl' ? 'Słonecznie' : 'Sunny',
    partly: lang === 'pl' ? 'Częściowe' : 'Partly cloudy',
    rain: lang === 'pl' ? 'Deszcz' : 'Rain',
    cloudy: lang === 'pl' ? 'Pochmurno' : 'Cloudy',
    snow: lang === 'pl' ? 'Śnieg' : 'Snow',
    night: lang === 'pl' ? 'Noc' : 'Night',
  };
  return (
    <div>
      <Lbl>{T.avgByCond}</Lbl>
      <div style={{
        marginTop: 10, padding: 16, background: '#fff', borderRadius: 18,
        boxShadow: '0 1px 2px rgba(15,23,42,.04)',
        display: 'flex', flexDirection: 'column', gap: 14,
      }}>
        {stats.condAvg.map(c => (
          <div key={c.cond} style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
            <Glyph cond={c.cond} size={28}/>
            <div style={{ flex: 1, minWidth: 0 }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'baseline' }}>
                <span style={{ font: '600 13px/16px Manrope', color: '#0F172A' }}>
                  {condLabels[c.cond] || c.cond}
                </span>
                <span style={{ font: '500 11px/14px Manrope', color: '#94A3B8' }}>
                  {c.n} {lang === 'pl' ? 'dni' : 'days'}
                </span>
              </div>
              <div style={{
                marginTop: 6, height: 6, borderRadius: 999, background: '#F1F5F9', position: 'relative',
              }}>
                <div style={{
                  position: 'absolute', top: 0, bottom: 0, left: 0,
                  width: `${(c.avg / 5) * 100}%`,
                  background: ratingColor(c.avg), borderRadius: 999,
                }}/>
              </div>
            </div>
            <div style={{
              minWidth: 32, textAlign: 'right',
              font: '600 15px/18px Manrope', color: ratingColor(c.avg),
            }}>{c.avg.toFixed(1)}</div>
          </div>
        ))}
      </div>
    </div>
  );
}

function VibeHistoryScreen({ lang = 'pl' }) {
  const T = VIBE_COPY[lang];
  const stats = useMemoH(() => getStats(), []);
  const [selected, setSelected] = useStateH({ key: '2026-04-22', date: new Date(2026, 3, 22) });

  const selEntry = selected?.key ? HIST_MAP[selected.key] : null;

  return (
    <div style={{ background: '#FAFBFF', minHeight: '100%', paddingBottom: 110 }}>
      {/* Header */}
      <div style={{ padding: '68px 22px 18px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
          <button style={{
            width: 36, height: 36, borderRadius: '50%', border: 0,
            background: 'transparent', cursor: 'pointer', marginLeft: -8,
            display: 'flex', alignItems: 'center', justifyContent: 'center',
          }}>
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#0F172A" strokeWidth="2" strokeLinecap="round">
              <path d="M15 18 L9 12 L15 6"/>
            </svg>
          </button>
          <div>
            <Lbl>{T.historySub}</Lbl>
            <div style={{
              font: '700 26px/30px Manrope', letterSpacing: '-.4px', color: '#0F172A', marginTop: 2,
            }}>{T.historyTitle}</div>
          </div>
        </div>

        {/* Summary stats */}
        <div style={{
          marginTop: 18, display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8,
        }}>
          <div style={{
            padding: '14px 14px', borderRadius: 14, background: '#fff',
            boxShadow: '0 1px 2px rgba(15,23,42,.04)',
          }}>
            <div style={{
              font: '300 28px/30px Manrope', color: ratingColor(stats.avg), letterSpacing: '-.5px',
            }}>{stats.avg.toFixed(1)}<span style={{ font: '500 12px/16px Manrope', color: '#94A3B8' }}>/5</span></div>
            <div style={{ font: '500 10px/14px Manrope', color: '#64748B' }}>
              {lang === 'pl' ? 'twoja średnia' : 'your average'}
            </div>
          </div>
          <div style={{
            padding: '14px 14px', borderRadius: 14, background: '#fff',
            boxShadow: '0 1px 2px rgba(15,23,42,.04)',
          }}>
            <div style={{ font: '300 28px/30px Manrope', color: '#0F172A' }}>
              {stats.total}
            </div>
            <div style={{ font: '500 10px/14px Manrope', color: '#64748B' }}>
              {lang === 'pl' ? 'ocenionych dni' : 'rated days'}
            </div>
          </div>
        </div>
      </div>

      {/* Kalendarz + detail */}
      <div style={{ padding: '0 22px 20px' }}>
        <MonthCalendar lang={lang}
          selectedKey={selected?.key}
          onDayTap={(key, entry, date) => setSelected({ key, date })}/>

        {selected && (
          <DayDetail lang={lang}
            dateKey={selected.key}
            entry={selEntry}
            date={selected.date}
            onClose={() => setSelected(null)}/>
        )}
      </div>

      {/* Najlepsza pogoda */}
      <div style={{ padding: '0 22px 20px' }}>
        <div style={{
          padding: 18, borderRadius: 20,
          background: 'linear-gradient(160deg, #FEF3C7 0%, #FED7AA 100%)',
        }}>
          <Lbl color="#78350F">{T.favWeather}</Lbl>
          <div style={{ marginTop: 10, display: 'flex', alignItems: 'center', gap: 14 }}>
            <div style={{
              width: 56, height: 56, borderRadius: 16, background: 'rgba(255,255,255,.5)',
              display: 'flex', alignItems: 'center', justifyContent: 'center',
            }}>
              <Glyph cond="sunny" size={36}/>
            </div>
            <div style={{ flex: 1 }}>
              <div style={{ font: '700 20px/24px Manrope', color: '#78350F', letterSpacing: '-.3px' }}>
                {lang === 'pl' ? '20°C, słonecznie' : '20°C, sunny'}
              </div>
              <div style={{ font: '500 12px/16px Manrope', color: '#9A3412', marginTop: 2 }}>
                <strong>4.3/5</strong> · {stats.total} {lang === 'pl' ? 'ocen' : 'ratings'}
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Ranking */}
      <div style={{ padding: '0 22px' }}>
        <TopConditions lang={lang} stats={stats}/>
      </div>
    </div>
  );
}

Object.assign(window, { VibeHistoryScreen, HIST_MAP });
