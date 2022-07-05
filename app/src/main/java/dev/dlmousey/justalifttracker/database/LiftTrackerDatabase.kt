package dev.dlmousey.justalifttracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.dlmousey.justalifttracker.database.converters.DateConverter
import dev.dlmousey.justalifttracker.database.dao.LiftDao
import dev.dlmousey.justalifttracker.database.dao.SetDao
import dev.dlmousey.justalifttracker.database.models.Lift
import dev.dlmousey.justalifttracker.database.models.Set
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Set::class, Lift::class], version = 4, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class LiftTrackerDatabase : RoomDatabase() {

    abstract fun setDao(): SetDao
    abstract fun liftDao(): LiftDao

    companion object {
        @Volatile
        private var INSTANCE: LiftTrackerDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): LiftTrackerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LiftTrackerDatabase::class.java,
                    "lift_tracker_database"
                ).fallbackToDestructiveMigration() // BAD - use migrations properly for prod
                    .allowMainThreadQueries() // BAD - remove if not needed
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}