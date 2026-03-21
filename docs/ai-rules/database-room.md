# Local Storage & Room DB Guidelines (CRITICAL RULES)

This project enforces a strict relational database design. Flat JSON storage for structured data is forbidden.

## 1. Relational Schema Design (CRITICAL)
* **FORBIDDEN:** Do NOT dump raw JSON payloads into database columns using `@TypeConverter`. This is an anti-pattern for data that can be modeled relationally.
* **Normalization:** You MUST design a proper relational schema. Use multiple `@Entity` classes and `@ForeignKey` for constraints.
* **Relationships:** Use `@Relation` and intermediate data classes (POJOs) to model 1-to-1, 1-to-many, and many-to-many relationships.
* **TypeConverters:** Use `@TypeConverter` ONLY for truly primitive-like custom types (e.g., `Instant` to `Long`, `BigDecimal` to `String`, or fixed Enums).

## 2. Entity & DAO Standards
* **Location:** Define entities in `data/local/entity` and DAOs in `data/local/dao`.
* **Naming:** All entity classes MUST have the `Entity` suffix (e.g., `UserEntity`, `PostEntity`).
* **DAOs:** * Use `suspend` functions for one-shot operations (Insert, Update, Delete).
    * Return `Flow<T>` or `Flow<List<T>>` for observable queries to ensure UDF.

## 3. Data Mapping & Boundaries
* **FORBIDDEN:** Never expose Room Entities to the `:domain` or `:feature` modules.
* **Mappers:** You MUST create mappers in the `data/local/mapper` package to convert Entities to Domain Models.
* **Repository:** The Repository implementation is the only place where Entities are converted to Domain Models before being passed to the Domain layer.

## 4. Implementation Example (Relational POJO)
When fetching a parent entity with its related children, use the following pattern:

```kotlin
import androidx.room.Embedded
import androidx.room.Relation
import androidx.compose.runtime.Immutable

@Immutable
data class ParentWithChildren(
    @Embedded val parent: ParentEntity,
    @Relation(
        parentColumn = "parentId",
        entityColumn = "ownerParentId"
    )
    val children: List<ChildEntity>
)
```

## 5. Dependency Injection (Koin)
* Annotate the Database provider with `@Single`.
* Annotate every DAO with `@Single` to make them injectable into Repositories.

## 6. Migration Strategy
* Always provide a versioning strategy. For new projects, start with version 1 and use `fallbackToDestructiveMigration()` only during the initial development phase.
