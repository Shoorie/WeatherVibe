# 🌐 Network & Ktor Guidelines (CRITICAL RULES)

> **Core Principle:** This project uses Ktor Client for all network operations. Retrofit, OkHttp
> (directly), and GSON/Moshi are strictly forbidden.

## 📋 Table of Contents
1. [Library & Setup](#1-library--setup)
2. [Data Transfer Objects (DTO)](#2-data-transfer-objects-dto)
3. [API Service / Client Structure](#3-api-service--client-structure)
4. [Safe API Calls & Error Handling (CRITICAL)](#4-safe-api-calls--error-handling-critical)
5. [Data Mapping & Boundaries (THE GOLDEN RULE)](#5-data-mapping--boundaries-the-golden-rule)
6. [Implementation Example (Universal Pattern)](#6-implementation-example-universal-pattern)
7. [Clean Imports](#7-clean-imports)

---

## 1. Library & Setup
* **Engine:** Use `Ktor Client` with the `CIO` or `Android` engine.
* **Serialization:** Use `ContentNegotiation` with `kotlinx.serialization`.
* **Logging:** Enable `Logging` plugin for debug builds only.
* **FORBIDDEN:** Do NOT use Retrofit, OkHttp (directly), or GSON/Moshi.

---

## 2. Data Transfer Objects (DTO)
* **Location:** Define DTOs in the `data/remote/dto` package.
* **Rule:** Every DTO MUST be in its own separate file.
* **Naming:** DTO classes MUST have the `Dto` or `Response` suffix (e.g., `UserResponse`,
  `ProfileDto`).
* **Annotation:** Mark all DTOs with `@Serializable`. Use `@SerialName` for every field to
  decouple Kotlin properties from JSON keys.

---

## 3. API Service / Client Structure
* **Interface:** Define a clean interface for network calls (e.g., `interface UserApiService`).
* **Implementation:** Name the implementation with the `Default` prefix (e.g.,
  `class DefaultUserApiService`).
* **Koin:** Annotate the implementation with `@Single`.

---

## 4. Safe API Calls & Error Handling (CRITICAL)
* **Threading:** All network calls MUST be executed within `withContext(Dispatchers.IO)`.
* **Error Wrapping:** Repositories throw exceptions or return raw data. Use Cases wrap the call
  in `flow { }.catch { }`.
* **FORBIDDEN:** Do NOT use `runCatching` in Use Cases (as per Architecture rules). The
  Repository/Service layer may map Ktor-specific exceptions (like `ClientRequestException`) into
  domain errors if necessary.

---

## 5. Data Mapping & Boundaries (THE GOLDEN RULE)
* **FORBIDDEN:** Network DTOs MUST NEVER leak into the `:domain` or `:feature` modules.
* **Mappers:** You MUST create mappers in `data/remote/mapper` to convert DTOs to Domain Models.
* **Role:** The Repository calls the API, maps the result, and returns a clean Domain Model.

---

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

---

## 7. Clean Imports
* Always use static imports for constants or enum members to keep the network logic readable.
