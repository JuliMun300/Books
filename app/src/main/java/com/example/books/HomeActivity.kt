package com.example.books

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


private lateinit var ToolbarBooks: Toolbar
private lateinit var BotonAgregarLibro: FloatingActionButton
private lateinit var RecyclerViewBooks: RecyclerView
private lateinit var SearchViewBuscar: SearchView
private var listaLibros = mutableListOf<Book>(
    Book("Harry Potter", "1999", "Jk Rowling"),
    Book("El señor de los anillos", "1954", "J. R. R. Tolkien"),
    Book("El Aleph", "1949", "Jorge Luis Borges"),
    Book("Madame Bovary", "1857", "Gustave Flaubert"),
    Book("La guerra y la paz", "1869", " León Tolstoi")

)
val adapter = BooksAdapter(listaLibros)

class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Instanciamos las variables
        ToolbarBooks = findViewById(R.id.Toolbar)
        SearchViewBuscar = findViewById(R.id.searchViewbuscar)
        SearchViewBuscar.setOnQueryTextListener(this)

        //Funcion Para Modificar el toolbar
        SetupToolbar()

        //Funcion para Modificar el Recycler View
        SetupRecyclerView()

        //Funcion para Modificar el boton de agregar libro
        Setupbutton()

        //Funcion para Notificar que se Agrego un nuevo Libro
        //SetupNewBooks()
    }

    private fun SetupNewBooks() {
        val nuevolibro = intent.getParcelableExtra<Book>("NUEVOLIBRO")

        if (nuevolibro != null) {
            //funcion para agregar el nuevo libro en la lista
            AgregarNuevoLibro(nuevolibro)
        }
    }

    private fun AgregarNuevoLibro(nuevolibro: Book) {

        listaLibros.add(listaLibros.size, nuevolibro)
        adapter.notifyDataSetChanged()
        Snackbar.make(constraintlayouthome,"Libro agregado con exito",Snackbar.LENGTH_SHORT).show()
    }


    private fun SetupToolbar() {
        setSupportActionBar(ToolbarBooks)
    }

    private fun SetupRecyclerView() {
        RecyclerViewBooks = findViewById(R.id.recyclerviewbooks)
        RecyclerViewBooks.setHasFixedSize(true)
        RecyclerViewBooks.layoutManager = LinearLayoutManager(this)
        RecyclerViewBooks.adapter = adapter
    }

    private fun Setupbutton() {
        BotonAgregarLibro = findViewById(R.id.botonAgregarLibro)

        BotonAgregarLibro.setOnClickListener {
            val intent = Intent(this, AgregarLibroActivity::class.java)
            BookLauncher.launch(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        ListaFiltrada(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        ListaFiltrada(newText)
        return true
    }

    //Funcion para cuando se busca un item aparezca en la lista
    private fun ListaFiltrada(query: String?) {
        if (query != null) {
            val filteredList = mutableListOf<Book>()
            for (i in listaLibros) {
                if (i.Titulo.lowercase(Locale.ROOT).contains(query.toString().lowercase())) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setfilteredList(filteredList)
            }
        }
    }
    private val BookLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK){
            val nuevolibro = result.data?.getParcelableExtra<Book>("NUEVOLIBRO")
            if (nuevolibro != null) {
                listaLibros.add(listaLibros.size, nuevolibro)
                adapter.notifyItemInserted(listaLibros.size + 1)
                Snackbar.make(constraintlayouthome,"Libro agregado con exito",Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}

