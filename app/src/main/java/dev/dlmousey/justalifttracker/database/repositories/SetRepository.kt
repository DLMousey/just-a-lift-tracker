package dev.dlmousey.justalifttracker.database.repositories

import androidx.annotation.WorkerThread
import dev.dlmousey.justalifttracker.database.dao.SetDao
import dev.dlmousey.justalifttracker.database.models.Set
import kotlinx.coroutines.flow.Flow

class SetRepository(private val setDao: SetDao) {

    val allSets: Flow<List<Set>> = setDao.getSets()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(set: Set) {
        setDao.insert(set)
    }

    fun findSetById(setId: Long): Flow<Set> {
        return setDao.getSet(setId)
    }

    fun findSetWithoutLiftById(setId: Long): Flow<Set> {
        return setDao.getSetWithoutLift(setId)
    }

    fun deleteSet(set: Set) {
        return setDao.deleteSet(set)
    }

    companion object {
        @Volatile private var instance: SetRepository? = null

        fun getInstance(setDao: SetDao) =
            instance ?: synchronized(this) {
                instance ?: SetRepository(setDao).also { instance = it }
            }
    }
}