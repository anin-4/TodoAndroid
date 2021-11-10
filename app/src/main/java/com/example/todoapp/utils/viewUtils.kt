package com.example.todoapp.utils

import android.view.View
import android.widget.CheckBox


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