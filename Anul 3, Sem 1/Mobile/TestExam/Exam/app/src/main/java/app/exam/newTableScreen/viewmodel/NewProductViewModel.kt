package app.exam.newTableScreen.viewmodel

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
class NewProductViewModel @Inject constructor(
    private val useCase: ProductsUseCase
) : ViewModel() {
    val nameError = MutableLiveData<String>()
    val typeError = MutableLiveData<String>()
    val quantityError = MutableLiveData<String>()
    val priceError = MutableLiveData<String>()
    val discountError = MutableLiveData<String>()

    val syncProducts: () -> Unit = {
        viewModelScope.launch {
            useCase.syncProducts()
        }
    }

    fun saveProduct(context: Context, product: Product, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        viewModelScope.launch(Dispatchers.IO){
            val connectivity = networkConnectivity(context, syncProducts)
            if (!connectivity) {
                showMessage(true, "No internet connection!")
                product.isUploaded = false
                useCase.saveProductLocally(product)
            } else {
                product.isUploaded = true
                useCase.saveProduct(product, showMessage, progressIndicatorVisibility)
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

    fun validateType(type: String): Boolean {
        if (type.isEmpty()) {
            typeError.value = "Type is required"
            return true
        }
        if (type.length > 100) {
            typeError.value = "Type must not exceed 100 characters"
            return true
        }
        typeError.value = ""
        return false
    }

    fun validateQuantity(quantity: String): Boolean {
        if (quantity.isEmpty()) {
            quantityError.value = "Quantity is required"
            return true
        }
        try{
            val nr = quantity.toInt()
        } catch (exception: NumberFormatException){
            quantityError.value = "Quantity must be a number!"
            return true
        }
        quantityError.value = ""
        return false
    }

    fun validatePrice(price: String): Boolean {
        if (price.isEmpty()) {
            priceError.value = "Price is required"
            return true
        }
        try{
            val nr = price.toInt()
        } catch (exception: NumberFormatException){
            priceError.value = "Price must be a number!"
            return true
        }
        priceError.value = ""
        return false
    }

    fun validateDiscount(discount: String): Boolean {
        if (discount.isEmpty()) {
            discountError.value = "Discount is required"
            return true
        }
        try{
            val nr = discount.toInt()
            if(nr<0 || nr>100){
                discountError.value = "Discount must be a number between 0 and 100!"
                return true
            }
        } catch (exception: NumberFormatException){
            discountError.value = "Discount must be a number!"
            return true
        }
        discountError.value = ""
        return false
    }

}