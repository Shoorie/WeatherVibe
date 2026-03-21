# Network & Ktor Guidelines (CRITICAL RULES)

This project uses Ktor Client for all network operations. Retrofit is strictly forbidden.

## 1. Library & Setup
* **Engine:** Use `Ktor Client` with the `CIO` or `Android` engine.
* **Serialization:** Use `ContentNegotiation` with `kotlinx.serialization`.
* **Logging:** Enable `Logging` plugin for debug builds only.
* **FORBIDDEN:** Do NOT use Retrofit, OkHttp (directly), or GSON/Moshi.

## 2. Data Transfer Objects (DTO)
* **Location:** Define DTOs in the `data/remote/dto` package.
* **Rule:** Every DTO MUST be in its own separate file.
* **Naming:** DTO classes MUST have the `Dto` or `Response` suffix (e.g., `UserResponse`, `WeatherDto`).
* **Annotation:** Mark all DTOs with `@Serializable`. Use `@SerialName` for every field to decouple Kotlin properties from JSON keys.

## 3. API Service / Client Structure
* **Interface:** Define a clean interface for network calls (e.g., `interface WeatherApiService`).
* **Implementation:** Name the implementation with the `Default` prefix (e.g., `class DefaultWeatherApiService`).
* **Koin:** Annotate the implementation with `@Single`.

## 4. Safe API Calls & Error Handling (CRITICAL)
* **Threading:** All network calls MUST be executed within `withContext(Dispatchers.IO)`.
* **Error Wrapping:** Use Cases are responsible for `runCatching`, but the Repository/Service layer should handle Ktor-specific exceptions (like `ClientRequestException`).

## 5. Data Mapping & Boundaries (THE GOLDEN RULE)
* **FORBIDDEN:** Network DTOs MUST NEVER leak into the `:domain` or `:feature` modules.
* **Mappers:** You MUST create mappers in `data/remote/mapper` to convert DTOs to Domain Models.
* **Role:** The Repository calls the API, maps the result, and returns a clean Domain Model.

## 6. Implementation Example (Universal Pattern)

```kotlin
@Single(binds = [UserRepository::class])
internal class DefaultUserRepository(
  private val api: UserApiService,
) : UserRepository {

  override suspend fun fetchUser(id: String): User = 
    withContext(Dispatchers.IO) {
      api.getUser(id).toDomain() 
    }
}
```

## 7. Clean Imports
* Always use static imports for constants or enum members to keep the network logic readable.
