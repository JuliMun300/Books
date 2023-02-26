package com.example.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private lateinit var ToolbarBooks: Toolbar
private lateinit var RecyclerViewBooks:RecyclerView
private var listaLibros = mutableListOf<Book>(
    Book("Harry Potter","1999","Jk Rowling")
)


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Instanciamos las variables
        ToolbarBooks = findViewById(R.id.Toolbar)

        //Funcion Para Modificar el toolbar
        SetupToolbar()

        //Funcion para Modificar el Recycler View
        SetupRecyclerView()
    }

    private fun SetupToolbar() {

        setSupportActionBar(ToolbarBooks)
    }

    private fun SetupRecyclerView() {

        RecyclerViewBooks = findViewById(R.id.recyclerviewbooks)
        RecyclerViewBooks.setHasFixedSize(true)
        RecyclerViewBooks.layoutManager = LinearLayoutManager(this)

        val adapter = BooksAdapter(listaLibros)
        RecyclerViewBooks.adapter = adapter



    }

}