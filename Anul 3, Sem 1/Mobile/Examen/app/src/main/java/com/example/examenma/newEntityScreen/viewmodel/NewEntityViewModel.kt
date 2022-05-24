package com.example.examenma.newEntityScreen.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examenma.ownerSectionScreen.domain.Entity
import com.example.examenma.ownerSectionScreen.usecase.EntitiesUseCase
import com.example.examenma.utils.networkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class NewEntityViewModel @Inject constructor(
    private val useCase: EntitiesUseCase
) : ViewModel() {
    val nameError = MutableLiveData<String>()
    val medie1Error = MutableLiveData<String>()
    val medie2Error = MutableLiveData<String>()

    val syncEntities: () -> Unit = {
        viewModelScope.launch {
            useCase.syncEntities()
        }
    }

    fun saveEntity(context: Context, entity: Entity, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        viewModelScope.launch(Dispatchers.IO){
            val connectivity = networkConnectivity(context, syncEntities)
            if (!connectivity) {
                showMessage(true, "No internet connection!")
                entity.isUploaded = false
                useCase.saveEntityLocally(entity)
            } else {
                entity.isUploaded = true
                useCase.saveEntity(entity, showMessage, progressIndicatorVisibility)
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

    fun validateMedie1(price: String): Boolean {
        if (price.isEmpty()) {
            medie1Error.value = "Medie is required"
            return true
        }
        try{
            val nr = price.toInt()
            if(nr<1 || nr>10){
                medie1Error.value = "Medie must be a number between 1 and 10!"
                return true
            }
        } catch (exception: NumberFormatException){
            medie1Error.value = "Medie must be a number!"
            return true
        }
        medie1Error.value = ""
        return false
    }

    fun validateMedie2(discount: String): Boolean {
        if (discount.isEmpty()) {
            medie2Error.value = "Medie is required"
            return true
        }
        try{
            val nr = discount.toInt()
            if(nr<1 || nr>10){
                medie2Error.value = "Medie must be a number between 1 and 10!"
                return true
            }
        } catch (exception: NumberFormatException){
            medie2Error.value = "Medie must be a number!"
            return true
        }
        medie2Error.value = ""
        return false
    }

}