package app.exam.ownerSectionScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import app.exam.ownerSectionScreen.domain.Product
import app.exam.utils.networkConnectivity
import app.exam.ownerSectionScreen.usecase.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerSectionViewModel @Inject constructor(
    private val useCase: ProductsUseCase
) : ViewModel() {

    private var _listOfProducts : MutableLiveData<List<Product>> = MutableLiveData<List<Product>>(listOf())
    val listOfProducts : LiveData<List<Product>> = _listOfProducts
    val isRetrievedSuccessfully = MutableLiveData<Boolean>()

    val syncProducts: () -> Unit = {
        viewModelScope.launch {
            useCase.syncProducts()
        }
    }

    init{
        viewModelScope.launch {
            try{
                useCase.getAllLocalProducts().collect {
                    _listOfProducts.postValue(it)
                    isRetrievedSuccessfully.postValue(true)
                }
            } catch (exception : Exception){
                isRetrievedSuccessfully.postValue(false)
                Log.d("error", "Could not load local data", exception)
            }
        }
    }

    fun loadProducts(context: Context, showError: (String) -> Unit, progressBarVisibility : MutableState<Boolean>){
        viewModelScope.launch {
            val connectivity = networkConnectivity(context, syncProducts)
            if (!connectivity) {
                showError("No internet connection! Showing local data")
            } else {
                useCase.getAllProducts(showError, progressBarVisibility)
            }
        }
    }
}