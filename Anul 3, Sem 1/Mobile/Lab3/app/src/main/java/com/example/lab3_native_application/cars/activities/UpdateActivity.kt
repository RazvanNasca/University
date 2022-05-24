package com.example.lab3_native_application.cars.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.lab3_native_application.R
import com.example.lab3_native_application.cars.db.SQLiteHelper
import com.example.lab3_native_application.cars.domain.Car

class UpdateActivity: AppCompatActivity()
{
    private lateinit var idNrInmatirculare: EditText
    private lateinit var idModel: EditText
    private lateinit var idCombustibil: EditText
    private lateinit var idStare: EditText
    private lateinit var idPret: EditText
    private lateinit var updateBtn: Button
    private lateinit var currentCar: Car

    private lateinit var sqlitehelper: SQLiteHelper

    private var combustibil = arrayOf("Benzina", "Motorina", "Electric", "Hibrid")
    private var stare = arrayOf("Libera", "Inchiriata")

    private fun initView() {
        idNrInmatirculare = findViewById(R.id.idNrInmatriculare)
        idModel = findViewById(R.id.idModel)
        idCombustibil = findViewById(R.id.idCombustibil)
        idStare = findViewById(R.id.idStare)
        idPret = findViewById(R.id.idPret)
        updateBtn = findViewById(R.id.btnUpdate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_item)
        sqlitehelper = SQLiteHelper(this)
        currentCar = sqlitehelper.getCarByNrInmatriculare(intent.extras?.get("car") as String)
        val carNr = currentCar.nrInmatriculare
        initView()
        idNrInmatirculare.setText(carNr)
        idModel.setText(currentCar.model)
        idCombustibil.setText(currentCar.combustibil)
        idStare.setText(currentCar.stare)
        idPret.setText(currentCar.pret.toString())
        updateBtn.setOnClickListener { updateCar() }

        val adapterCombustibil: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, combustibil)
        val adapterStare: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, stare)

        val combustibilTextView = findViewById<View>(R.id.idCombustibil) as AutoCompleteTextView
        val stareTextView = findViewById<View>(R.id.idStare) as AutoCompleteTextView

        combustibilTextView.threshold = 1
        stareTextView.threshold = 1

        combustibilTextView.setAdapter(adapterCombustibil)
        stareTextView.setAdapter(adapterStare)

    }

    private fun clearEditText() {
        idNrInmatirculare.setText("")
        idModel.setText("")
        idCombustibil.setText("")
        idStare.setText("")
        idPret.setText("")
    }

    private fun updateCar() {
        val model = idModel.text.toString()
        val combustibil = idCombustibil.text.toString()
        val stare = idStare.text.toString()
        val pret = idPret.text.toString()

        var ok = true

        if(!combustibil.isEmpty() && combustibil != "Benzina" && combustibil != "Motorina" && combustibil != "Electric" && combustibil != "Hibrid")
        {
            Toast.makeText(this, "Please enter a correct Combustibil ", Toast.LENGTH_SHORT).show()
            ok = false
        }

        if(!stare.isEmpty() && stare != "Libera" && stare != "Inchiriata")
        {
            Toast.makeText(this, "Please enter a correct Stare ", Toast.LENGTH_SHORT).show()
            ok = false
        }

        if (model.isEmpty() || combustibil.isEmpty() || stare.isEmpty() || pret.isEmpty()) {
            Toast.makeText(this, "Please enter the required field ", Toast.LENGTH_SHORT).show()
            ok = false
        }

        if(!pret.matches("-?\\d+(\\.\\d+)?".toRegex()))
        {
            Toast.makeText(this, "Please enter a correct Pret ", Toast.LENGTH_SHORT).show()
            ok = false
        }

        if(ok) {
            val car = Car(
                nrInmatriculare = currentCar.nrInmatriculare, model = model,
                combustibil = combustibil, stare = stare, pret = pret.toDouble()
            )
            val status = sqlitehelper.updateCar(car)
            if (status > -1) {
                clearEditText()
                val intent = Intent()
                intent.putExtra("carNr", car.nrInmatriculare)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}