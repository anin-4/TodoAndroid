package com.example.todoapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.Task
import com.example.todoapp.databinding.FragmentTodosBinding
import com.example.todoapp.ui.adapter.ClickHandlerType
import com.example.todoapp.ui.adapter.TodoListRecyclerViewAdapter
import com.example.todoapp.ui.viewModels.MainViewModel
import com.example.todoapp.ui.viewModels.SortOrder
import com.example.todoapp.utils.Constants.EDIT_MODE
import com.example.todoapp.utils.Constants.NEW_MODE
import com.example.todoapp.utils.onTextChangeListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.todoItemsRv.apply {
            adapter = todoListRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        mainViewModel.tasks.observe(viewLifecycleOwner,{
            todoListRecyclerViewAdapter.submitList(it)
        })

        todoListRecyclerViewAdapter.onClickHandler ={item:Task, selected:Boolean,case:ClickHandlerType ->
            when(case){
                ClickHandlerType.NAVIGATE_EDIT -> {
                    val bundle = Bundle().apply {
                        putParcelable("task",item)
                        putInt("type", EDIT_MODE)
                    }
                    findNavController().navigate(R.id.action_fragmentTodos_to_fragmentEdit,bundle)
                }
                ClickHandlerType.CHECK_BOX ->{
                    mainViewModel.handleClick(item, selected)
                }
            }
        }

        binding.addFloatingButton.setOnClickListener {
            val bundle =Bundle().apply {
                putInt("type", NEW_MODE)
            }
            findNavController().navigate(R.id.action_fragmentTodos_to_fragmentEdit,bundle)
        }

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = todoListRecyclerViewAdapter.currentList[viewHolder.adapterPosition]
                mainViewModel.onTaskSwiped(item)
            }

        }).attachToRecyclerView(binding.todoItemsRv)

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.taskbar_menu,menu)
        val searchMenu = menu.findItem(R.id.searchButtonMenu)
        val searchView = searchMenu.actionView as SearchView
        
        searchView.onTextChangeListener {
            mainViewModel.changeQuery(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.hideFinishedTask).isChecked=
            mainViewModel.preferenceFlow.first().hideCompleted
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return  when (item.itemId) {
            R.id.sortByName -> {
                mainViewModel.sortItemBySortingOrder(SortOrder.BY_NAME)
                true
            }

            R.id.sortByTime -> {
                mainViewModel.sortItemBySortingOrder(SortOrder.BY_DATE)
                true
            }
           R.id.hideFinishedTask ->{
               item.isChecked=!item.isChecked
               mainViewModel.hideCompletedItems(item.isChecked)
               true
           }

           R.id.deleteFinishedTask -> {
               true
           }

            else -> super.onOptionsItemSelected(item)
        }
    }
}