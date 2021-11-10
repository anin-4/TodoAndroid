package com.example.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.FragmentTodosBinding
import com.example.todoapp.ui.adapter.TodoListRecyclerViewAdapter
import com.example.todoapp.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentTodos : Fragment() {

    private  lateinit var binding:FragmentTodosBinding
    private val todoListRecyclerViewAdapter:TodoListRecyclerViewAdapter = TodoListRecyclerViewAdapter()
    private val mainViewModel:MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentTodosBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.todoItemsRv.apply {
            adapter = todoListRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        mainViewModel.items.observe(viewLifecycleOwner, {
            todoListRecyclerViewAdapter.submitList(it)
        })


    }
}