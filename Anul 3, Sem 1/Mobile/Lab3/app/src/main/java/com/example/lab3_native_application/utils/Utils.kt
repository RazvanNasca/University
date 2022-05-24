package com.example.lab3_native_application.utils

import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun dateToUiString(
        date: Date
    ) : String{
        return try {
            val dateFormat = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            simpleDateFormat.format(date)
        } catch (exception: Exception){
            Log.d("error", "", exception)
            ""
        }
    }

    fun stringToDate(
        date: String
    ) : Date {
        return try {
            val dateFormat = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            simpleDateFormat.parse(date)
        } catch (exception: Exception) {
            Log.d("error", "", exception)
            Date()
        }
    }

}