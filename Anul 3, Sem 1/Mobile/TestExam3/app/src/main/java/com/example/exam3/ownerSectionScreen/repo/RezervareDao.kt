package com.example.exam3.ownerSectionScreen.repo

import androidx.room.*
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import kotlinx.coroutines.flow.Flow

/**
 * The Room Magic is in this file, where you map a Java method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao //Data Access Object
interface RezervareDao {
    @Query("SELECT * FROM rezervare_table")
    fun getAllRezervari(): Flow<List<Rezervare>>
    // a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
    // You don't need an Activity to observe (collect) data

    @Insert
    fun insert(rezervare: Rezervare)

    @Query("DELETE FROM rezervare_table")
    fun deleteAllRezervari()

    @Query("SELECT * FROM rezervare_table WHERE isUploaded=0")
    fun getOfflineRezervari(): Flow<List<Rezervare>>

    @Query("DELETE FROM rezervare_table WHERE isUploaded=0")
    fun deleteOfflineRezervari()

    @Insert
    fun saveRezervari(rezervari: List<Rezervare>)

    @Delete
    fun delete(rezervare: Rezervare)
}