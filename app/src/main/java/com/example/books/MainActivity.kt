package com.example.books

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

private lateinit var TextoEmail: TextView
private lateinit var TextoContraseña: TextView
private lateinit var BotonIngresar: Button
private lateinit var BotonCrearUsuario: Button
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
        BotonCrearUsuario = findViewById(R.id.botonCrearUsuario)
        Prefs = getSharedPreferences("Preferencias", MODE_PRIVATE)

        //Agregamos el boton y cuando se presione se validan los campos
        BotonIngresar.setOnClickListener {
            ValidarCampos()
        }

        //Agregamos el boton cuando se presione para ejecutar el registro del usuario
        BotonCrearUsuario.setOnClickListener {
            RegistroCampos()
        }
    }

    //funcion para validar campos
    private fun ValidarCampos() {
        if (TextoEmail.text.toString().isEmpty() || TextoContraseña.text.toString().isEmpty()) {
            Toast.makeText(this, "Ingresa los campos", Toast.LENGTH_SHORT).show()
        } else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                TextoEmail.text.toString(),
                TextoContraseña.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    ShowAlert()
                }
            }
        }
    }

    //Funcion para registrar el usuario
    private fun RegistroCampos() {
        if (TextoEmail.text.toString().isEmpty() || TextoContraseña.text.toString().isEmpty()) {
            ShowAlert()
        } else {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                TextoEmail.text.toString(),
                TextoContraseña.text.toString()
            ).addOnCompleteListener { task ->
                if (task.isComplete) {
                    Toast.makeText(this, "Usuario Creado", Toast.LENGTH_SHORT).show()
                } else {
                    ShowAlert()
                }
            }
        }
    }

    //Funcion para guardar datos cada vez que se el usuario presione Recordad Usuario
    fun GuardarDatos(nombre: String, Pass: String) {
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

    //Funcion para mostrar un mensaje de error
    fun ShowAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("A ocurrido un error")
        builder.show()
    }
}