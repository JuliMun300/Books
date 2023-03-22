package com.example.books

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

private lateinit var TextoEmail: TextView
private lateinit var TextoContraseña: TextView
private lateinit var BotonIngresar: Button
private lateinit var CheckboxGuardar: CheckBox
private lateinit var Prefs: SharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instanciamos las vistas
        TextoEmail = findViewById(R.id.EmailTextView)
        TextoContraseña = findViewById(R.id.ContraseñaTextField)
        BotonIngresar = findViewById(R.id.botonIngresar)
        CheckboxGuardar = findViewById(R.id.checkBoxguardar)
        Prefs = getSharedPreferences("Preferencias", MODE_PRIVATE)

        CargarDatos()

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
            finish()
        }

        if (CheckboxGuardar.isChecked) {
            GuardarDatos(TextoEmail.text.toString(), TextoContraseña.text.toString())
        }
    }

    //Funcion para guardar datos cada vez que se el usuario presione Recordad Usuario
    private fun GuardarDatos(nombre: String, Pass: String) {
        val editor = Prefs.edit()
        editor.putString("nombre", nombre)
        editor.putString("pass", Pass)
        editor.commit()
    }

    //Funcion para cada vez que abra la aplicacion cargue el usuario ingresado antes
    private fun CargarDatos() {
        val nombreGuardado = Prefs.getString("nombre", "")
        val contraseñaGuardada = Prefs.getString("pass", "")

        if (nombreGuardado != null) {
            if (!nombreGuardado.isEmpty())

                TextoEmail.text = nombreGuardado
        }
        if (contraseñaGuardada != null) {
            if (!contraseñaGuardada.isEmpty()) {

                TextoContraseña.text = contraseñaGuardada
            }
        }
    }
}
