// PhoneShell — iPhone-ish device frame with dynamic island, bottom nav, sheet, particles.

const { useState, useEffect, useRef } = React;

// Rain/snow particles overlay used by Atmospheric direction.
function Particles({ cond }) {
  if (cond !== 'rain' && cond !== 'snow') return null;
  const isSnow = cond === 'snow';
  const count = isSnow ? 32 : 60;
  const items = [];
  for (let i = 0; i < count; i++) {
    const left = (i * 7.3) % 100;
    const delay = ((i * 0.13) % 2.5);
    const dur = isSnow ? 5 + ((i*0.3) % 4) : 0.9 + ((i*0.17) % 0.7);
    if (isSnow) {
      items.push(
        <div key={i} style={{
          position: 'absolute', left: `${left}%`, top: 0,
          width: 6, height: 6, borderRadius: '50%', background: '#F1F5F9',
          boxShadow: '0 0 6px rgba(255,255,255,.6)',
          animation: `wvSnowFall ${dur}s linear ${delay}s infinite`, opacity: 0,
        }}/>
      );
    } else {
      items.push(
        <div key={i} style={{
          position: 'absolute', left: `${left}%`, top: 0,
          width: 1, height: 14, background: 'linear-gradient(180deg, rgba(147,197,253,0) 0%, rgba(147,197,253,.9) 100%)',
          animation: `wvRainDrop ${dur}s linear ${delay}s infinite`, opacity: 0,
        }}/>
      );
    }
  }
  return <div style={{ position: 'absolute', inset: 0, pointerEvents: 'none', overflow: 'hidden' }}>{items}</div>;
}

// iOS-style phone shell: rounded bezel, dynamic island, status bar, content, bottom nav.
function PhoneShell({ children, statusDark = false, statusTime = '21:15', bg = '#FAFBFF' }) {
  return (
    <div style={{
      width: 390, height: 820,
      borderRadius: 54, background: '#0B0B0F',
      padding: 10, position: 'relative',
      boxShadow: '0 30px 80px rgba(15,23,42,.28), inset 0 0 0 1.5px #1F2937',
    }}>
      <div style={{
        width: '100%', height: '100%', borderRadius: 44, overflow: 'hidden',
        background: bg, position: 'relative',
      }}>
        {/* dynamic island */}
        <div style={{
          position: 'absolute', top: 10, left: '50%', transform: 'translateX(-50%)',
          width: 116, height: 34, background: '#0B0B0F', borderRadius: 100, zIndex: 30,
        }} />
        {/* status bar */}
        <div style={{
          position: 'absolute', top: 0, left: 0, right: 0, height: 54, zIndex: 25,
          display: 'flex', alignItems: 'center', justifyContent: 'space-between',
          padding: '16px 28px 0', pointerEvents: 'none',
          color: statusDark ? '#fff' : '#0F172A',
          font: '600 16px/1 Manrope, sans-serif',
        }}>
          <span>{statusTime}</span>
          <span style={{ display: 'inline-flex', alignItems: 'center', gap: 6, fontSize: 12 }}>
            <svg width="18" height="12" viewBox="0 0 18 12" fill="none">
              <rect x="0" y="8" width="3" height="4" rx="1" fill="currentColor"/>
              <rect x="5" y="5" width="3" height="7" rx="1" fill="currentColor"/>
              <rect x="10" y="2" width="3" height="10" rx="1" fill="currentColor"/>
              <rect x="15" y="0" width="3" height="12" rx="1" fill="currentColor"/>
            </svg>
            <svg width="16" height="12" viewBox="0 0 16 12" fill="none">
              <path d="M8 11.5 l 1.5 -1.5 a 2 2 0 0 0 -3 0 Z" fill="currentColor"/>
              <path d="M8 9.5 l 3 -3 a 4.3 4.3 0 0 0 -6 0 Z" fill="currentColor" opacity=".8"/>
              <path d="M8 7.5 l 4.5 -4.5 a 6.4 6.4 0 0 0 -9 0 Z" fill="currentColor" opacity=".6"/>
            </svg>
            <span style={{
              display: 'inline-flex', alignItems: 'center', border: `1.2px solid ${statusDark ? '#fff' : '#0F172A'}`,
              borderRadius: 3, padding: '0 3px', height: 11, gap: 1,
            }}>
              <span style={{ fontSize: 8, fontWeight: 700, letterSpacing: '-.3px' }}>87</span>
              <span style={{ width: 10, height: 5, background: 'currentColor', borderRadius: 1, marginLeft: 1 }} />
            </span>
          </span>
        </div>
        {children}
      </div>
    </div>
  );
}

