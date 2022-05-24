package com.example.exam2.ownerSectionScreen.service


import com.example.exam2.ownerSectionScreen.domain.Product
import com.example.exam2.BuildConfig
import retrofit2.http.*
import rx.Observable

interface ProductService {
    @GET("products")
    fun getProducts(): Observable<List<Product>>

    @POST("product")
    fun addProduct(@Body product: Product): Observable<Product>

//    @PUT("product")
//    fun updateProduct(@Body e:Product): Observable<Product>
//
//    @DELETE("products/{id}")
//    suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>
//
//    @GET("products")
//    fun getAllProducts(@Query("warehouseId") warehouseId: Long): Observable<List<Product>>

    companion object{ //static
        const val SERVICE_ENDPOINT = BuildConfig.SERVER_URL
    }
}