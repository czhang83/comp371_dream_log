package com.example.dreamlog

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DreamViewModel (private val repository: DreamRepository) : ViewModel(){
    val allDreams : LiveData<List<Dream>> = repository.allDreams.asLiveData()

    // make sure that view model is running in its own scope
    // in the viewModel library, it has its own scope - viewModelScope
    // want to launch a new coroutine to run each of the suspend function from repository
    // to use viewModelScope allows everything to run based on their lifecycles

    fun insert(dream: Dream) = viewModelScope.launch {
        repository.insert(dream)
    }


    fun update(id:Int, title:String, content:String, reflection:String, emotion:String) = viewModelScope.launch {
        repository.update(id, title, content, reflection, emotion)
    }

    fun delete (id:Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun getDream (id: Int) : LiveData<Dream> {
        return repository.getDream(id).asLiveData()
    }

}

class DreamViewModelFactory(private val repository: DreamRepository) : ViewModelProvider.Factory{
    // override the create method to return the TaskViewModel
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DreamViewModel::class.java)){
            return DreamViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}