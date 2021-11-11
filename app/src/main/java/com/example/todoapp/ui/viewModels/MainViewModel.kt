package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Task
import com.example.todoapp.repository.TodoAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoAppRepository: TodoAppRepository
): ViewModel() {

    private var _searchQuery = MutableStateFlow<List<Task>>(emptyList())
    val searchQuery:StateFlow<List<Task>>
        get() = _searchQuery


    init {
        viewModelScope.launch {
            todoAppRepository.getItemsFromDatabase().collect{
                _searchQuery.value =it
            }
        }
    }

    fun getItemsFromSearchQuery(query:String){
        viewModelScope.launch {
            todoAppRepository.getItemsFromQuery(query).collect {
                _searchQuery.value=it
            }
        }
    }

}