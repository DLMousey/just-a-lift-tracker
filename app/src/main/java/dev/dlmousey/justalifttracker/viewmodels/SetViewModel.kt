package dev.dlmousey.justalifttracker.viewmodels

import androidx.lifecycle.*
import dev.dlmousey.justalifttracker.database.models.Set
import dev.dlmousey.justalifttracker.database.repositories.SetRepository
import kotlinx.coroutines.launch

class SetViewModel(private val setRepository: SetRepository) : ViewModel() {

    val allSets: LiveData<List<Set>> = setRepository.allSets.asLiveData()

    fun insert(set: Set) = viewModelScope.launch {
        setRepository.insert(set)
    }

    fun delete(set: Set) = viewModelScope.launch {
        setRepository.deleteSet(set)
    }
}

class SetViewModelFactory(private val repository: SetRepository) : ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SetViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}