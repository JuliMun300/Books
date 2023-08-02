package com.example.books

import android.util.Log.i
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class HomeActivityTest{



    private var Book1= Book("Harry Potter", "1999", "Jk Rowling")
    
    private var listaLibros = mutableListOf<Book>( Book("Harry Potter", "1999", "Jk Rowling"),
        Book("El señor de los anillos", "1954", "J. R. R. Tolkien"))


@Test
fun whenTheBooksAreTheSame(){

    val Book2 = Book("Harry Potter", "1999", "Jk Rowling")

    Assert.assertEquals(Book1,Book2)
}

    @Test
    fun whenTheListAreNotEmpty(){

    if(listaLibros.isEmpty()){
        Assert.assertFalse(true)
    }
}

}