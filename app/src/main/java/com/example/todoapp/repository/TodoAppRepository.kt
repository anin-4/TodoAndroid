package com.example.todoapp.repository

import com.example.todoapp.data.Task
import com.example.todoapp.database.TodoDao
import com.example.todoapp.ui.viewModels.SortOrder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoAppRepository @Inject constructor(
    private val todoDao: TodoDao
) {

    fun getTasks(query: String,sortOrder: SortOrder,hideCompleted:Boolean): Flow<List<Task>> {
        return when(sortOrder){
            SortOrder.BY_DATE->todoDao.getTasksSortedByDateCreated(query,hideCompleted)
            SortOrder.BY_NAME->todoDao.getTasksSortedByName(query,hideCompleted)
        }
    }

    suspend fun updateTask(item:Task, selected:Boolean){
        todoDao.update(item.copy(finished = selected))
    }

    suspend fun updateTaskEdit(item:Task){
        todoDao.update(item)
    }

    suspend fun deleteTask(item: Task){
        todoDao.delete(item.id)
    }

    suspend fun insertTodo(item:Task){
        todoDao.insertIntoDb(item)
    }
}