package com.example.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.Task


@Database(entities = [Task::class],version=1)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao():TodoDao
}