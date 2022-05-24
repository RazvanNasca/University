package app.exam.detailsScreen.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.exam.ownerSectionScreen.domain.Product
import app.exam.utils.networkConnectivity
import app.exam.ownerSectionScreen.usecase.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NumberFormatException
import javax.inject.Inject

@HiltViewModel
class TableDetailsViewModel @Inject constructor(
    private val useCase: ProductsUseCase
): ViewModel() {
    val nameError = MutableLiveData<String>()
    val seatsError = MutableLiveData<String>()

//    fun updateTable(context: Context, product: Product, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>){
//        viewModelScope.launch(Dispatchers.IO){
//            val connectivity = networkConnectivity(context)
//            if (!connectivity) {
//                showMessage(false, "No internet connection! Cannot update table!")
//            }else{
//                useCase.updateTable(product, showMessage, progressIndicatorVisibility)
//            }
//        }
//    }
//
//    fun deleteTable(context: Context, tableId: Int, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
//        viewModelScope.launch(Dispatchers.IO){
//            val connectivity = networkConnectivity(context)
//            if (!connectivity) {
//                showMessage(false, "No internet connection! Cannot delete table!")
//            }else {
//                useCase.deleteTable(tableId, showMessage, progressIndicatorVisibility)
//            }
//        }
//    }
//
//    fun validateName(name: String): Boolean {
//        if (name.isEmpty()) {
//            nameError.value = "Name is required"
//            return true
//        }
//        if(name.matches(Regex("[0-9]+"))){
//            nameError.value = "Name must not contain any numbers"
//            return true
//        }
//        if (name.length > 100) {
//            nameError.value = "Name must not exceed 100 characters"
//            return true
//        }
//        nameError.value = ""
//        return false
//    }
//
//    fun validateSeats(seats: String): Boolean {
//        if (seats.isEmpty()) {
//            seatsError.value = "Number of seats is required"
//            return true
//        }
//        try {
//            val priceDouble = seats.toInt()
//        } catch (exception: NumberFormatException) {
//            seatsError.value = "You must enter a valid number of seats"
//            return true
//        }
//        seatsError.value = ""
//        return false
//    }
}