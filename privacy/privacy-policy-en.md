---
---

# WeatherVibe Privacy Policy

**Effective date:** 8 May 2026
**Last updated:** 9 May 2026

This privacy policy describes how the **WeatherVibe** mobile application ("the App") handles user
data. The app developer ("we", "our") respects your privacy and limits data processing to the
minimum necessary to provide weather features.

In short: **we do not run our own servers, your personal data (name, favorite locations, mood
history) stays exclusively on your device, and we do not sell user information.**

The App uses **Google AdMob** to display advertisements, **Firebase Remote Config** to
remotely toggle features, and **Firebase Analytics** to measure basic usage statistics. These
services operate on a consent basis under GDPR — you make your first choice on first launch in the
EU/EEA, and you can change it at any time.

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
- Location **is not shared** with advertising networks or analytics tools. It only goes to the
  weather providers listed in section 3.

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

### 2.4. Technical and analytical data (Firebase Analytics)

The App uses **Firebase Analytics** by Google to measure basic usage metrics (e.g. number of app
launches, crashes, OS version). Firebase Analytics collects, among other things:

- the **Firebase installation ID**,
- the **Android Advertising ID (AAID)** — if you have given consent and have not reset it in your
  system settings,
- device model and language, Android system version, country (based on IP),
- system events (app launch, session duration, crashes).

Data is sent to Google at `app-measurement.com` and `*.google-analytics.com`. We do **not** pass
your username, list of favorite locations, mood ratings, or AI-generated content to Firebase
Analytics.

You can reset or remove your advertising ID at any time via Android Settings → Privacy → Ads.

### 2.5. Ad consent management (UMP)

If you use the App in the **European Union, United Kingdom, or Switzerland**, on first launch the
App displays a consent form managed by **Google User Messaging Platform (UMP)**. The form lets you:

- consent to personalized ads and related processing by Google AdMob,
- limit ads to **non-personalized ads (NPA)**,
- decline consent (in which case ads will not be displayed).

You can change your decision at any time via **Profile → Privacy settings**. The decision is stored
locally by the UMP SDK; the form will not appear again as long as that decision exists.

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

### 3.3. Google AdMob (advertising)

- **Purpose:** displaying banner ads on selected screens (Home, Locations list, Activity Planner).
- **Data sent:** Android Advertising ID (AAID), approximate location based on IP, technical device
  data (model, OS, language), app context, the consent status collected via UMP (see 2.5).
- **Scope of processing depends on consent:**
    - **Consent for personalized ads** — Google AdMob may profile you based on activity in other
      apps and websites.
    - **No consent (NPA)** — only non-personalized ads are shown, based on app context and
      approximate location.
    - **Consent declined** — ads are not displayed.
- **Hosts:** `googleads.g.doubleclick.net`, `pagead2.googlesyndication.com`, `csi.gstatic.com`,
  `fundingchoicesmessages.google.com`.
- **Provider's privacy policy:** https://policies.google.com/privacy
- **How Google uses data from apps:** https://policies.google.com/technologies/partner-sites

### 3.4. Firebase (Google) — Remote Config and Analytics

- **Purpose:**
    - **Remote Config** — remotely toggling app features (e.g. ad availability per screen) without
      shipping a new release.
    - **Analytics** — see section 2.4.
- **Data sent:** Firebase installation ID, technical device data, AAID (if consent given), system
  events.
- **Hosts:** `firebaseremoteconfig.googleapis.com`, `firebaseinstallations.googleapis.com`,
  `app-measurement.com`, `*.google-analytics.com`.
- **Provider's privacy policy:** https://firebase.google.com/support/privacy

### 3.5. Other recipients

Beyond the services in sections 3.1–3.4 we do not share data with any data brokers or social
services.

---

## 4. Data storage

- The data listed in section 2.3 is stored **only locally** on your device.
- We have no database server or cloud account on which your personal data is stored.
- Technical identifiers used by Firebase Analytics and AdMob are processed by Google in line with
  their privacy policies.
- Uninstalling the App **permanently deletes** all data from the device, including Firebase
  identifiers and the locally stored UMP decision.
- You can clear app data at any time via Android Settings → Apps → WeatherVibe → Storage → Clear
  data.

---

## 5. Retention period

Local data (preferences, mood-rating history, favorite locations, weather cache) is kept *
*indefinitely** or until you delete it yourself or uninstall the App.

Data processed by Google (AdMob, Firebase Analytics, Remote Config) is retained according to
Google's policies — by default up to 14 months for Analytics events.

---

## 6. Your rights (GDPR)

Because all personal data you enter in the App stays on your device, you have full control over it.
You can:

- **view** it on the relevant app screens (profile, locations, vibe history),
- **modify** it within the app,
- **delete** it by clearing app data or uninstalling the app,
- **change your ad consent** in the UMP panel (Profile → Privacy settings),
- **reset your AAID advertising identifier** in the Android system settings,
- **opt out of Google ad personalization** at the Google account level: https://myadcenter.google.com.

For data processed by Google you can also use Google's own export/deletion mechanisms at
https://myaccount.google.com.

If you have questions about data processing or wish to exercise other GDPR rights (including the
right to information, objection, restriction of processing), contact us at: *
*weathervibe.support@gmail.com**.

If you believe your rights have been infringed, you may lodge a complaint with the supervisory
authority — in Poland this is the **President of the Personal Data Protection Office
** (https://uodo.gov.pl). EU residents can also contact their local Data Protection Authority.

---

## 7. Children

The App is not directed at children under 13 and does not knowingly collect any data from children.
AdMob ads are configured with `tagForUnderAgeOfConsent = false`, meaning we do not target content
to users covered by age-restricted regulations. If you are a parent or guardian and believe your
child has used the app, contact us — we will help remove related identifiers.

---

## 8. Security

- Communication with external services (Open-Meteo, Anthropic, Google AdMob, Firebase) takes place
  exclusively over **HTTPS**.
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
