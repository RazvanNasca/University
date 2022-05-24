package com.example.exam3.newTableScreen.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam2.utils.networkConnectivity
import com.example.exam3.ownerSectionScreen.usecase.RezervariUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class NewProductViewModel @Inject constructor(
    private val useCase: RezervariUseCase
) : ViewModel() {
    val nameError = MutableLiveData<String>()
    val doctorError = MutableLiveData<String>()
    val dataError = MutableLiveData<String>()
    val oraError = MutableLiveData<String>()
    val detaliiError = MutableLiveData<String>()

    val syncProducts: () -> Unit = {
        viewModelScope.launch {
            useCase.syncRezervari()
        }
    }

    fun saveProduct(context: Context, product: Rezervare, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        viewModelScope.launch(Dispatchers.IO){
            val connectivity = networkConnectivity(context, syncProducts)
            if (!connectivity) {
                showMessage(true, "No internet connection!")
                product.isUploaded = false
                useCase.saveRezervareLocally(product)
            } else {
                product.isUploaded = true
                useCase.saveRezervare(product, showMessage, progressIndicatorVisibility)
            }
        }
    }

    fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            nameError.value = "Name is required"
            return true
        }
        if (name.length > 100) {
            nameError.value = "Name must not exceed 100 characters"
            return true
        }
        nameError.value = ""
        return false
    }

    fun validateDoctor(type: String): Boolean {
        if (type.isEmpty()) {
            doctorError.value = "Type is required"
            return true
        }
        if (type.length > 100) {
            doctorError.value = "Type must not exceed 100 characters"
            return true
        }
        doctorError.value = ""
        return false
    }

    fun validateData(quantity: String): Boolean {
        if (quantity.isEmpty()) {
            dataError.value = "Data is required"
            return true
        }
        try{
            val nr = quantity.toInt()
        } catch (exception: NumberFormatException){
            dataError.value = "Data must be a number!"
            return true
        }
        dataError.value = ""
        return false
    }

    fun validateOra(price: String): Boolean {
        if (price.isEmpty()) {
            oraError.value = "Ora is required"
            return true
        }
        try{
            val nr = price.toInt()
        } catch (exception: NumberFormatException){
            oraError.value = "Ora must be a number!"
            return true
        }
        oraError.value = ""
        return false
    }

    fun validateDiscount(type: String): Boolean {
        if (type.isEmpty()) {
            doctorError.value = "Details is required"
            return true
        }
        if (type.length > 100) {
            doctorError.value = "Details must not exceed 100 characters"
            return true
        }
        doctorError.value = ""
        return false
    }

}