package com.example.todoapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTodosBinding
import com.example.todoapp.ui.adapter.TodoListRecyclerViewAdapter
import com.example.todoapp.ui.viewModels.MainViewModel
import com.example.todoapp.utils.onTextChangeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.searchQuery.collect {
                    todoListRecyclerViewAdapter.submitList(it)
                }
            }
        }

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.taskbar_menu,menu)
        val searchMenu = menu.findItem(R.id.searchButtonMenu)
        val searchView = searchMenu.actionView as SearchView
        
        searchView.onTextChangeListener {
            mainViewModel.getItemsFromSearchQuery(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       return  when (item.itemId) {
            R.id.sortByName -> {

                true
            }

            R.id.sortByTime -> {


                true
            }
           R.id.hideFinishedTask ->{


               true
           }

           R.id.deleteFinishedTask -> {

               true
           }

            else -> super.onOptionsItemSelected(item)
        }
    }
}