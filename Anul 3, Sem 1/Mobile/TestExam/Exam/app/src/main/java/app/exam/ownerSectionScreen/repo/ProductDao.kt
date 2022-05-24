package app.exam.ownerSectionScreen.repo

import androidx.room.*
import app.exam.ownerSectionScreen.domain.Product
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
interface ProductDao {
    @Query("SELECT * FROM products_table")
    fun getAllProducts(): Flow<List<Product>>
    // a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value.
    // You don't need an Activity to observe (collect) data

    @Insert
    fun insert(product: Product)

    @Query("DELETE FROM products_table")
    fun deleteAllProducts()

    @Query("SELECT * FROM products_table WHERE isUploaded=0")
    fun getOfflineProducts(): Flow<List<Product>>

    @Query("DELETE FROM products_table WHERE isUploaded=0")
    fun deleteOfflineProducts()

    @Delete
    fun delete(product: Product)

    @Insert
    fun saveProducts(products: List<Product>)
}