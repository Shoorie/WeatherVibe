// places-variants.jsx
// Trzy warianty ekranu "Lokalizacje" dla WeatherVibe.
// Wszystkie w DNA kierunku A (Vibe Soft): indigo, Manrope, miękkie karty.
// Cel użytkownika: "wszystko po trochu" — przegląd, porównanie, przełączenie, dodanie.
// Wszystkie warianty: drag-to-reorder, home wyróżnione, tap → wybierz miasto.

const { useState, useRef, useEffect } = React;

// ─── Shared helpers ────────────────────────────────────────────────

// Minimal weather glyph — dopasowany do DS kierunku A.
function Glyph({ cond, size = 28, tint }) {
  const s = size;
  const color = tint || '#4F46E5';
  const sun = 'radial-gradient(circle, #FCD34D 0%, #F59E0B 100%)';
  if (cond === 'sunny') {
    return (
      <svg width={s} height={s} viewBox="0 0 32 32">
        <circle cx="16" cy="16" r="6" fill="#F59E0B"/>
        {[0,45,90,135,180,225,270,315].map(a => (
          <line key={a} x1="16" y1="3" x2="16" y2="7" stroke="#F59E0B" strokeWidth="1.6" strokeLinecap="round" transform={`rotate(${a} 16 16)`}/>
        ))}
      </svg>
    );
  }
  if (cond === 'partly') {
    return (
      <svg width={s} height={s} viewBox="0 0 32 32">
        <circle cx="12" cy="12" r="5" fill="#F59E0B"/>
        <path d="M 10 22 q -5 0 -5 -5 q 0 -4 4 -5 q 1 -5 6 -5 q 5 0 6 4 q 5 0 5 4 q 1 6 -5 7 Z" fill="#CBD5E1"/>
      </svg>
    );
  }
  if (cond === 'rain') {
    return (
      <svg width={s} height={s} viewBox="0 0 32 32">
        <path d="M 10 18 q -5 0 -5 -5 q 0 -4 4 -5 q 1 -5 6 -5 q 5 0 6 4 q 5 0 5 4 q 1 6 -5 7 Z" fill="#94A3B8"/>
        <path d="M 11 22 l -2 5" stroke="#3B82F6" strokeWidth="1.5" strokeLinecap="round"/>
        <path d="M 16 22 l -2 5" stroke="#3B82F6" strokeWidth="1.5" strokeLinecap="round"/>
        <path d="M 21 22 l -2 5" stroke="#3B82F6" strokeWidth="1.5" strokeLinecap="round"/>
      </svg>
    );
  }
  if (cond === 'snow') {
    return (
      <svg width={s} height={s} viewBox="0 0 32 32">
        <path d="M 10 18 q -5 0 -5 -5 q 0 -4 4 -5 q 1 -5 6 -5 q 5 0 6 4 q 5 0 5 4 q 1 6 -5 7 Z" fill="#E2E8F0"/>
        <circle cx="10" cy="25" r="1.5" fill="#fff" stroke="#94A3B8"/>
        <circle cx="16" cy="27" r="1.5" fill="#fff" stroke="#94A3B8"/>
        <circle cx="22" cy="25" r="1.5" fill="#fff" stroke="#94A3B8"/>
      </svg>
    );
  }
  if (cond === 'night') {
    return (
      <svg width={s} height={s} viewBox="0 0 32 32">
        <path d="M 22 16 a 8 8 0 1 1 -8 -10 a 6 6 0 0 0 8 10 Z" fill="#E2E8F0"/>
        <circle cx="7" cy="8" r="0.9" fill="#FCD34D"/>
        <circle cx="25" cy="26" r="0.9" fill="#FCD34D"/>
      </svg>
    );
  }
  // cloudy
  return (
    <svg width={s} height={s} viewBox="0 0 32 32">
      <path d="M 10 22 q -5 0 -5 -5 q 0 -4 4 -5 q 1 -5 6 -5 q 5 0 6 4 q 5 0 5 4 q 1 6 -5 7 Z" fill="#94A3B8"/>
      <path d="M 14 26 q -4 0 -4 -4 q 0 -3 3 -4 q 1 -4 5 -4 q 4 0 5 3 q 4 0 4 3 q 1 5 -4 6 Z" fill="#CBD5E1" opacity=".85"/>
    </svg>
  );
}

