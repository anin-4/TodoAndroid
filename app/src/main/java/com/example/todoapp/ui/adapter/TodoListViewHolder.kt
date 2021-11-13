package com.example.todoapp.ui.adapter


import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.TodoItemBinding
import com.example.todoapp.utils.check
import com.example.todoapp.utils.unCheck
import com.example.todoapp.utils.visible

class TodoListViewHolder(private val binding:TodoItemBinding):RecyclerView.ViewHolder(binding.root) {

    var onClickHandler:((item:Task, selected:Boolean,case:ClickHandlerType)->Unit)?= null
    fun bind(item: Task){
        binding.apply {
            todoTextView.text=item.todo
            if(item.finished) {
                todoCheckBox.check()
                todoTextView.paint.isStrikeThruText=true
            }
            else{
                todoCheckBox.unCheck()
            }
            if(item.important) priorityIndicator.visible()

            todoCheckBox.setOnClickListener {
                val position = adapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    onClickHandler?.invoke(item,todoCheckBox.isChecked,ClickHandlerType.CHECK_BOX)
                }
            }

            root.setOnClickListener {
                onClickHandler?.invoke(item,todoCheckBox.isChecked,ClickHandlerType.NAVIGATE_EDIT)
            }
        }

    }
}

enum class ClickHandlerType{
    CHECK_BOX,
    NAVIGATE_EDIT
}