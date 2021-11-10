package com.example.todoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.todoapp.repository.TodoAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoAppRepository: TodoAppRepository
): ViewModel() {

    var items = todoAppRepository.getItemsFromDatabase().asLiveData()

}