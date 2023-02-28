package com.example.books

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AgregarLibroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}