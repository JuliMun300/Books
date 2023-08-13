package com.example.books

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private lateinit var NombreTextView: TextView
private lateinit var EmailTextView: TextView
private lateinit var ContraseñaTextView: TextView
private lateinit var BotonRegistro: Button

class RegistrerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrer)

        NombreTextView = findViewById(R.id.nombreTextView)
        EmailTextView = findViewById(R.id.emailTextView)
        ContraseñaTextView = findViewById(R.id.contraseñaTextView)
        BotonRegistro = findViewById(R.id.registroBoton)

        BotonRegistro.setOnClickListener {
            val nombre = NombreTextView.text.toString()
            val Email = EmailTextView.text.toString()
            val Contraseña = ContraseñaTextView.text.toString()

            if (nombre.isEmpty() || Email.isEmpty() || Contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, Ingrese los campos", Toast.LENGTH_SHORT).show()
            } else {
                RegistrarUsuario(nombre, Email, Contraseña)
            }
        }
    }

    private fun RegistrarUsuario(nombre: String, email: String, contraseña: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email,
            contraseña
        ).addOnCompleteListener { task ->
            if (task.isComplete) {
                Toast.makeText(this, "Usuario Creado", Toast.LENGTH_SHORT).show()

                val db = Firebase.firestore

                val user = hashMapOf(
                    "Nombre" to nombre,
                    "Email" to email,
                    "Contraseña" to contraseña
                )

                db.collection("Users").document(nombre).set(user)

                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
    }
}