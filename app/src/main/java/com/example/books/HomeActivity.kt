package com.example.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


private lateinit var ToolbarBooks: Toolbar
private lateinit var BotonAgregarLibro: FloatingActionButton
private lateinit var RecyclerViewBooks: RecyclerView
private var listaLibros = mutableListOf<Book>(
    Book("Harry Potter", "1999", "Jk Rowling")
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

        //Funcion para Modificar el boton de agregar libro
        SetupButoon()
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

    private fun SetupButoon() {
        BotonAgregarLibro = findViewById(R.id.botonAgregarLibro)

        BotonAgregarLibro.setOnClickListener{
            Toast.makeText(this, "Boton Presionado", Toast.LENGTH_SHORT).show()
        }

    }
}