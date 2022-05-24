package com.example.examenma.ownerSectionScreen.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entities_table")
data class Entity(
    @PrimaryKey(autoGenerate = false) var id: Int? = null,
    var nume: String,
    var medie1: Int,
    var medie2: Int,
    var medieAll: Int? = null,
    var status: Boolean? = null,
    var isUploaded: Boolean? = null
)