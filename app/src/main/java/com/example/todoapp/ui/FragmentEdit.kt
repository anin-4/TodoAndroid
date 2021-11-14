package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.FragmentEditBinding
import com.example.todoapp.ui.viewModels.MainViewModel
import com.example.todoapp.utils.Constants.EDIT_MODE
import com.example.todoapp.utils.Constants.NEW_MODE
import com.example.todoapp.utils.displaySnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentEdit: Fragment() {
    private lateinit var binding:FragmentEditBinding
    private val args:FragmentEditArgs by navArgs()
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentEditBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = args.task
        val type = args.type

        binding.apply {
            todoInput.setText(item?.todo ?: "")
            importantCheckBox.isChecked=item?.important?: false
            dateTextView.text= item?.createdDateTimeFormatted
        }

        binding.apply{
            addItem.setOnClickListener {
                if(todoInput.text.isNotEmpty()){
                    if(type==NEW_MODE)
                        mainViewModel.saveIntoDataBase(Task(todoInput.text.toString(),important = importantCheckBox.isChecked))
                    else if(type==EDIT_MODE)
                        mainViewModel.updateTask(item?.copy(todo=todoInput.text.toString(),important = importantCheckBox.isChecked))
                    findNavController().navigate(R.id.action_fragmentEdit_to_fragmentTodos)
                }
                else{
                    displaySnackBar(view,"Task Cannot be empty")
                }
            }
        }
    }
}