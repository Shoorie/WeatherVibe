// WeatherVibe — shared UI primitives used across directions.

const wv = {
  ink: '#0F172A', ink2: '#475569', ink3: '#64748B',
  line: '#E2E8F0', surface: '#FFFFFF', bg: '#FAFBFF',
  indigo: '#4F46E5', indigoSoft: '#E0E7FF', indigoInk: '#1E1B4B',
  cool: '#0284C7', warm: '#D97706',
};

// ─── Label above sections (12px/uppercase/letter-spaced)
function SectionLabel({ children, style, color }) {
  return (
    <div style={{
      font: '500 11px/16px Manrope, sans-serif',
      letterSpacing: '0.6px', textTransform: 'uppercase',
      color: color || '#64748B', marginBottom: 8, ...style,
    }}>{children}</div>
  );
}

// ─── Temp range pill: colored bar + optional "now" dot.
function TempRange({ lo, hi, showDot, nowT, minT = -10, maxT = 30 }) {
  const { left, width } = rangePct(lo, hi, minT, maxT);
  const gradient = `linear-gradient(90deg, ${tempColor(lo, minT, maxT)}, ${tempColor(hi, minT, maxT)})`;
  const nowLeft = nowT !== undefined ? ((nowT - minT) / (maxT - minT)) * 100 : null;
  return (
    <div style={{ position: 'relative', flex: 1, height: 6, background: '#E2E8F0', borderRadius: 999 }}>
      <div style={{
        position: 'absolute', top: 0, bottom: 0, left: `${left}%`, width: `${width}%`,
        background: gradient, borderRadius: 999,
      }} />
      {nowLeft !== null && (
        <div style={{
          position: 'absolute', top: -3, left: `calc(${nowLeft}% - 6px)`,
          width: 12, height: 12, borderRadius: '50%', background: '#0F172A',
          border: '2px solid #fff',
        }} />
      )}
    </div>
  );
}

