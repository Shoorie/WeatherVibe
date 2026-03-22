# 💾 Local Storage & Room DB Guidelines (CRITICAL RULES)

> **Core Principle:** This project enforces a strict relational database design. Flat JSON storage
> for structured data is strictly forbidden.

## 📋 Table of Contents
1. [Relational Schema Design (CRITICAL)](#1-relational-schema-design-critical)
2. [Entity & DAO Standards](#2-entity--dao-standards)
3. [Data Mapping & Boundaries](#3-data-mapping--boundaries)
4. [Implementation Example (Relational POJO)](#4-implementation-example-relational-pojo)
5. [Dependency Injection (Koin)](#5-dependency-injection-koin)
6. [Migration Strategy](#6-migration-strategy)
7. [Self-Verification Checklist](#7-self-verification-checklist)

---

## 1. Relational Schema Design (CRITICAL)
* **FORBIDDEN:** Do NOT dump raw JSON payloads into database columns using `@TypeConverter`. This is
  an anti-pattern for data that can be modeled relationally.
* **Normalization:** You MUST design a proper relational schema. Use multiple `@Entity` classes and
  `@ForeignKey` for constraints.
* **Relationships:** Use `@Relation` and intermediate data classes (POJOs) to model 1-to-1,
  1-to-many, and many-to-many relationships.
* **TypeConverters:** Use `@TypeConverter` ONLY for truly primitive-like custom types (e.g.,
  `Instant` to `Long`, `BigDecimal` to `String`, or fixed Enums).

---

## 2. Entity & DAO Standards
* **Location:** Define entities in `data/local/entity` and DAOs in `data/local/dao`.
* **Naming:** All entity classes MUST have the `Entity` suffix (e.g., `UserEntity`, `PostEntity`).
* **Constructor Order:** `@PrimaryKey` fields MUST come first, then remaining fields sorted
  alphabetically. This is an exception to the global alphabetical-only rule from `code-style.md`.
* **DAOs:** * Use `suspend` functions for one-shot operations (Insert, Update, Delete).
    * Return `Flow<T>` or `Flow<List<T>>` for observable queries to ensure UDF
      (Unidirectional Data Flow).

---

## 3. Data Mapping & Boundaries
* **FORBIDDEN:** Never expose Room Entities to the `:domain` or `:feature` modules.
* **Mappers:** You MUST create mappers in the `data/local/mapper` package to convert Entities to
  Domain Models.
* **Repository:** The Repository implementation is the only place where Entities are converted to
  Domain Models before being passed to the Domain layer.

---

## 4. Implementation Example (Relational POJO)
When fetching a parent entity with its related children, use the following pattern:

```kotlin
import androidx.room.Embedded
import androidx.room.Relation
import androidx.compose.runtime.Immutable

data class ParentWithChildren(
  @Embedded val parent: ParentEntity,
  
  @Relation(
    parentColumn = "parentId",
    entityColumn = "ownerParentId"
  )
  val children: List<ChildEntity>
)
```

---

## 5. Dependency Injection (Koin)
* Annotate the Database provider with `@Single`.
* Annotate every DAO with `@Single` to make them injectable into Repositories.

---

## 6. Migration Strategy
* Always provide a versioning strategy. For new projects, start with version 1 and use
  `fallbackToDestructiveMigration()` only during the initial development phase.

---

## 7. Self-Verification Checklist
Before finalizing database changes, verify:

1. [ ] **Relational Design:** Is the data modeled relationally? (No JSON blobs in columns?)
2. [ ] **Entity Naming:** Do all Entity classes have the `Entity` suffix?
3. [ ] **DAOs:** Are read operations returning `Flow` for UDF?
4. [ ] **Mapping:** Do Entities remain strictly within the `:data` module? (Used mappers?)
5. [ ] **TypeConverters:** Only used for primitive-like custom types (Enums, Dates)?
6. [ ] **UDF:** Are write operations `suspend` and read operations observable?
7. [ ] **DI:** Are DAOs and the Database provider annotated with `@Single`?
8. [ ] **Constraints:** Are `@ForeignKey` used where appropriate for data integrity?
9. [ ] **Threading:** Are DAO operations called from `Dispatchers.IO` (usually via Repository)?
