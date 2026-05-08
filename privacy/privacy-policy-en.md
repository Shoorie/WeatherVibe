---
---

# WeatherVibe Privacy Policy

**Effective date:** 8 May 2026
**Last updated:** 8 May 2026

This privacy policy describes how the **WeatherVibe** mobile application ("the App") handles user
data. The app developer ("we", "our") respects your privacy and limits data processing to the
minimum necessary to provide weather features.

In short: **we do not run our own servers, do not collect analytics, do not sell user information,
and do not display ads.**

---

## 1. Data controller

- **Controller:** Adrian Zalewski (app developer)
- **Contact:** weathervibe.support@gmail.com

---

## 2. What data the App processes

### 2.1. Device location

The App requests `ACCESS_FINE_LOCATION` and `ACCESS_COARSE_LOCATION` permissions to fetch a weather
forecast for your current position.

- Location is read **only on demand** (after your system-level consent and a manual weather
  refresh).
- Coordinates (`latitude`/`longitude`) are sent to the weather data provider (see section 3) solely
  to retrieve the forecast.
- **We do not store location history.** Locally on the device we keep only the coordinates of
  locations you **manually add to your favorites**, so that their weather can be displayed again
  without re-searching.
- Location **is not shared** with anyone other than the weather providers listed in section 3.

### 2.2. Notifications

The App requests the `POST_NOTIFICATIONS` permission to send you local notifications (e.g. morning
weather brief, pollen alerts, mood-rating reminders).

- All notifications are generated **locally on the device**.
- We do not use push notifications or services such as Firebase Cloud Messaging.

### 2.3. Data you enter

In the App you may enter:

- **Username** (e.g. first name) — used solely to personalize the greeting on the profile screen.
- **Mood rating** (vibe rating) — stored locally to track your personal history.
- **Preferences** (theme, brief tone, favorite locations, notification preferences) — stored
  locally.

All of the above is stored **only on your device** in a Room database and Proto DataStore. None of
it is sent to any server, except as described in section 3.

### 2.4. Technical data

The App **does not use** analytics tools such as Firebase Analytics, Google Analytics, Crashlytics,
Mixpanel, or similar. We do not collect:

- device model,
- system version,
- crash reports,
- in-app session time,
- screens you visit.

---

## 3. With whom we share data

The App relies on the following external services. Each receives only the minimum data necessary to
perform its function.

### 3.1. Open-Meteo (weather data provider)

- **Purpose:** fetching weather forecasts, air-quality data, and location lookup by name.
- **Data sent:** geographic coordinates (`latitude`/`longitude`) or a place name typed into search.
- **Hosts:** `api.open-meteo.com`, `air-quality-api.open-meteo.com`, `geocoding-api.open-meteo.com`.
- **Provider's privacy policy:** https://open-meteo.com/en/terms

Open-Meteo does not require an account or user identifier. We do not send them your name, e-mail
address, or device identifier.

### 3.2. Anthropic (generative AI provider)

- **Purpose:** generating a personalized weather brief ("AI brief") in which the language and tone
  are tailored to your preferences.
- **Data sent:** a text prompt containing:
    - current weather data (temperature, wind, precipitation, air quality),
    - the brief tone you selected (e.g. *humorous*, *formal*),
    - optionally your mood rating, if you provided one.
- **Host:** `api.anthropic.com`
- **Provider's privacy policy:** https://www.anthropic.com/legal/privacy

We **do not send** your username, e-mail, location coordinates, device identifier, or any other
directly identifying data. Per Anthropic's policy, content sent to its commercial API is not used to
train models.

### 3.3. No other recipients

We do not share data with any advertising networks, data brokers, analytics tools, or social
services.

---

## 4. Data storage

- The data listed in section 2.3 is stored **only locally** on your device.
- We have no database server or cloud account on which your data is stored.
- Uninstalling the App **permanently deletes** all data from the device.
- You can clear app data at any time via Android Settings → Apps → WeatherVibe → Storage → Clear
  data.

---

## 5. Retention period

Local data (preferences, mood-rating history, favorite locations, weather cache) is kept *
*indefinitely** or until you delete it yourself or uninstall the App.

---

## 6. Your rights (GDPR)

Because all personal data stays on your device, you have full control over it. You can:

- **view** it on the relevant app screens (profile, locations, vibe history),
- **modify** it within the app,
- **delete** it by clearing app data or uninstalling the app.

If you have questions about data processing or wish to exercise other GDPR rights (including the
right to information, objection, restriction of processing), contact us at: *
*weathervibe.support@gmail.com**.

If you believe your rights have been infringed, you may lodge a complaint with the supervisory
authority — in Poland this is the **President of the Personal Data Protection Office
** (https://uodo.gov.pl). EU residents can also contact their local Data Protection Authority.

---

## 7. Children

The App is not directed at children under 13 and does not knowingly collect any data from children.
If you are a parent or guardian and believe your child has provided us with data, contact us — we
will delete it without delay.

---

## 8. Security

- Communication with external services (Open-Meteo, Anthropic) takes place exclusively over **HTTPS
  **.
- Local data is protected by Android process isolation (sandbox).
- We do not require you to create an account or provide a password.

---

## 9. Changes to this policy

We may update this policy in the future (e.g. when we add new features). The current version is
always available at the same URL, with the date of last update in the header. Material changes will
be announced in the app description on Google Play.

---

## 10. Contact

For privacy-related matters, write to: **weathervibe.support@gmail.com**.
