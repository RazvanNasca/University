package com.example.exam3.ownerSectionScreen.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rezervare_table")
data class Rezervare(
    @PrimaryKey(autoGenerate = false) var id: Int? = null,
    var nume: String,
    var doctor: String,
    var data: Int,
    var ora: Int,
    var detalii: String,
    var status: Boolean? = null,
    var isUploaded: Boolean? = null
)