// Mini-wykres dobowej temperatury — 24 pkt sparkline.
function TempSparkline({ hi, lo, cond, width = 120, height = 28, strokeColor }) {
  // Pseudo-deterministyczna krzywa bazująca na hi/lo + cond.
  const pts = [];
  const n = 24;
  for (let i = 0; i < n; i++) {
    const t = i / (n - 1);
    // dwa "guzy" ciepła (rano/popołudnie) + noc
    const diurnal = Math.sin((t - 0.25) * Math.PI * 2) * 0.5 + 0.5;
    const v = lo + (hi - lo) * Math.pow(diurnal, 1.3);
    pts.push(v);
  }
  const min = Math.min(...pts), max = Math.max(...pts);
  const range = max - min || 1;
  const d = pts.map((v, i) => {
    const x = (i / (n - 1)) * width;
    const y = height - ((v - min) / range) * height;
    return `${i === 0 ? 'M' : 'L'} ${x.toFixed(1)} ${y.toFixed(1)}`;
  }).join(' ');
  const fill = d + ` L ${width} ${height} L 0 ${height} Z`;
  const color = strokeColor || '#4F46E5';
  return (
    <svg width={width} height={height} viewBox={`0 0 ${width} ${height}`}>
      <defs>
        <linearGradient id={`g-${color.slice(1)}`} x1="0" y1="0" x2="0" y2="1">
          <stop offset="0%" stopColor={color} stopOpacity="0.25"/>
          <stop offset="100%" stopColor={color} stopOpacity="0"/>
        </linearGradient>
      </defs>
      <path d={fill} fill={`url(#g-${color.slice(1)})`} />
      <path d={d} fill="none" stroke={color} strokeWidth="1.5" strokeLinecap="round" strokeLinejoin="round"/>
    </svg>
  );
}

// Drag-to-reorder hook. Returns: { order, onDragStart, onDragOver, onDragEnd, draggingId, overId }.
function useReorder(initial) {
  const [order, setOrder] = useState(initial);
  const [draggingId, setDraggingId] = useState(null);
  const [overId, setOverId] = useState(null);

  // Keep order in sync if initial length changes (e.g. tweaks).
  useEffect(() => {
    // add any new ids not yet in order
    setOrder(prev => {
      const missing = initial.filter(x => !prev.includes(x));
      return missing.length ? [...prev, ...missing] : prev;
    });
  }, [initial.join('|')]);

  const onDragStart = (id) => (e) => {
    setDraggingId(id);
    e.dataTransfer.effectAllowed = 'move';
    // Firefox needs data set
    try { e.dataTransfer.setData('text/plain', id); } catch (_) {}
  };
  const onDragOver = (id) => (e) => {
    e.preventDefault();
    e.dataTransfer.dropEffect = 'move';
    if (id !== overId) setOverId(id);
  };
  const onDrop = (targetId) => (e) => {
    e.preventDefault();
    if (!draggingId || draggingId === targetId) return;
    setOrder(prev => {
      const next = [...prev];
      const from = next.indexOf(draggingId);
      const to = next.indexOf(targetId);
      if (from < 0 || to < 0) return prev;
      next.splice(from, 1);
      next.splice(to, 0, draggingId);
      return next;
    });
    setDraggingId(null);
    setOverId(null);
  };
  const onDragEnd = () => { setDraggingId(null); setOverId(null); };

  return { order, onDragStart, onDragOver, onDrop, onDragEnd, draggingId, overId };
}

