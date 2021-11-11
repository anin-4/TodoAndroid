package com.example.todoapp.utils

import android.view.View
import android.widget.CheckBox
import androidx.appcompat.widget.SearchView


fun View.invisible(){
    this.visibility=View.INVISIBLE
}

fun View.visible(){
    visibility= View.VISIBLE
}

fun View.gone(){
    visibility=View.GONE
}

fun CheckBox.check(){
    isChecked=true
}

fun CheckBox.unCheck(){
    isChecked=false
}

inline fun SearchView.onTextChangeListener(crossinline text:(String)->Unit){
    this.setOnQueryTextListener(
        object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                text(newText.orEmpty())
                return true
            }

        }
    )
}