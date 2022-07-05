package dev.dlmousey.justalifttracker.viewmodels

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import dev.dlmousey.justalifttracker.database.models.Lift
import dev.dlmousey.justalifttracker.database.models.Set
import dev.dlmousey.justalifttracker.database.repositories.LiftRepository
import dev.dlmousey.justalifttracker.database.repositories.SetRepository
import kotlinx.coroutines.launch
import java.util.*

class SetViewModel(private val setRepository: SetRepository) : ViewModel() {

    val allSets: LiveData<List<Set>> = setRepository.allSets.asLiveData()

    fun insert(set: Set) = viewModelScope.launch {
        setRepository.insert(set)
    }

    fun delete(set: Set) = viewModelScope.launch {
        setRepository.deleteSet(set)
    }

//    fun create(liftId: Long, reps: Int, weight: Int, rpe: Int, setNumber: Int, owner: LifecycleOwner): Set {
//        var lift: Lift? = null
////        liftRepository.findLiftById(liftId).asLiveData().observe(owner, Observer { result ->
////            lift = result
////        })
//
//        return Set(0, reps, weight, rpe, setNumber, Date().time, lift!!)
//    }
}

class SetViewModelFactory(
    private val setRepository: SetRepository
) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SetViewModel(setRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}