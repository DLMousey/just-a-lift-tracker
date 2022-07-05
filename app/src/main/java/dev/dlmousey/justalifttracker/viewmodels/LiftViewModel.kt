package dev.dlmousey.justalifttracker.viewmodels

import androidx.lifecycle.*
import dev.dlmousey.justalifttracker.database.models.Lift
import dev.dlmousey.justalifttracker.database.repositories.LiftRepository
import kotlinx.coroutines.launch

class LiftViewModel(private val liftRepository: LiftRepository) : ViewModel() {

    val allLifts: LiveData<List<Lift>> = liftRepository.allLifts.asLiveData()

    fun findById(liftId: Long) = viewModelScope.launch {
        liftRepository.findLiftById(liftId)
    }

    fun insert(lift: Lift) = viewModelScope.launch {
        liftRepository.insert(lift)
    }

    fun delete(lift: Lift) = viewModelScope.launch {
        liftRepository.deleteLift(lift)
    }
}

class LiftViewModelFactory(private val repository: LiftRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiftViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LiftViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}