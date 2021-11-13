package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.todoapp.databinding.FragmentEditBinding

class FragmentEdit: Fragment() {
    private lateinit var binding:FragmentEditBinding
    private val args:FragmentEditArgs by navArgs()
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

        binding.apply {
            todoInput.setText(item?.todo ?: "")
            importantCheckBox.isChecked=item?.finished?: false
            dateTextView.text= item?.createdDateTimeFormatted
        }
    }
}