package com.example.todoapp.utils

import android.view.View
import android.widget.CheckBox
import androidx.appcompat.widget.SearchView
import com.google.android.material.snackbar.Snackbar




fun View.visible(){
    visibility= View.VISIBLE
}

fun CheckBox.check(){
    isChecked=true
}

fun CheckBox.unCheck(){
    isChecked=false
}

fun displaySnackBar(view: View,msg:String){
    Snackbar.make(view,msg,Snackbar.LENGTH_LONG).show()
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