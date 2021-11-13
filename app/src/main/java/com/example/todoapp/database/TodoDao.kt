package com.example.todoapp.database

import androidx.room.*
import com.example.todoapp.data.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoDb(item:Task)

    @Query("delete from todo_task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM todo_task_table WHERE (finished != :hideCompleted OR finished = 0) AND todo LIKE '%' || :searchQuery || '%' ORDER BY important DESC, todo")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query("SELECT * FROM todo_task_table WHERE (finished != :hideCompleted OR finished = 0) AND todo LIKE '%' || :searchQuery || '%' ORDER BY important DESC, created")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>


    @Update
    suspend fun update(task: Task)
}