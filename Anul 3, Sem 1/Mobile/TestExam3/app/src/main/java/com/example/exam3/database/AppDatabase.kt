package com.example.exam3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam3.ownerSectionScreen.repo.RezervareDao

/**
 * This is the backend. The database. This used to be done by the OpenHelper.
 * The fact that this has very few comments emphasizes its coolness.
 */
@Database(entities = [Rezervare::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun rezervareDao() : RezervareDao

    companion object { //static Java
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context,
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rezervare_app_database"
                )
                // Wipes and rebuilds instead of migrating if no Migration object.
                // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(AppDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class AppDatabaseCallback(
        ) : RoomDatabase.Callback()
    }
}