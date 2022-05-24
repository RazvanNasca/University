package com.example.lab3_native_application.cars.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_native_application.R
import com.example.lab3_native_application.cars.CarAdapter
import com.example.lab3_native_application.cars.db.SQLiteHelper
import com.example.lab3_native_application.cars.domain.Car

class MainActivity : AppCompatActivity()
{
    private lateinit var addBtn: Button
    private lateinit var sqlitehelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView

    private var adapter: CarAdapter? = null
    private var currentCar: Car? = null

    var createCar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        onAddResult(result)
    }

    var updateCar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        onUpdateResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqlitehelper = SQLiteHelper(this)
        getAllCars()
        adapter?.setOnClickItem {
            Toast.makeText(this, it.model, Toast.LENGTH_SHORT).show()
            currentCar = Car(nrInmatriculare = it.nrInmatriculare, model = it.model,
                combustibil = it.combustibil, stare = it.stare, pret = it.pret
            )
            val car = findByNrInmatriculare(it.nrInmatriculare)
            updateCar.launch(
                Intent(this, UpdateActivity::class.java).putExtra(
                "car", it.nrInmatriculare
            ))
        }
        adapter?.setOnClickDeleteItem { deleteCar(it.nrInmatriculare) }
        addBtn.setOnClickListener {
            createCar.launch(Intent(this, AddActivity::class.java));
        }
    }



    private fun onAddResult(result: ActivityResult) {
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val carNr = data?.getStringExtra("carNr")
            val car = findByNrInmatriculare(carNr!!)
            adapter?.addItem(car)
            Toast.makeText(this, "Car added succesfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onUpdateResult(result: ActivityResult) {
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val carNr = data?.getStringExtra("carNr")
            val car = findByNrInmatriculare(carNr!!)
            adapter?.updateItem(car)
            Toast.makeText(this, "Car update succesfully", Toast.LENGTH_SHORT).show()
        }
    }

    private fun findByNrInmatriculare(nrInmatriculare: String): Car {
        Log.e("Inainte de cautare", nrInmatriculare)
        val car = sqlitehelper.getCarByNrInmatriculare(nrInmatriculare)
        Log.e("Masina gasita: ", car.nrInmatriculare)
        return car
    }

    private fun getAllCars() {
        val cars = sqlitehelper.getAllCars()
        Log.e("Masini: ", "${cars.size}")
        adapter?.addItems(cars)
    }

    private fun initView() {
        addBtn = findViewById(R.id.btnAdd)
        recyclerView = findViewById(R.id.recyclerView)
    }

    private fun deleteCar(nrInmatriculare: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete this car?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqlitehelper.deleteCarByNrInmatriculare(nrInmatriculare)
            getAllCars()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CarAdapter()
        recyclerView.adapter = adapter
    }
}
