# Local Storage & Room DB Guidelines

1. **Library:** Use AndroidX Room.
2. **Entities:** Define `@Entity` classes in the `data/local/entity` package. Each entity in its own file.
3. **DAOs:** Define `@Dao` interfaces. Use `suspend` functions for one-shot inserts/updates and return `Flow<List<T>>` for observable queries.
4. **TypeConverters:** Use `@TypeConverter` for complex data types (like lists of hourly forecasts) utilizing `kotlinx.serialization` to convert to/from JSON strings.
5. **Separation of Concerns:** Do not expose Room Entities to the Domain or UI layers. Map them to Domain models inside the Repository.
