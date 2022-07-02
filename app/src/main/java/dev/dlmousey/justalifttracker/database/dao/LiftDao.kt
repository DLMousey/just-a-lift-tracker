package dev.dlmousey.justalifttracker.database.dao

import androidx.room.*
import dev.dlmousey.justalifttracker.database.models.Lift
import kotlinx.coroutines.flow.Flow


@Dao
interface LiftDao {

    @Query("SELECT * FROM lifts_table")
    fun getAllLifts(): Flow<List<Lift>>

    @Query("SELECT * FROM lifts_table WHERE liftId=:id")
    fun getLift(id: Long): Flow<Lift>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(lift: Lift): Long

    @Delete
    fun deleteLift(lift: Lift)
}