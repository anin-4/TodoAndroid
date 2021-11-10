package com.example.todoapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat


@Entity(tableName = "todo_task_table")
@Parcelize
data class Task(
    val todo:String ="",
    val finished:Boolean=false,
    val important:Boolean=false,
    val created:Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
): Parcelable {

    val createdDateTimeFormatted:String
        get() = DateFormat.getDateInstance().format(created)
}
