package com.example.exam2.ownerSectionScreen.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import app.exam.utils.Converter
import java.lang.IllegalStateException
import java.util.*

@Entity(tableName = "products_table")
data class Product(
    @PrimaryKey(autoGenerate = false) var id: Int? = null,
    var nume: String,
    var tip: String,
    var cantitate: Int,
    var pret: Int,
    var discount: Int,
    var status: Boolean? = null,
    var isUploaded: Boolean? = null
)