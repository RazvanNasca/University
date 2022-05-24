package app.exam.di

import android.content.Context
import app.exam.database.AppDatabase
import app.exam.ownerSectionScreen.service.ProductService
import app.exam.ownerSectionScreen.repo.ProductsRepository
import app.exam.ownerSectionScreen.repo.ProductsRepositoryImpl
import app.exam.ownerSectionScreen.usecase.ProductsUseCase
import app.exam.ownerSectionScreen.usecase.ProductsUseCaseImpl
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
    fun provideProductsRepository(@ApplicationContext appContext: Context): ProductsRepository {
        val tableDao = AppDatabase.getDatabase(appContext).productDao()

        val gson = GsonBuilder().setDateFormat("dd.MM.yyyy").create()
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(ProductService.SERVICE_ENDPOINT)
            .build()

        val tableService = retrofit.create(ProductService::class.java)
        return ProductsRepositoryImpl(tableDao, tableService)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleInt {

        @Binds
        @Singleton
        fun provideProductsUseCase(uc: ProductsUseCaseImpl): ProductsUseCase
        
    }

}