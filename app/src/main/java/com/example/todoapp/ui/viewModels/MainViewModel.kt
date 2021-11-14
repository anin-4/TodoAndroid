package com.example.todoapp.ui.viewModels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.PreferenceManager
import com.example.todoapp.data.Task
import com.example.todoapp.repository.TodoAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoAppRepository: TodoAppRepository,
    private val preferenceManager: PreferenceManager
): ViewModel() {
    private var _searchQuery =  MutableStateFlow("")

    var preferenceFlow = preferenceManager.preferenceFlow

    @ExperimentalCoroutinesApi
    private var modifiedTasks = combine(
        _searchQuery,
        preferenceFlow
    ) { query, filterPreference->
        Pair(query, filterPreference)
    }.flatMapLatest {
        todoAppRepository.getTasks(it.first,it.second.sortOrder,it.second.hideCompleted)
    }

    @ExperimentalCoroutinesApi
    var tasks = modifiedTasks.asLiveData()


    fun sortItemBySortingOrder(sortOrder: SortOrder)=viewModelScope.launch {
        preferenceManager.updateSortOrder(sortOrder)
    }

    fun hideCompletedItems(okay:Boolean) = viewModelScope.launch {
            preferenceManager.updateHideCompleted(okay)
        }


    fun changeQuery(query:String){
        _searchQuery.value=query
    }

    fun handleClick(item: Task, selected: Boolean) = viewModelScope.launch {
            todoAppRepository.updateTask(item,selected)
    }

    fun onTaskSwiped(item: Task?) = viewModelScope.launch {
        item?.let {
            todoAppRepository.deleteTask(item)
        }
    }

    fun saveIntoDataBase(item:Task) = viewModelScope.launch {
        todoAppRepository.insertTodo(item)
    }

    fun updateTask(item:Task?) = viewModelScope.launch {
        item?.let {
            todoAppRepository.updateTaskEdit(item)
        }

    }

    fun deleteAllTask()= viewModelScope.launch {
        todoAppRepository.deleteAll()
    }


}

enum class SortOrder{
    BY_NAME,
    BY_DATE
}