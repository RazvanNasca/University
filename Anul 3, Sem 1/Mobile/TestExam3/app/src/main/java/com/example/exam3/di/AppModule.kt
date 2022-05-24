package com.example.exam3.di

import android.content.Context
import com.example.exam3.database.AppDatabase
import com.example.exam3.ownerSectionScreen.repo.RezervariRepository
import com.example.exam3.ownerSectionScreen.repo.RezervariRepositoryImpl
import com.example.exam3.ownerSectionScreen.service.RezervareService
import com.example.exam3.ownerSectionScreen.usecase.RezervariUseCase
import com.example.exam3.ownerSectionScreen.usecase.RezervariUseCaseImpl
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideProductsRepository(@ApplicationContext appContext: Context): RezervariRepository {
        val rezervareDao = AppDatabase.getDatabase(appContext).rezervareDao()

        val gson = GsonBuilder().setDateFormat("dd.MM.yyyy").create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(RezervareService.SERVICE_ENDPOINT)
            .build()

        val rezervareService = retrofit.create(RezervareService::class.java)
        return RezervariRepositoryImpl(rezervareDao, rezervareService)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun provideRezervariUseCase(uc: RezervariUseCaseImpl): RezervariUseCase

    }

}