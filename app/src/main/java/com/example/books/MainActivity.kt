package com.example.books

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

private lateinit var TextoTitulo: TextView
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
        TextoTitulo = findViewById(R.id.textViewTitulo)
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
            val intent = Intent(this, RegistrerActivity::class.java)
            startActivity(intent)
        }
        RemoteConfig()
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
                    ShowAlert("El usuario que ingreso no existe")
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
    fun ShowAlert(Mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(Mensaje)
        builder.show()
    }

    //Funcion para configurar de forma remota las variables desde Firebase
    private fun RemoteConfig() {

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }
        val FirebaseConfig = Firebase.remoteConfig
        FirebaseConfig.setConfigSettingsAsync(configSettings)
        FirebaseConfig.setDefaultsAsync(
            mapOf(
                "Show_Create_Button" to true,
                "Change_Title_Login" to "Books"
            )
        )

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val showCreateUserButton = Firebase.remoteConfig.getBoolean("Show_Create_Button")
                val ChangeTitle = Firebase.remoteConfig.getString("Change_Title_Login")

                if (showCreateUserButton) {
                    BotonCrearUsuario.visibility = View.VISIBLE
                }
                TextoTitulo.text = ChangeTitle
            }
        }
    }
}