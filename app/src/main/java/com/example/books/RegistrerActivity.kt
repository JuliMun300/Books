package com.example.books

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

private lateinit var NombreTextView: TextView
private lateinit var EmailTextView: TextView
private lateinit var ContraseñaTextView: TextView
private lateinit var BotonRegistro: Button
private lateinit var ImagenUsuario: ImageView
private var currentFile: Uri? = null
private var ImageReference = Firebase.storage.reference

class RegistrerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrer)

        NombreTextView = findViewById(R.id.nombreTextView)
        EmailTextView = findViewById(R.id.emailTextView)
        ContraseñaTextView = findViewById(R.id.contraseñaTextView)
        BotonRegistro = findViewById(R.id.registroBoton)
        ImagenUsuario = findViewById(R.id.imagenusuario)

        ImagenUsuario.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            ImageLauncher.launch(intent)
        }

        BotonRegistro.setOnClickListener {
            val nombre = NombreTextView.text.toString()
            val Email = EmailTextView.text.toString()
            val Contraseña = ContraseñaTextView.text.toString()

            if (nombre.isEmpty() || Email.isEmpty() || Contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, Ingrese los campos", Toast.LENGTH_SHORT).show()
            } else {
                RegistrarUsuario(nombre, Email, Contraseña)
                UpdateImage(currentFile,Email)
            }
        }
    }

    private fun UpdateImage(currentFile: Uri?,Email: String) {

        if (currentFile != null) {
            ImageReference.child("images/$Email").putFile(currentFile).addOnCompleteListener {
                Toast.makeText(this, "imagen Agregada", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Hubo un error", Toast.LENGTH_SHORT).show()
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

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private val ImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.data.let {
                    currentFile = it
                    ImagenUsuario.setImageURI(it)
                }
            }
        }
}