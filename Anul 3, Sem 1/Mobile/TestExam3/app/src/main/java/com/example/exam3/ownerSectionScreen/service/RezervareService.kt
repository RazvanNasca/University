package com.example.exam3.ownerSectionScreen.service


import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam3.BuildConfig
import retrofit2.http.*
import rx.Observable

interface RezervareService {
    @GET("open")
    fun getRezervari(): Observable<List<Rezervare>>

    @POST("confirm")
    fun addRezervare(@Body rezervare: Rezervare): Observable<Rezervare>

    @POST("create")
    fun createRezervare(@Body rezervare: Rezervare): Observable<Rezervare>

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