package com.example.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private lateinit var Nombre: TextView
private lateinit var Año: TextView
private lateinit var Autor: TextView
private lateinit var BotonAgregar: Button

class AgregarLibroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)

        BotonAgregar = findViewById(R.id.botonAgregar)

        BotonAgregar.setOnClickListener {
            ValidarCampos()
        }
    }

    private fun ValidarCampos() {

        Nombre = findViewById(R.id.nombreEditText)
        Año = findViewById(R.id.añoEditText)
        Autor = findViewById(R.id.AutorEditText)

        if (Nombre.text.toString().isEmpty() || Año.text.toString().isEmpty() || Autor.text.toString().isEmpty()) {

            Toast.makeText(this, "Faltan Campos", Toast.LENGTH_SHORT).show()

        } else {

            val nombre = Nombre.text.toString()
            val año = Año.text.toString()
            val autor = Autor.text.toString()

            val nuevolibro = Book(nombre, año, autor)
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("NUEVOLIBRO", nuevolibro)
            setResult(RESULT_OK,intent)
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}