// Bottom-nav (3 tabs).
function BottomNav({ active, onTab, dark = false, tint = '#4F46E5' }) {
  const lang = window.__wvLang || 'pl';
  const T = i18n[lang];
  const items = [
    { id: 'home',    label: T.home,    icon: (
      <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M3 11 L12 3 L21 11 V21 a1 1 0 0 1 -1 1 h-5 v-7 h-4 v7 H4 a1 1 0 0 1 -1 -1 Z"/>
      </svg>)},
    { id: 'places',  label: T.places,  icon: (
      <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M12 21s-7-7.5-7-12.5 A7 7 0 0 1 19 8.5 C19 13.5 12 21 12 21 Z"/>
        <circle cx="12" cy="9" r="2.5"/>
      </svg>)},
    { id: 'profile', label: T.profile, icon: (
      <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <circle cx="12" cy="8" r="4"/>
        <path d="M4 21 a8 8 0 0 1 16 0"/>
      </svg>)},
  ];
  const bg = dark ? 'rgba(11,11,15,.72)' : 'rgba(255,255,255,.88)';
  const border = dark ? 'rgba(255,255,255,.08)' : 'rgba(15,23,42,.06)';
  const inkInactive = dark ? 'rgba(255,255,255,.55)' : '#64748B';
  return (
    <div style={{
      position: 'absolute', left: 0, right: 0, bottom: 0, zIndex: 20,
      padding: '10px 12px 24px',
      background: bg, borderTop: `1px solid ${border}`,
      backdropFilter: 'blur(18px)', WebkitBackdropFilter: 'blur(18px)',
    }}>
      <div style={{ display: 'flex', justifyContent: 'space-around', gap: 4 }}>
        {items.map(it => {
          const sel = active === it.id;
          return (
            <button key={it.id} onClick={() => onTab(it.id)} className="wv-btn" style={{
              flex: 1, maxWidth: 130,
              display: 'flex', alignItems: 'center', gap: 8,
              padding: sel ? '10px 16px' : '10px 12px',
              borderRadius: 999, border: 0, cursor: 'pointer',
              background: sel ? (dark ? 'rgba(129,140,248,.22)' : '#E0E7FF') : 'transparent',
              color: sel ? tint : inkInactive,
              font: `${sel ? 600 : 500} 13px/16px Manrope, sans-serif`,
              justifyContent: 'center',
            }}>
              {it.icon}
              {sel && <span>{it.label}</span>}
            </button>
          );
        })}
      </div>
    </div>
  );
}

// Bottom sheet w/ backdrop.
function BottomSheet({ open, onClose, children, dark = false }) {
  return (
    <>
      <div onClick={onClose} style={{
        position: 'absolute', inset: 0, background: 'rgba(15,23,42,.45)',
        opacity: open ? 1 : 0, pointerEvents: open ? 'auto' : 'none',
        transition: 'opacity .25s', zIndex: 40,
      }} />
      <div style={{
        position: 'absolute', left: 0, right: 0, bottom: 0, zIndex: 41,
        background: dark ? '#1C2B48' : '#fff',
        color: dark ? '#fff' : '#0F172A',
        borderTopLeftRadius: 28, borderTopRightRadius: 28,
        transform: open ? 'translateY(0)' : 'translateY(100%)',
        transition: 'transform .35s cubic-bezier(.4,0,.2,1)',
        boxShadow: '0 -8px 32px rgba(15,23,42,.18)',
        maxHeight: '82%', overflowY: 'auto',
      }} className="wv-scroll">
        <div style={{
          width: 40, height: 4, borderRadius: 2, background: dark ? 'rgba(255,255,255,.3)' : '#CBD5E1',
          margin: '10px auto 0',
        }}/>
        {children}
      </div>
    </>
  );
}

Object.assign(window, { PhoneShell, BottomNav, BottomSheet, Particles });