// Header współdzielony.
function PlacesHeader({ lang, count, onSearch, onEdit, editing }) {
  return (
    <div style={{ padding: '68px 22px 18px' }}>
      <div style={{ display: 'flex', alignItems: 'flex-end', justifyContent: 'space-between' }}>
        <div>
          <div style={{
            font: '500 11px/14px Manrope', letterSpacing: '2px',
            textTransform: 'uppercase', color: '#64748B',
          }}>{lang === 'pl' ? 'Twoje miejsca' : 'Your places'}</div>
          <div style={{
            font: '700 28px/32px Manrope', letterSpacing: '-.5px', color: '#0F172A',
            marginTop: 4,
          }}>
            {lang === 'pl' ? 'Lokalizacje' : 'Locations'}
            <span style={{
              marginLeft: 10, font: '500 14px/20px Manrope',
              color: '#94A3B8', verticalAlign: 'middle',
            }}>{count}</span>
          </div>
        </div>
        <div style={{ display: 'flex', gap: 6 }}>
          <button onClick={onSearch} aria-label="search" style={{
            width: 36, height: 36, borderRadius: '50%', border: 0, cursor: 'pointer',
            background: '#F1F5F9', color: '#475569',
            display: 'flex', alignItems: 'center', justifyContent: 'center',
          }}>
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
              <circle cx="11" cy="11" r="7"/><path d="M21 21 l-4.3-4.3"/>
            </svg>
          </button>
          <button onClick={onEdit} style={{
            height: 36, padding: '0 14px', borderRadius: 18, border: 0, cursor: 'pointer',
            background: editing ? '#4F46E5' : '#F1F5F9',
            color: editing ? '#fff' : '#475569',
            font: '600 12px/16px Manrope', letterSpacing: '.2px',
          }}>{editing ? (lang === 'pl' ? 'Gotowe' : 'Done') : (lang === 'pl' ? 'Edytuj' : 'Edit')}</button>
        </div>
      </div>
    </div>
  );
}

// Reusable add-button row.
function AddRow({ lang, onClick, style }) {
  return (
    <button onClick={onClick} className="wv-btn" style={{
      width: '100%', padding: '18px 20px',
      background: 'rgba(79,70,229,.04)', border: '1px dashed rgba(79,70,229,.35)',
      borderRadius: 18, color: '#4F46E5', cursor: 'pointer',
      display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 8,
      font: '600 14px/20px Manrope',
      ...style,
    }}>
      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
        <path d="M12 5v14M5 12h14"/>
      </svg>
      {lang === 'pl' ? 'Dodaj miasto' : 'Add a city'}
    </button>
  );
}

