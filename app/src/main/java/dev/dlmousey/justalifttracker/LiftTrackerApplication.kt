package dev.dlmousey.justalifttracker

import android.app.Application
import dev.dlmousey.justalifttracker.database.LiftTrackerDatabase
import dev.dlmousey.justalifttracker.database.repositories.LiftRepository
import dev.dlmousey.justalifttracker.database.repositories.SetRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LiftTrackerApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { LiftTrackerDatabase.getDatabase(this, applicationScope) }
    val liftRepository by lazy { LiftRepository(database.liftDao()) }
    val setRepository by lazy { SetRepository(database.setDao()) }
}