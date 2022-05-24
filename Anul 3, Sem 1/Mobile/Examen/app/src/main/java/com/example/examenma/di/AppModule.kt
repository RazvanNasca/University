package com.example.examenma.di

import android.content.Context
import com.example.examenma.database.AppDatabase
import com.example.examenma.ownerSectionScreen.repo.EntitiesRepository
import com.example.examenma.ownerSectionScreen.repo.EntitiesRepositoryImpl
import com.example.examenma.ownerSectionScreen.service.EntityService
import com.example.examenma.ownerSectionScreen.usecase.EntitiesUseCase
import com.example.examenma.ownerSectionScreen.usecase.EntitiesUseCaseImpl
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
    fun provideProductsRepository(@ApplicationContext appContext: Context): EntitiesRepository {
        val entityDao = AppDatabase.getDatabase(appContext).entityDao()

        val gson = GsonBuilder().setDateFormat("dd.MM.yyyy").create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(EntityService.SERVICE_ENDPOINT)
            .build()

        val entityService = retrofit.create(EntityService::class.java)
        return EntitiesRepositoryImpl(entityDao, entityService)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun provideEntitiesUseCase(uc: EntitiesUseCaseImpl): EntitiesUseCase
        
    }

}