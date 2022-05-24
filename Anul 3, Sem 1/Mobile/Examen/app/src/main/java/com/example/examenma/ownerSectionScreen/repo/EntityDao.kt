package com.example.examenma.ownerSectionScreen.repo

import androidx.room.*
import com.example.examenma.ownerSectionScreen.domain.Entity
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
interface EntityDao {
    @Query("SELECT * FROM entities_table")
    fun getAllProducts(): Flow<List<Entity>>
    // a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
    // You don't need an Activity to observe (collect) data

    @Insert
    fun insert(entity: Entity)

    @Query("DELETE FROM entities_table")
    fun deleteAllEntities()

    @Query("SELECT * FROM entities_table WHERE isUploaded=0")
    fun getOfflineEntities(): Flow<List<Entity>>

    @Query("DELETE FROM entities_table WHERE isUploaded=0")
    fun deleteOfflineProducts()

    @Insert
    fun saveEntities(entities: List<Entity>)

    @Delete
    fun delete(entity: Entity)
}