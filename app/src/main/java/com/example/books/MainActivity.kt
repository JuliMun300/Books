package com.example.books

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private lateinit var TextoEmail: TextView
private lateinit var TextoContraseña: TextView
private lateinit var BotonIngresar: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instanciamos las vistas
        TextoEmail = findViewById(R.id.EmailTextView)
        TextoContraseña = findViewById(R.id.ContraseñaTextField)
        BotonIngresar = findViewById(R.id.botonIngresar)


        //Agregamos el boton y cuando se presione se validan los campos
        BotonIngresar.setOnClickListener {
            ValidarCampos()
        }


    }

    //funcion para validar campos
    private fun ValidarCampos() {
        if (TextoEmail.text.toString().isEmpty() || TextoContraseña.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingresa los campos", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}