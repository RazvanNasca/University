package com.example.lab3_native_application.cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3_native_application.R
import com.example.lab3_native_application.cars.domain.Car

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>()
{
    private var cars: ArrayList<Car> = ArrayList()
    private var onClickItem: ((Car) -> Unit)? = null
    private var onClickDeleteItem: ((Car) -> Unit)? = null

    fun addItems(items: ArrayList<Car>) {
        this.cars = items
        notifyDataSetChanged()
    }

    fun addItem(car: Car) {
        this.cars.add(car)
        notifyDataSetChanged()
    }

    fun updateItem(car: Car) {
        this.cars.forEachIndexed { index, t ->
            t.takeIf { it.nrInmatriculare == car.nrInmatriculare}?.let {
                this.cars[index] = car
            }
        }
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (Car)->Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (Car) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_cars, parent, false)
    )

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bindView(car)
        holder.itemView.setOnClickListener { onClickItem?.invoke(car) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(car) }
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    class CarViewHolder(var view: View) : RecyclerView.ViewHolder(view){

        private var nrInmatriculare = view.findViewById<TextView>(R.id.idNrInmatriculare)
        private var model = view.findViewById<TextView>(R.id.idModel)
        private var combustibil = view.findViewById<TextView>(R.id.idCombustibil)
        private var stare = view.findViewById<TextView>(R.id.idStare)
        private var pret = view.findViewById<TextView>(R.id.idPret)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(car: Car) {
            nrInmatriculare.text = car.nrInmatriculare
            model.text = car.model
            combustibil.text = car.combustibil
            stare.text = car.stare
            pret.text = car.pret.toString()
        }
    }

}