// ─── Big weather glyph. Simple stylized SVG per condition. Scales + animates.
function WeatherGlyph({ cond, size = 96, animated = true, mood = 'light' }) {
  const s = size;
  // Classic emoji-style glyphs, drawn in SVG so they look consistent on web.
  if (cond === 'partly') {
    return (
      <svg width={s} height={s} viewBox="0 0 120 120" style={{ display: 'block' }}>
        <defs>
          <radialGradient id={`glow-${cond}-${size}`} cx="50%" cy="50%" r="50%">
            <stop offset="0%" stopColor="#FCD34D" stopOpacity="0.5" />
            <stop offset="100%" stopColor="#FCD34D" stopOpacity="0" />
          </radialGradient>
        </defs>
        <circle cx="45" cy="45" r="45" fill={`url(#glow-${cond}-${size})`} />
        {/* sun rays */}
        <g transform="translate(45 45)">
          {animated && (
            <animateTransform attributeName="transform" type="rotate"
              from="0" to="360" dur="24s" repeatCount="indefinite" additive="sum" />
          )}
          {Array.from({length: 8}).map((_,i) => (
            <rect key={i} x="-2" y="-38" width="4" height="12" rx="2" fill="#F59E0B"
              transform={`rotate(${i * 45})`} />
          ))}
        </g>
        {/* sun body */}
        <circle cx="45" cy="45" r="18" fill="#FCD34D" />
        <circle cx="45" cy="45" r="14" fill="#FBBF24" />
        {/* cloud */}
        <g>
          {animated && (
            <animateTransform attributeName="transform" type="translate"
              values="0 0; 3 0; 0 0" dur="6s" repeatCount="indefinite" />
          )}
          <ellipse cx="78" cy="78" rx="28" ry="18" fill="#E5E7EB" />
          <ellipse cx="62" cy="72" rx="18" ry="14" fill="#F3F4F6" />
          <ellipse cx="92" cy="74" rx="14" ry="12" fill="#F9FAFB" />
          <ellipse cx="78" cy="86" rx="32" ry="8" fill="#D1D5DB" opacity="0.6"/>
        </g>
      </svg>
    );
  }
  if (cond === 'cloudy' || cond === 'overcast') {
    return (
      <svg width={s} height={s} viewBox="0 0 120 120">
        <g>
          {animated && (
            <animateTransform attributeName="transform" type="translate"
              values="0 0; 4 0; 0 0" dur="7s" repeatCount="indefinite" />
          )}
          <ellipse cx="42" cy="62" rx="22" ry="18" fill="#D1D5DB" />
          <ellipse cx="78" cy="62" rx="26" ry="20" fill="#E5E7EB" />
          <ellipse cx="60" cy="52" rx="22" ry="16" fill="#F3F4F6" />
          <ellipse cx="60" cy="72" rx="38" ry="10" fill="#9CA3AF" opacity="0.4"/>
        </g>
      </svg>
    );
  }
  if (cond === 'rain') {
    return (
      <svg width={s} height={s} viewBox="0 0 120 120">
        <ellipse cx="42" cy="50" rx="22" ry="18" fill="#94A3B8" />
        <ellipse cx="78" cy="50" rx="26" ry="20" fill="#A8B5C7" />
        <ellipse cx="60" cy="40" rx="22" ry="16" fill="#CBD5E1" />
        {[0,1,2,3,4].map(i => (
          <g key={i}>
            <path d={`M ${38 + i*10} 80 q 0 6 -3 10 q -3 4 -3 10 q 0 4 3 4 q 3 0 3 -4 q 0 -6 -3 -10 q 3 -4 3 -10 Z`}
              fill="#38BDF8" transform={`translate(0 ${i % 2 ? 4 : 0})`}>
              {animated && (
                <animateTransform attributeName="transform" type="translate"
                  values={`0 ${i%2?-4:0}; 0 20; 0 ${i%2?-4:0}`}
                  dur={`${1.2 + i * 0.1}s`} repeatCount="indefinite" begin={`${i * 0.15}s`} />
              )}
              {animated && (
                <animate attributeName="opacity" values="0;1;0" dur={`${1.2 + i*0.1}s`}
                  repeatCount="indefinite" begin={`${i * 0.15}s`} />
              )}
            </path>
          </g>
        ))}
      </svg>
    );
  }
  if (cond === 'snow') {
    return (
      <svg width={s} height={s} viewBox="0 0 120 120">
        <g stroke="#93C5FD" strokeWidth="3" strokeLinecap="round" fill="none">
          <g transform="translate(60 60)">
            {animated && (
              <animateTransform attributeName="transform" type="rotate"
                from="0" to="360" dur="40s" repeatCount="indefinite" additive="sum"/>
            )}
            <line x1="-34" y1="0" x2="34" y2="0" />
            <line x1="0" y1="-34" x2="0" y2="34" />
            <line x1="-24" y1="-24" x2="24" y2="24" />
            <line x1="-24" y1="24" x2="24" y2="-24" />
            {[0,60,120,180,240,300].map(a => (
              <g key={a} transform={`rotate(${a})`}>
                <line x1="20" y1="0" x2="14" y2="-6" />
                <line x1="20" y1="0" x2="14" y2="6" />
                <line x1="30" y1="0" x2="24" y2="-6" />
                <line x1="30" y1="0" x2="24" y2="6" />
              </g>
            ))}
          </g>
          <circle cx="60" cy="60" r="5" fill="#DBEAFE" stroke="#BFDBFE" />
        </g>
      </svg>
    );
  }
  if (cond === 'sunny') {
    return (
      <svg width={s} height={s} viewBox="0 0 120 120">
        <defs>
          <radialGradient id={`sunglow-${size}`} cx="50%" cy="50%" r="50%">
            <stop offset="0%" stopColor="#FCD34D" stopOpacity="0.6"/>
            <stop offset="100%" stopColor="#F59E0B" stopOpacity="0"/>
          </radialGradient>
        </defs>
        <circle cx="60" cy="60" r="55" fill={`url(#sunglow-${size})`} />
        <g transform="translate(60 60)">
          {animated && (
            <animateTransform attributeName="transform" type="rotate"
              from="0" to="360" dur="30s" repeatCount="indefinite" additive="sum"/>
          )}
          {Array.from({length: 12}).map((_,i) => (
            <rect key={i} x="-2.5" y="-44" width="5" height={i%2?16:12} rx="2.5" fill="#F59E0B"
              transform={`rotate(${i * 30})`} />
          ))}
        </g>
        <circle cx="60" cy="60" r="24" fill="#FCD34D" />
        <circle cx="60" cy="60" r="20" fill="#FBBF24" />
      </svg>
    );
  }
  if (cond === 'night') {
    return (
      <svg width={s} height={s} viewBox="0 0 120 120">
        <defs>
          <radialGradient id={`moonglow-${size}`} cx="50%" cy="50%" r="50%">
            <stop offset="0%" stopColor="#C4B5FD" stopOpacity="0.35"/>
            <stop offset="100%" stopColor="#C4B5FD" stopOpacity="0"/>
          </radialGradient>
        </defs>
        <circle cx="60" cy="60" r="55" fill={`url(#moonglow-${size})`} />
        <path d="M 72 32 a 28 28 0 1 0 18 48 a 22 22 0 1 1 -18 -48 Z" fill="#F8FAFC" />
        <circle cx="42" cy="42" r="1.6" fill="#F8FAFC" />
        <circle cx="95" cy="50" r="1.2" fill="#F8FAFC" />
        <circle cx="32" cy="78" r="1.4" fill="#F8FAFC" />
        <circle cx="92" cy="88" r="1.2" fill="#F8FAFC" />
      </svg>
    );
  }
  return null;
}

