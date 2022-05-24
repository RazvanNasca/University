package com.example.examenma.utils

import android.util.Log
import androidx.room.TypeConverter
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class Converter {

    @TypeConverter
    fun dateToMilliseconds(
        date: Date
    ): Long {
        return try {
            date.time
        } catch (exception: Exception) {
            Log.d("error", "", exception)
            0L
        }
    }

    @TypeConverter
    fun millisecondsToDate(
        date: Long
    ): Date {
        return try {
            val dateFormat = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val dateString = simpleDateFormat.format(calendar.time)
            simpleDateFormat.parse(dateString)
        } catch (exception: Exception) {
            Log.d("error", "", exception)
            Date()
        }
    }
}