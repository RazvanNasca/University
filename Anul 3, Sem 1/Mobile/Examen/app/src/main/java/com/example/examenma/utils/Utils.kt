package com.example.examenma.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

import kotlinx.serialization.Serializable


object Utils {
    /*
     * Returneaza culoarea fundalului al unui produs in functie de data de expirare
     */
//    fun BackgroundColorByStatus(
//        status: TableStatus
//    ): Color {
//        return if (status.equals(TableStatus.USED))
//            Color(0xFFFFA8A8)
//        else {
//            Color(0xFFC1E1C1)
//        }
//    }

    /**
     * Transforma milisecunde intr-o data
     */
    fun millisecondsToStringDate(milliseconds: Long): String {
        return try {
            val formatter = SimpleDateFormat("dd.MM.yyyy")
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = milliseconds
            return formatter.format(calendar.time)
        } catch (exception: Exception) {
            Log.d("error", "", exception)
            ""
        }
    }

    /**
     * Transforma o data intr-un string
     */
    fun dateToString(
        date: Date
    ): String {
        return try {
            val dateFormat = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            simpleDateFormat.format(date)
        } catch (exception: Exception) {
            Log.d("error", "", exception)
            ""
        }
    }

    /**
     * Transforma un string intr-o data
     */
    fun stringToDate(
        date: String
    ): Date {
        return try {
            val dateFormat = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
            simpleDateFormat.parse(date)
        } catch (exception: Exception) {
            Log.d("error", "", exception)
            Date()
        }
    }

//    fun stringToTable(
//        table: String
//    ): Product {
//        var values = table.split(";")
//        return Product(
//            id = values[0].toInt(),
//            name = values[1],
//            seats = values[2].toInt(),
//            type = TableType.valueOf(values[3]),
//            status = TableStatus.valueOf(values[4])
//        )
//    }
//
//    fun tableToString(
//        product: Product
//    ): String{
//        return product.id.toString() + ";" + product.name + ";" + product.seats.toString() + ";" + product.type.toString() + ";" + product.status.toString()
//    }
}

fun Any.logi(message: Any? = "no message!") {
    Log.i(this.javaClass.simpleName, message.toString())
}

fun Any.logd(message: Any? = "no message!", cause: Throwable? = null) {
    Log.d(this.javaClass.simpleName, message.toString(), cause)
}

fun Any.logw(message: Any? = "no message!", cause: Throwable? = null) {
    Log.w(this.javaClass.simpleName, message.toString(), cause)
}

fun Any.loge(message: Any? = "no message!", cause: Throwable? = null) {
    Log.e(this.javaClass.simpleName, message.toString(), cause)
}

fun networkConnectivity(context: Context, syncProducts: () -> Unit? = {}): Boolean {
    val cm = context
        .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            //take action when network connection is gained
            syncProducts()
        }
    })
    if (cm != null) {
        val capabilities =
            cm.getNetworkCapabilities(cm.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

@Serializable
class Obj(
    var id: Int
)