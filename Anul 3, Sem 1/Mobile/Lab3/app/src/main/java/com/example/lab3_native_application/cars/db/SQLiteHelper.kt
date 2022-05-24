package com.example.lab3_native_application.cars.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.lab3_native_application.cars.domain.Car
import java.lang.Exception

class SQLiteHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    companion object {
        private const val DATABASE_NAME = "cars.db"
        private const val DATABASE_VERSION = 1
        private const val TBL_CARS = "tbl_cars"
        private const val NR_INMATRICULARE = "nr_inmatriculare"
        private const val MODEL = "model"
        private const val COMBUSTIBIL = "combustibil"
        private const val STARE = "stare"
        private const val PRET = "pret"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableCars = (
                "CREATE TABLE " + TBL_CARS + "("
                        + NR_INMATRICULARE + " TEXT PRIMARY KEY,"
                        + MODEL + " TEXT,"
                        + COMBUSTIBIL + " TEXT,"
                        + STARE + " TEXT,"
                        + PRET + " REAL" + ")"
                )
        db?.execSQL(createTableCars);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CARS")
        onCreate(db)
    }

    fun insertCar(car: Car): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NR_INMATRICULARE, car.nrInmatriculare)
        contentValues.put(MODEL, car.model)
        contentValues.put(COMBUSTIBIL, car.combustibil)
        contentValues.put(STARE, car.stare)
        contentValues.put(PRET, car.pret)

        val success = db.insert(TBL_CARS, null, contentValues)
        db.close()
        return success
    }

    fun getAllCars(): ArrayList<Car> {
        val cars: ArrayList<Car> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_CARS"
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch(e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var nrInmatriculare: String
        var model: String
        var combustibil: String
        var stare: String
        var pret: Double

        if(cursor.moveToFirst()){
            do {
                nrInmatriculare = cursor.getString(cursor.getColumnIndex("nr_inmatriculare"))
                model = cursor.getString(cursor.getColumnIndex("model"))
                combustibil = cursor.getString(cursor.getColumnIndex("combustibil"))
                stare = cursor.getString(cursor.getColumnIndex("stare"))
                pret = cursor.getDouble(cursor.getColumnIndex("pret"))

                val car = Car(nrInmatriculare = nrInmatriculare, model = model,
                    combustibil = combustibil, stare = stare, pret = pret)
                cars.add(car)

            } while(cursor.moveToNext())
        }
        return cars
    }

    fun updateCar(car: Car) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NR_INMATRICULARE, car.nrInmatriculare)
        contentValues.put(MODEL, car.model)
        contentValues.put(COMBUSTIBIL, car.combustibil)
        contentValues.put(STARE, car.stare)
        contentValues.put(PRET, car.pret)

        val success = db.update(TBL_CARS, contentValues, "nr_inmatriculare='"+car.nrInmatriculare+"'", null)
        db.close()
        return success
    }

    fun deleteCarByNrInmatriculare(nr_inmatriculare: String) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(NR_INMATRICULARE, nr_inmatriculare)
        val success = db.delete(TBL_CARS, "nr_inmatriculare='$nr_inmatriculare'", null)
        db.close()
        return success
    }

    fun getCarByNrInmatriculare(nr_inmatriculare: String) : Car {
        val db = this.readableDatabase
        val contentValues = ContentValues()
        contentValues.put(NR_INMATRICULARE, nr_inmatriculare)
        val selectQuery = "SELECT  * FROM " + TBL_CARS + " WHERE " + NR_INMATRICULARE + " = '" + nr_inmatriculare + "'"
        Log.e("before query", selectQuery)
        val car = Car(nrInmatriculare = "", model = "", combustibil = "", stare = "", pret = 0.0)
        val cursor: Cursor?
        cursor = db.rawQuery(selectQuery, null)
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst()
                if (cursor.getCount() > 0) {
                    do {
                        car.nrInmatriculare = cursor.getString(cursor.getColumnIndex(NR_INMATRICULARE))
                        car.model = cursor.getString(cursor.getColumnIndex(MODEL))
                        car.combustibil = cursor.getString(cursor.getColumnIndex(COMBUSTIBIL))
                        car.stare = cursor.getString(cursor.getColumnIndex(STARE))
                        car.pret = cursor.getDouble(cursor.getColumnIndex(PRET))
                    } while ((cursor.moveToNext()));
                }
            }
        } finally {
            cursor.close();
        }
        return car
    }
}