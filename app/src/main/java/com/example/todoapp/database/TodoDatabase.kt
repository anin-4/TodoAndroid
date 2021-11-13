package com.example.todoapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todoapp.data.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


@Database(entities = [Task::class],version=1)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao():TodoDao

    class CallBack @Inject constructor(
        private val todoDatabase: Provider<TodoDatabase>,
        private val scope: CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao =todoDatabase.get().todoDao()

            scope.launch {
                dao.insertIntoDb(Task("call ma",finished = true))
                dao.insertIntoDb(Task("call ankit", important = true,finished = true))
                dao.insertIntoDb(Task("bring dinner"))
                dao.insertIntoDb(Task("order lunch",important = true))
                dao.insertIntoDb(Task("watch dune"))
                dao.insertIntoDb(Task("sleep"))
            }

        }
    }
}