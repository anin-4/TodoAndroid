package com.example.todoapp.repository

import com.example.todoapp.database.TodoDao
import javax.inject.Inject

class TodoAppRepository @Inject constructor(
    private val todoDao: TodoDao
) {
    fun getItemsFromDatabase() = todoDao.getItemsOrderedByTime()

    fun getItemsFromQuery(query:String) = todoDao.getItemsBasedOnQuery(query)
}