// ════════════════════════════════════════════════════════════════════
// WARIANT 1 — "AMBIENT CARDS"
// Pełnowymiarowe karty miast z atmosferycznym gradientem pogody.
// Najbogatsze wizualnie. Home = duża hero karta na górze.
// ════════════════════════════════════════════════════════════════════
function V1AmbientCard({ city, lang, isHome, dragging, overMe, dragHandlers, editing, onPick }) {
  const c = CONDITIONS[city.cond];
  // Wyciągnij kolorystykę z CONDITIONS — gradient neba jako tło karty.
  const grad = `linear-gradient(135deg, ${c.skyDark[0]} 0%, ${c.skyDark[1]} 55%, ${c.skyDark[2]} 100%)`;
  const name = lang === 'pl' ? city.name_pl : city.name_en;
  const region = lang === 'pl' ? city.region_pl : city.region_en;
  const condLabel = lang === 'pl'
    ? ({ partly: 'Częściowo zachmurzenie', sunny: 'Słonecznie', rain: 'Deszczowo', snow: 'Śnieżnie', night: 'Pogodna noc', cloudy: 'Pochmurno' }[city.cond])
    : ({ partly: 'Partly cloudy', sunny: 'Sunny', rain: 'Rainy', snow: 'Snowing', night: 'Clear night', cloudy: 'Cloudy' }[city.cond]);

  return (
    <div
      draggable={editing}
      {...(editing ? {
        onDragStart: dragHandlers.onDragStart(city.id),
        onDragOver:  dragHandlers.onDragOver(city.id),
        onDrop:      dragHandlers.onDrop(city.id),
        onDragEnd:   dragHandlers.onDragEnd,
      } : {})}
      onClick={() => !editing && onPick(city)}
      style={{
        position: 'relative', borderRadius: 22, overflow: 'hidden', cursor: editing ? 'grab' : 'pointer',
        background: grad, color: '#fff',
        padding: isHome ? '22px 22px 20px' : '18px 20px 16px',
        minHeight: isHome ? 170 : 112,
        boxShadow: overMe ? '0 0 0 2px #4F46E5, 0 10px 28px rgba(79,70,229,.25)' : '0 6px 20px rgba(15,23,42,.10)',
        opacity: dragging ? 0.4 : 1,
        transform: dragging ? 'scale(.98)' : 'none',
        transition: 'box-shadow .15s, transform .15s, opacity .15s',
        display: 'flex', flexDirection: 'column', justifyContent: 'space-between',
      }}
    >
      {/* gradient szum */}
      <div style={{
        position: 'absolute', inset: 0, pointerEvents: 'none',
        background: 'radial-gradient(ellipse at 85% -10%, rgba(255,255,255,.25), transparent 50%)',
      }}/>

      {/* TOP row: name + temp */}
      <div style={{ position: 'relative', display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', gap: 12 }}>
        <div style={{ flex: 1, minWidth: 0 }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
            {isHome && (
              <svg width="13" height="13" viewBox="0 0 24 24" fill="currentColor" style={{ opacity: .9 }}>
                <path d="M12 3 L2 12h3v9h6v-6h2v6h6v-9h3z"/>
              </svg>
            )}
            <div style={{
              font: `${isHome ? '700 20px/26px' : '600 17px/22px'} Manrope`,
              letterSpacing: '-.3px',
            }}>{name}</div>
          </div>
          <div style={{
            font: '400 12px/16px Manrope', opacity: .8, marginTop: 2,
            whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis',
          }}>
            {city.localTime} · {region}
          </div>
        </div>
        <div style={{ textAlign: 'right', flexShrink: 0 }}>
          <div style={{ font: `200 ${isHome ? 44 : 36}px/${isHome ? 44 : 36}px Manrope`, letterSpacing: '-1.5px' }}>
            {city.tempC}°
          </div>
          <div style={{ font: '500 11px/14px Manrope', opacity: .8, marginTop: 2 }}>
            ↑{city.hiC}°  ↓{city.loC}°
          </div>
        </div>
      </div>

      {/* BOTTOM row: cond + glyph (tylko hero) */}
      {isHome && (
        <div style={{
          position: 'relative', display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end',
          marginTop: 10,
        }}>
          <div>
            <div style={{ font: '600 13px/18px Manrope', opacity: .95 }}>{condLabel}</div>
            <div style={{ font: '400 11px/16px Manrope', opacity: .7, marginTop: 2 }}>
              {lang === 'pl' ? 'Odczuwalna' : 'Feels like'} {c.feelsC}°
            </div>
          </div>
          <div style={{ opacity: .95 }}>
            <Glyph cond={city.cond} size={42} />
          </div>
        </div>
      )}
      {!isHome && (
        <div style={{
          position: 'relative', display: 'flex', justifyContent: 'space-between', alignItems: 'flex-end', marginTop: 6,
        }}>
          <div style={{ font: '500 12px/16px Manrope', opacity: .85 }}>{condLabel}</div>
          <Glyph cond={city.cond} size={26} />
        </div>
      )}

      {editing && (
        <div style={{
          position: 'absolute', top: 10, right: 10,
          width: 28, height: 28, borderRadius: 14, background: 'rgba(255,255,255,.2)',
          display: 'flex', alignItems: 'center', justifyContent: 'center',
        }}>
          <svg width="12" height="12" viewBox="0 0 12 12" fill="currentColor"><circle cx="3" cy="3" r="1"/><circle cx="9" cy="3" r="1"/><circle cx="3" cy="6" r="1"/><circle cx="9" cy="6" r="1"/><circle cx="3" cy="9" r="1"/><circle cx="9" cy="9" r="1"/></svg>
        </div>
      )}
    </div>
  );
}

function V1_AmbientCards({ lang = 'pl', onPick = () => {}, onAdd = () => {} }) {
  const [editing, setEditing] = useState(false);
  const reorder = useReorder(CITIES.map(c => c.id));
  const byId = Object.fromEntries(CITIES.map(c => [c.id, c]));
  const ordered = reorder.order.map(id => byId[id]).filter(Boolean);
  const home = ordered.find(c => c.home);
  const rest = ordered.filter(c => !c.home);

  return (
    <div style={{ background: '#FAFBFF', minHeight: '100%', paddingBottom: 110 }}>
      <PlacesHeader lang={lang} count={CITIES.length}
        onSearch={onAdd} onEdit={() => setEditing(!editing)} editing={editing}/>

      {editing && (
        <div style={{ padding: '0 22px 10px', font: '400 12px/18px Manrope', color: '#64748B' }}>
          {lang === 'pl' ? '✋ Przytrzymaj i przeciągnij, żeby zmienić kolejność' : '✋ Drag a card to reorder'}
        </div>
      )}

      {/* HERO: home */}
      {home && (
        <div style={{ padding: '0 16px 12px' }}>
          <V1AmbientCard city={home} lang={lang} isHome={true}
            editing={editing} dragHandlers={reorder}
            dragging={reorder.draggingId === home.id}
            overMe={reorder.overId === home.id && reorder.draggingId !== home.id}
            onPick={onPick} />
        </div>
      )}

      {/* Section label */}
      <div style={{
        padding: '8px 22px 10px',
        font: '500 11px/14px Manrope', letterSpacing: '1.4px',
        textTransform: 'uppercase', color: '#64748B',
      }}>{lang === 'pl' ? 'Obserwowane' : 'Following'}</div>

      {/* Rest */}
      <div style={{ padding: '0 16px', display: 'flex', flexDirection: 'column', gap: 10 }}>
        {rest.map(c => (
          <V1AmbientCard key={c.id} city={c} lang={lang}
            editing={editing} dragHandlers={reorder}
            dragging={reorder.draggingId === c.id}
            overMe={reorder.overId === c.id && reorder.draggingId !== c.id}
            onPick={onPick} />
        ))}
        <AddRow lang={lang} onClick={onAdd} style={{ marginTop: 4 }}/>
      </div>
    </div>
  );
}

// ════════════════════════════════════════════════════════════════════
// WARIANT 2 — "CLEAN LIST + SPARKLINE"
// Minimalistyczny. Białe tło, akcent indigo tylko na temperaturze.
// Sparkline 24h daje "glanceable" dane. Home = pierwszy z subtelnym znacznikiem.
// ════════════════════════════════════════════════════════════════════
function V2Row({ city, lang, isHome, dragging, overMe, dragHandlers, editing, onPick }) {
  const c = CONDITIONS[city.cond];
  const name = lang === 'pl' ? city.name_pl : city.name_en;
  const region = lang === 'pl' ? city.region_pl : city.region_en;

  return (
    <div
      draggable={editing}
      {...(editing ? {
        onDragStart: dragHandlers.onDragStart(city.id),
        onDragOver:  dragHandlers.onDragOver(city.id),
        onDrop:      dragHandlers.onDrop(city.id),
        onDragEnd:   dragHandlers.onDragEnd,
      } : {})}
      onClick={() => !editing && onPick(city)}
      style={{
        position: 'relative', cursor: editing ? 'grab' : 'pointer',
        background: overMe ? '#EEF2FF' : (isHome ? '#fff' : '#fff'),
        borderRadius: 16, padding: '14px 16px 12px',
        boxShadow: isHome ? 'inset 0 0 0 1px rgba(79,70,229,.25), 0 1px 2px rgba(15,23,42,.04)' : '0 1px 2px rgba(15,23,42,.06)',
        display: 'flex', alignItems: 'center', gap: 14,
        opacity: dragging ? 0.4 : 1,
        transform: overMe ? 'translateY(-1px)' : 'none',
        transition: 'all .15s',
      }}
    >
      {editing && (
        <div style={{ color: '#94A3B8', flexShrink: 0 }}>
          <svg width="14" height="14" viewBox="0 0 12 12" fill="currentColor"><circle cx="3" cy="3" r="1"/><circle cx="9" cy="3" r="1"/><circle cx="3" cy="6" r="1"/><circle cx="9" cy="6" r="1"/><circle cx="3" cy="9" r="1"/><circle cx="9" cy="9" r="1"/></svg>
        </div>
      )}

      <Glyph cond={city.cond} size={28} />

      <div style={{ flex: 1, minWidth: 0 }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
          <div style={{
            font: '600 15px/20px Manrope', color: '#0F172A', letterSpacing: '-.2px',
            whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis', maxWidth: 140,
          }}>{name}</div>
          {isHome && (
            <span style={{
              font: '700 9px/12px Manrope', letterSpacing: '.6px',
              background: '#EEF2FF', color: '#4F46E5',
              padding: '2px 6px', borderRadius: 6, textTransform: 'uppercase',
            }}>{lang === 'pl' ? 'Dom' : 'Home'}</span>
          )}
        </div>
        <div style={{
          font: '400 11px/14px Manrope', color: '#94A3B8', marginTop: 2,
          display: 'flex', alignItems: 'center', gap: 6,
        }}>
          <span>{city.localTime}</span>
          <span>·</span>
          <span style={{ whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{region}</span>
        </div>
      </div>

      <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
        <TempSparkline hi={city.hiC} lo={city.loC} cond={city.cond} width={60} height={22} strokeColor="#4F46E5"/>
        <div style={{ textAlign: 'right', minWidth: 48 }}>
          <div style={{ font: '300 26px/28px Manrope', color: '#0F172A', letterSpacing: '-1px' }}>
            {city.tempC}°
          </div>
          <div style={{ font: '500 10px/14px Manrope', color: '#94A3B8' }}>
            {city.hiC}°/{city.loC}°
          </div>
        </div>
      </div>
    </div>
  );
}

function V2_CleanList({ lang = 'pl', onPick = () => {}, onAdd = () => {} }) {
  const [editing, setEditing] = useState(false);
  const [sortBy, setSortBy] = useState('manual'); // manual | temp | name
  const reorder = useReorder(CITIES.map(c => c.id));
  const byId = Object.fromEntries(CITIES.map(c => [c.id, c]));
  let ordered = reorder.order.map(id => byId[id]).filter(Boolean);

  if (sortBy === 'temp') ordered = [...ordered].sort((a, b) => b.tempC - a.tempC);
  if (sortBy === 'name') ordered = [...ordered].sort((a, b) => {
    const na = lang === 'pl' ? a.name_pl : a.name_en;
    const nb = lang === 'pl' ? b.name_pl : b.name_en;
    return na.localeCompare(nb);
  });
  // zawsze wyciągnij home na górę jeśli sort === 'manual'
  if (sortBy === 'manual') {
    const home = ordered.find(c => c.home);
    if (home) ordered = [home, ...ordered.filter(c => c.id !== home.id)];
  }

  return (
    <div style={{ background: '#FAFBFF', minHeight: '100%', paddingBottom: 110 }}>
      <PlacesHeader lang={lang} count={CITIES.length}
        onSearch={onAdd} onEdit={() => setEditing(!editing)} editing={editing}/>

      {/* Sort chips */}
      <div style={{ padding: '0 22px 14px', display: 'flex', gap: 6, overflowX: 'auto' }} className="wv-scroll">
        {[
          { v: 'manual', l: lang === 'pl' ? 'Ręcznie' : 'Custom' },
          { v: 'temp',   l: lang === 'pl' ? 'Temperatura' : 'Temperature' },
          { v: 'name',   l: lang === 'pl' ? 'Alfabetycznie' : 'A–Z' },
        ].map(o => (
          <button key={o.v} onClick={() => setSortBy(o.v)} style={{
            flexShrink: 0, padding: '6px 12px', borderRadius: 999, border: 0, cursor: 'pointer',
            background: sortBy === o.v ? '#0F172A' : '#F1F5F9',
            color: sortBy === o.v ? '#fff' : '#475569',
            font: '600 12px/16px Manrope', letterSpacing: '.1px',
          }}>{o.l}</button>
        ))}
      </div>

      {editing && sortBy === 'manual' && (
        <div style={{ padding: '0 22px 10px', font: '400 12px/18px Manrope', color: '#64748B' }}>
          {lang === 'pl' ? '✋ Przeciągnij, żeby zmienić kolejność' : '✋ Drag to reorder'}
        </div>
      )}
      {editing && sortBy !== 'manual' && (
        <div style={{ padding: '0 22px 10px', font: '400 12px/18px Manrope', color: '#DC2626' }}>
          {lang === 'pl' ? 'Wróć do „Ręcznie", żeby przeciągać' : 'Switch to "Custom" to reorder'}
        </div>
      )}

      <div style={{ padding: '0 16px', display: 'flex', flexDirection: 'column', gap: 8 }}>
        {ordered.map(c => (
          <V2Row key={c.id} city={c} lang={lang} isHome={c.home}
            editing={editing && sortBy === 'manual'} dragHandlers={reorder}
            dragging={reorder.draggingId === c.id}
            overMe={reorder.overId === c.id && reorder.draggingId !== c.id}
            onPick={onPick} />
        ))}
        <AddRow lang={lang} onClick={onAdd} style={{ marginTop: 4 }}/>
      </div>
    </div>
  );
}

// ════════════════════════════════════════════════════════════════════
// WARIANT 3 — "COMPARE RAIL"
// Siatka 2-kolumnowa + "compare rail" na górze.
// Gęstsza, umożliwia wzrokowe porównanie. Home = duży kafel na lewo u góry.
// ════════════════════════════════════════════════════════════════════
function V3Tile({ city, lang, isHome, big, dragging, overMe, dragHandlers, editing, onPick }) {
  const c = CONDITIONS[city.cond];
  const name = lang === 'pl' ? city.name_pl : city.name_en;
  const grad = `linear-gradient(160deg, ${c.skyDark[2]}20 0%, ${c.skyDark[0]}40 100%)`;

  return (
    <div
      draggable={editing}
      {...(editing ? {
        onDragStart: dragHandlers.onDragStart(city.id),
        onDragOver:  dragHandlers.onDragOver(city.id),
        onDrop:      dragHandlers.onDrop(city.id),
        onDragEnd:   dragHandlers.onDragEnd,
      } : {})}
      onClick={() => !editing && onPick(city)}
      style={{
        position: 'relative', cursor: editing ? 'grab' : 'pointer',
        background: '#fff', borderRadius: 18, overflow: 'hidden',
        padding: 14, height: big ? 180 : 132,
        boxShadow: overMe ? '0 0 0 2px #4F46E5' : '0 2px 6px rgba(15,23,42,.06)',
        gridColumn: big ? 'span 2' : 'span 1',
        display: 'flex', flexDirection: 'column', justifyContent: 'space-between',
        opacity: dragging ? 0.4 : 1, transition: 'all .15s',
      }}
    >
      {/* subtelny gradient pogodowy w tle */}
      <div style={{ position: 'absolute', inset: 0, background: grad, pointerEvents: 'none' }}/>

      <div style={{ position: 'relative', display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
        <div style={{ minWidth: 0, flex: 1 }}>
          <div style={{ display: 'flex', alignItems: 'center', gap: 4 }}>
            {isHome && (
              <svg width="11" height="11" viewBox="0 0 24 24" fill="#4F46E5">
                <path d="M12 3 L2 12h3v9h6v-6h2v6h6v-9h3z"/>
              </svg>
            )}
            <div style={{
              font: `${big ? '700' : '600'} ${big ? 17 : 14}px/18px Manrope`,
              color: '#0F172A', letterSpacing: '-.2px',
              whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis',
            }}>{name}</div>
          </div>
          <div style={{ font: `400 ${big ? 11 : 10}px/14px Manrope`, color: '#94A3B8', marginTop: 2 }}>
            {city.localTime}
          </div>
        </div>
        <Glyph cond={city.cond} size={big ? 30 : 22}/>
      </div>

      <div style={{ position: 'relative' }}>
        <div style={{
          font: `200 ${big ? 52 : 38}px/${big ? 52 : 38}px Manrope`,
          color: '#0F172A', letterSpacing: '-2px',
        }}>
          {city.tempC}°
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginTop: 4 }}>
          <TempSparkline hi={city.hiC} lo={city.loC} cond={city.cond} width={big ? 80 : 52} height={big ? 22 : 18} strokeColor="#4F46E5"/>
          <div style={{ font: '500 10px/14px Manrope', color: '#64748B' }}>
            {city.hiC}°/{city.loC}°
          </div>
        </div>
      </div>
    </div>
  );
}

function V3_CompareGrid({ lang = 'pl', onPick = () => {}, onAdd = () => {} }) {
  const [editing, setEditing] = useState(false);
  const [compareMode, setCompareMode] = useState(false);
  const [selected, setSelected] = useState([]);
  const reorder = useReorder(CITIES.map(c => c.id));
  const byId = Object.fromEntries(CITIES.map(c => [c.id, c]));
  const ordered = reorder.order.map(id => byId[id]).filter(Boolean);

  const toggleSelect = (city) => {
    if (selected.includes(city.id)) {
      setSelected(selected.filter(x => x !== city.id));
    } else if (selected.length < 3) {
      setSelected([...selected, city.id]);
    }
  };

  const selectedCities = selected.map(id => byId[id]).filter(Boolean);

  return (
    <div style={{ background: '#FAFBFF', minHeight: '100%', paddingBottom: 110 }}>
      <PlacesHeader lang={lang} count={CITIES.length}
        onSearch={onAdd} onEdit={() => setEditing(!editing)} editing={editing}/>

      {/* Compare toggle */}
      <div style={{ padding: '0 16px 14px' }}>
        <button onClick={() => { setCompareMode(!compareMode); setSelected([]); }} style={{
          width: '100%', padding: '10px 14px', borderRadius: 12, border: 0, cursor: 'pointer',
          background: compareMode ? '#0F172A' : '#F1F5F9',
          color: compareMode ? '#fff' : '#475569',
          font: '600 12px/16px Manrope',
          display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 6,
        }}>
          <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round">
            <path d="M3 6h4M3 12h4M3 18h4M13 6h8M13 12h8M13 18h8"/>
          </svg>
          {compareMode
            ? (lang === 'pl' ? `Wybrano ${selected.length}/3 — wyjdź` : `${selected.length}/3 selected — exit`)
            : (lang === 'pl' ? 'Porównaj miasta' : 'Compare cities')
          }
        </button>
      </div>

      {/* Compare rail — pojawia się po wybraniu miast */}
      {compareMode && selectedCities.length >= 2 && (
        <div style={{
          margin: '0 16px 14px', padding: '14px 12px', borderRadius: 16,
          background: '#0F172A', color: '#fff',
        }}>
          <div style={{
            font: '500 10px/14px Manrope', letterSpacing: '1.4px', textTransform: 'uppercase',
            opacity: .6, marginBottom: 10,
          }}>{lang === 'pl' ? 'Porównanie' : 'Side by side'}</div>
          <div style={{ display: 'grid', gridTemplateColumns: `repeat(${selectedCities.length}, 1fr)`, gap: 10 }}>
            {selectedCities.map(city => (
              <div key={city.id}>
                <div style={{ font: '600 12px/16px Manrope', opacity: .95 }}>
                  {lang === 'pl' ? city.name_pl : city.name_en}
                </div>
                <div style={{ font: '200 34px/36px Manrope', letterSpacing: '-1px', marginTop: 2 }}>
                  {city.tempC}°
                </div>
                <div style={{ font: '400 10px/14px Manrope', opacity: .65, marginTop: 2 }}>
                  ↑{city.hiC}° ↓{city.loC}°
                </div>
                <div style={{
                  marginTop: 6, display: 'flex', justifyContent: 'space-between',
                  font: '400 10px/14px Manrope', opacity: .7,
                }}>
                  <span>💨 {city.wind}</span>
                  <span>💧 {city.humidity}</span>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {editing && (
        <div style={{ padding: '0 22px 10px', font: '400 12px/18px Manrope', color: '#64748B' }}>
          {lang === 'pl' ? '✋ Przeciągnij kafelek' : '✋ Drag tiles to reorder'}
        </div>
      )}
      {compareMode && (
        <div style={{ padding: '0 22px 10px', font: '400 12px/18px Manrope', color: '#64748B' }}>
          {lang === 'pl' ? 'Dotknij 2–3 miast do porównania' : 'Tap 2–3 cities to compare'}
        </div>
      )}

      {/* Grid */}
      <div style={{ padding: '0 16px', display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 10 }}>
        {ordered.map((c, i) => {
          const isSelected = selected.includes(c.id);
          return (
            <div key={c.id} style={{ position: 'relative' }}>
              <V3Tile city={c} lang={lang} isHome={c.home} big={c.home && i === 0}
                editing={editing} dragHandlers={reorder}
                dragging={reorder.draggingId === c.id}
                overMe={reorder.overId === c.id && reorder.draggingId !== c.id}
                onPick={compareMode ? () => toggleSelect(c) : onPick} />
              {compareMode && (
                <div style={{
                  position: 'absolute', top: 10, right: 10, width: 22, height: 22,
                  borderRadius: 11, border: '1.5px solid #fff',
                  background: isSelected ? '#4F46E5' : 'rgba(255,255,255,.7)',
                  display: 'flex', alignItems: 'center', justifyContent: 'center',
                  boxShadow: '0 1px 2px rgba(15,23,42,.15)',
                }}>
                  {isSelected && (
                    <svg width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="#fff" strokeWidth="3" strokeLinecap="round">
                      <path d="M5 12l5 5L20 7"/>
                    </svg>
                  )}
                </div>
              )}
            </div>
          );
        })}
      </div>

      <div style={{ padding: '14px 16px 0' }}>
        <AddRow lang={lang} onClick={onAdd}/>
      </div>
    </div>
  );
}

Object.assign(window, { V1_AmbientCards, V2_CleanList, V3_CompareGrid, Glyph, TempSparkline });
