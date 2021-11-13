package com.example.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.TodoItemBinding

class TodoListRecyclerViewAdapter: ListAdapter<Task, TodoListViewHolder>(DiffCallBack()){

    var onClickHandler:((item:Task,selected:Boolean,case:ClickHandlerType)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val binding= TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return TodoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.onClickHandler=onClickHandler
        val currentItem= getItem(position)
        holder.bind(currentItem)
    }

    class DiffCallBack: DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem


    }
}