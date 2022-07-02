package dev.dlmousey.justalifttracker.database.repositories

import androidx.annotation.WorkerThread
import dev.dlmousey.justalifttracker.database.dao.LiftDao
import dev.dlmousey.justalifttracker.database.models.Lift
import kotlinx.coroutines.flow.Flow

class LiftRepository(private val liftDao: LiftDao) {

    val allLifts: Flow<List<Lift>> = liftDao.getAllLifts()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(lift: Lift) {
        liftDao.insert(lift)
    }

    fun findLiftById(liftId: Long): Flow<Lift> {
        return liftDao.getLift(liftId)
    }

    fun deleteLift(lift: Lift) {
        return liftDao.deleteLift(lift)
    }

    companion object {
        @Volatile private var instance: LiftRepository? = null

        fun getInstance(liftDao: LiftDao) =
            instance ?: synchronized(this) {
                instance ?: LiftRepository(liftDao).also { instance = it }
            }
    }
}