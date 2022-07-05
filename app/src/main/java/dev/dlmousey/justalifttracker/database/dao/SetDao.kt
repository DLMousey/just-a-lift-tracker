package dev.dlmousey.justalifttracker.database.dao

import androidx.room.*
import dev.dlmousey.justalifttracker.database.models.Set
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface SetDao {

    @Query("SELECT * FROM sets_table")
    fun getSetsWithoutLift(): Flow<List<Set>>

    @Query("SELECT * FROM sets_table WHERE setId=:id")
    fun getSetWithoutLift(id: Long): Flow<Set>

    @Query("SELECT * FROM sets_table s INNER JOIN lifts_table l on l.liftId = s.liftId WHERE date=:date")
    fun getSetsByDate(date: Date?): Flow<List<Set>>

    @Query("SELECT * FROM sets_table s INNER JOIN lifts_table l on l.liftId = s.liftId ORDER BY date DESC")
    fun getSets(): Flow<List<Set>>

    @Query("SELECT * FROM sets_table s INNER JOIN lifts_table l on l.liftId = s.liftId WHERE setId=:id")
    fun getSet(id: Long): Flow<Set>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(set: Set): Long

    @Delete
    fun deleteSet(set: Set)
}