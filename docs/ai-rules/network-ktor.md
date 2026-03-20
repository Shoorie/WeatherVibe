# Network (Ktor) & Open-Meteo API Guidelines

1. **Library Setup:** * Use `Ktor Client` with `ContentNegotiation` and `kotlinx.serialization`. Do NOT use Retrofit.
2. **API Provider (Open-Meteo):**
    * Base URL: `https://api.open-meteo.com/v1/forecast`
    * It's a FREE API, no keys required.
    * Default test coordinates (Zielona Góra, Poland): `latitude=51.9354&longitude=15.5064`
    * Required params: `hourly=temperature_2m,weathercode`, `daily=weathercode,temperature_2m_max,temperature_2m_min`, `timezone=auto`.
3. **Data Handling:**
    * Create specific Data Transfer Objects (DTOs) marked with `@Serializable`. Keep each DTO in its own file in the `data/remote/dto` package.
    * Always map network DTOs to clean Domain models in the Repository before returning them.
4. **Error Handling:**
    * Wrap API calls in a `Result<T>` or a custom `NetworkResponse` sealed class. Never throw raw exceptions to the UI.
