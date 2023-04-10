package com.example.books

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter(var Libros: List<Book>) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {


    fun setfilteredList(libros: List<Book>) {
        this.Libros = libros
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_book, parent, false))
    }

    override fun onBindViewHolder(holder: BooksAdapter.ViewHolder, position: Int) {
        val item = Libros.get(position)
        holder.render(item)

    }

    override fun getItemCount(): Int {
        return Libros.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val NombreTextView: TextView = view.findViewById(R.id.titulo)
        val AñoTextView: TextView = view.findViewById(R.id.año)
        val AutorTextView: TextView = view.findViewById(R.id.autor)


        fun render(libro: Book) {
            NombreTextView.text = libro.Titulo
            AñoTextView.text = libro.Año
            AutorTextView.text = libro.Autor

        }

    }

}