package com.example.books

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class Book(
    val Titulo: String,
    val Año: String,
    val Autor: String
):Parcelable