// ─── Animated weather scene (full-bleed background for "Atmospheric" direction).
function WeatherScene({ cond, children, style }) {
  const c = CONDITIONS[cond] || CONDITIONS.partly;
  const g = `linear-gradient(180deg, ${c.skyDark[0]} 0%, ${c.skyDark[1]} 50%, ${c.skyDark[2]} 100%)`;
  const nightStars = cond === 'night' && (
    <div style={{ position: 'absolute', inset: 0, pointerEvents: 'none' }}>
      {Array.from({length: 40}).map((_,i) => {
        const x = (i * 37) % 100, y = (i * 53) % 70;
        return <div key={i} style={{
          position: 'absolute', left: `${x}%`, top: `${y}%`,
          width: i%5===0 ? 2 : 1, height: i%5===0 ? 2 : 1, borderRadius: '50%',
          background: '#F8FAFC', opacity: 0.4 + ((i%5)/10),
          animation: `wvTwinkle ${2 + (i%5)}s ease-in-out ${i*0.1}s infinite`,
        }}/>;
      })}
    </div>
  );
  return (
    <div style={{
      position: 'relative', background: g,
      minHeight: '100%', overflow: 'hidden', ...style,
    }}>
      {nightStars}
      {children}
    </div>
  );
}

// ─── Mini-tab / chip / pill components
function Chip({ children, selected, onClick, dismissable, onDismiss, style }) {
  return (
    <span onClick={onClick} style={{
      display: 'inline-flex', alignItems: 'center', gap: 6,
      padding: dismissable ? '6px 10px 6px 14px' : '6px 14px',
      borderRadius: 999, cursor: onClick ? 'pointer' : 'default',
      background: selected ? wv.indigo : '#F1F5F9',
      border: `1px solid ${selected ? wv.indigo : wv.line}`,
      color: selected ? '#fff' : wv.ink,
      font: '500 12px/16px Manrope, sans-serif', userSelect: 'none',
      ...style,
    }}>
      {children}
      {dismissable && (
        <span onClick={(e) => { e.stopPropagation(); onDismiss && onDismiss(); }}
              style={{ opacity: .7, fontSize: 14, lineHeight: 1, marginLeft: 2 }}>×</span>
      )}
    </span>
  );
}

function IconButton({ children, onClick, style, size = 40 }) {
  return (
    <button onClick={onClick} style={{
      width: size, height: size, border: 0, borderRadius: '50%',
      background: 'transparent', cursor: 'pointer', display: 'inline-flex',
      alignItems: 'center', justifyContent: 'center', color: 'inherit',
      fontFamily: 'Manrope, sans-serif', fontSize: 18,
      ...style,
    }}>{children}</button>
  );
}

// ─── Keyframes for any direction
function GlobalStyles() {
  return (
    <style>{`
      @keyframes wvTwinkle { 0%,100%{ opacity:.2;} 50%{ opacity:.9;} }
      @keyframes wvDrift   { 0%{ transform:translateX(0);} 50%{ transform:translateX(6%);} 100%{ transform:translateX(0);} }
      @keyframes wvDriftR  { 0%{ transform:translateX(0);} 50%{ transform:translateX(-6%);} 100%{ transform:translateX(0);} }
      @keyframes wvFade    { from{ opacity: 0; transform: translateY(8px);} to{ opacity:1; transform:translateY(0);} }
      @keyframes wvSlideUp { from{ transform: translateY(100%);} to{ transform: translateY(0);} }
      @keyframes wvPulse   { 0%,100%{ transform:scale(1); opacity:.8;} 50%{ transform:scale(1.04); opacity:1;} }
      @keyframes wvFloat   { 0%,100%{ transform:translateY(0);} 50%{ transform:translateY(-6px);} }
      @keyframes wvSpin    { from { transform: rotate(0);} to { transform: rotate(360deg); } }
      @keyframes wvRainDrop{ 0%{transform:translateY(-10px); opacity:0;} 10%{opacity:.8;} 90%{opacity:.8;} 100%{transform:translateY(420px); opacity:0;} }
      @keyframes wvSnowFall{ 0%{transform:translateY(-10px) rotate(0); opacity:0;} 10%{opacity:1;} 100%{transform:translateY(420px) rotate(360deg); opacity:0;} }
      html, body { margin: 0; }
      * { box-sizing: border-box; }
      .wv-scroll::-webkit-scrollbar { display: none; }
      .wv-scroll { scrollbar-width: none; }
      .wv-btn:active { opacity: .85; transform: scale(.99); }
      .wv-link { color: #4F46E5; font-weight: 600; cursor: pointer; }
    `}</style>
  );
}

Object.assign(window, { wv, SectionLabel, TempRange, WeatherGlyph, WeatherScene, Chip, IconButton, GlobalStyles });
