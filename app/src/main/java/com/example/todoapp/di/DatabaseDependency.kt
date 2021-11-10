package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.database.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseDependency {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context:Context
    )= Room.databaseBuilder(context,TodoDatabase::class.java,"todo_db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(
        todoDatabase: TodoDatabase
    ) = todoDatabase.todoDao()
}