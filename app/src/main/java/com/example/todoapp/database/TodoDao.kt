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

    @Query("select * from todo_task_table order by created")
    fun getItemsOrderedByTime(): Flow<List<Task>>

    @Query("select * from todo_task_table where todo like '%' || :query || '%' order by important desc")
    fun getItemsBasedOnQuery(query:String):Flow<List<Task>>

    @Update
    fun update(task: Task)
}