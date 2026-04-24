package com.weather.vibe.data.location.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.weather.vibe.data.location.local.entity.LocationFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationFavoriteDao {

  @Query("SELECT * FROM favorite_locations ORDER BY position ASC, id ASC")
  fun observeAll(): Flow<List<LocationFavoriteEntity>>

  @Query("SELECT * FROM favorite_locations WHERE id = :id")
  suspend fun findById(id: Long): LocationFavoriteEntity?

  @Query("SELECT * FROM favorite_locations WHERE locationId = :locationId")
  suspend fun findByLocationId(locationId: Long): LocationFavoriteEntity?

  @Query("SELECT COALESCE(MAX(position), -1) FROM favorite_locations")
  suspend fun maxPosition(): Int

  @Query("SELECT COUNT(*) FROM favorite_locations")
  suspend fun count(): Int

  @Insert(onConflict = OnConflictStrategy.ABORT)
  suspend fun insert(entity: LocationFavoriteEntity): Long

  @Query("DELETE FROM favorite_locations WHERE id = :id")
  suspend fun deleteById(id: Long)

  @Query("UPDATE favorite_locations SET label = :label WHERE id = :id")
  suspend fun updateLabel(id: Long, label: String?)

  @Query(
    """
    UPDATE favorite_locations
    SET isDefault = 1
    WHERE id = (SELECT id FROM favorite_locations ORDER BY position ASC, id ASC LIMIT 1)
    """
  )
  suspend fun promoteFirstAsDefault()

  @Transaction
  suspend fun deleteByIdAndPromoteDefault(id: Long) {
    deleteById(id = id)
    promoteFirstAsDefault()
  }

  @RawQuery
  suspend fun runRawUpdate(query: SupportSQLiteQuery): Int

  suspend fun replacePositions(orderedIds: List<Long>) {
    if (orderedIds.isEmpty()) return
    val cases = orderedIds
      .mapIndexed { index, id -> "WHEN $id THEN $index" }
      .joinToString(separator = " ")
    val idsList = orderedIds.joinToString(separator = ",")
    val sql = "UPDATE favorite_locations SET position = CASE id $cases END WHERE id IN ($idsList)"
    runRawUpdate(SimpleSQLiteQuery(sql))
  }
}
