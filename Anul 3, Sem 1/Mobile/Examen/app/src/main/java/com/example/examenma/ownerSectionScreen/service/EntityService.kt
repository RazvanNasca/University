package com.example.examenma.ownerSectionScreen.service

import com.example.examenma.ownerSectionScreen.domain.Entity
import com.example.examenma.BuildConfig
import retrofit2.http.*
import rx.Observable

interface EntityService {
    @GET("all")
    fun getEntities(): Observable<List<Entity>>

    @POST("register")
    fun addEntity(@Body entity: Entity): Observable<Entity>

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