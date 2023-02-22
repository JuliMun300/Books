package com.example.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar


private lateinit var ToolbarBooks:Toolbar


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Instanciamos las variables
        ToolbarBooks = findViewById(R.id.Toolbar)

        //Funcion Para Modificar el toolbar
        SetupToolbar()
    }

    private fun SetupToolbar() {

        setSupportActionBar(ToolbarBooks)
    }
}