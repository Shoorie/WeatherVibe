# 🌐 Network & Ktor Guidelines (CRITICAL RULES)

> **Core Principle:** This project uses Ktor Client for all network operations. Retrofit, OkHttp
> (directly), and GSON/Moshi are strictly forbidden.

## 📋 Table of Contents
1. [Library & Setup](#1-library--setup)
2. [Data Transfer Objects (DTO)](#2-data-transfer-objects-dto)
3. [API Service / Client Structure](#3-api-service--client-structure)
4. [Safe API Calls & Error Handling (CRITICAL)](#4-safe-api-calls--error-handling-critical)
5. [Data Mapping & Boundaries (THE GOLDEN RULE)](#5-data-mapping--boundaries-the-golden-rule)
6. [Dependency Injection (Koin)](#6-dependency-injection-koin)
7. [Implementation Example (Universal Pattern)](#7-implementation-example-universal-pattern)
8. [Self-Verification Checklist](#9-self-verification-checklist)

---

## 1. Library & Setup
* **Engine:** Use `Ktor Client` with the `CIO` or `Android` engine.
* **Serialization:** Use `ContentNegotiation` with `kotlinx.serialization`.
* * **Logging:** Enable `Logging` plugin for debug builds only.

* **FORBIDDEN:** Do NOT use Retrofit, OkHttp (directly), or GSON/Moshi.

---

## 2. Data Transfer Objects (DTO)
* **Location:** Define DTOs in the `data/remote/dto` package.
* **Rule:** Every DTO MUST be in its own separate file.
* **Naming:** DTO classes MUST have the `Dto` or `Response` suffix (e.g., `UserResponse`).
* **Annotation:** Mark all DTOs with `@Serializable`. Use `@SerialName` for every field.

---

## 3. API Service / Client Structure
* **Interface:** Define a clean interface for network calls (e.g., `interface UserApiService`).
* **Implementation:** Name the implementation with the `Default` prefix (e.g., `DefaultUserApiService`).

---

## 4. Safe API Calls & Error Handling (CRITICAL)
* **Threading:** All network calls MUST run inside `withContext(Dispatchers.IO)`.
* **Read Use Cases:** return `Flow<Result<T>>` via `flow { }.catch { }`.
* **Write Use Cases:** `suspend fun` that throws on failure; the ViewModel handles errors with
  a `CoroutineExceptionHandler`.
* **Never** use `runCatching` — it swallows `CancellationException`.

---

## 5. Data Mapping & Boundaries (THE GOLDEN RULE)
* **FORBIDDEN:** Network DTOs MUST NEVER leak into the `:domain` or `:feature` modules.
* **Mappers:** You MUST create mappers in `data/remote/mapper` to convert DTOs to Domain Models.
* **Role:** The Repository calls the API, maps the result, and returns a clean Domain Model.

---

## 6. Dependency Injection (Koin)
* **Rule:** For detailed DI rules, see `docs/ai-rules/di-koin.md`.
* **Requirement:** API services and Repositories MUST use `@Single`.

---

## 7. Implementation Example (Universal Pattern)

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

## 8. Self-Verification Checklist
Before finalizing network-related changes, verify:

1. [ ] **Tech Stack:** Only Ktor and kotlinx.serialization used?
2. [ ] **DTO Naming:** Do all DTOs have `Dto` or `Response` suffix?
3. [ ] **SerialNames:** Is every field in DTOs annotated with `@SerialName`?
4. [ ] **File Structure:** Is every DTO in its own separate file?
5. [ ] **Threading:** Are network calls wrapped in `withContext(Dispatchers.IO)`?
6. [ ] **Data Leakage:** Do DTOs remain strictly within the `:data` module?
7. [ ] **Mappers:** Are there explicit mappers from DTO to Domain models?
8. [ ] **Naming:** API implementation has `Default` prefix?
9. [ ] **DI:** Are you following rules in `docs/ai-rules/di-koin.md`?
10. [ ] **Serialization:** All DTOs marked with `@Serializable`?
