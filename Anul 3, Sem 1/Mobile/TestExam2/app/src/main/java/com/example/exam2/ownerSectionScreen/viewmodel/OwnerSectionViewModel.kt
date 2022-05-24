package com.example.exam2.ownerSectionScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.example.exam2.ownerSectionScreen.domain.Product
import com.example.exam2.utils.networkConnectivity
import com.example.exam2.ownerSectionScreen.usecase.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerSectionViewModel @Inject constructor(
    private val useCase: ProductsUseCase
) : ViewModel() {

    lateinit var listOfProducts : LiveData<List<Product>>
    val isRetrievedSuccessfully = MutableLiveData<Boolean>()

    val syncProducts: () -> Unit = {
        viewModelScope.launch {
            useCase.syncProducts()
        }
    }

    init{
        viewModelScope.launch {
            try{
                listOfProducts = useCase.getAllLocalProducts().asLiveData()
                isRetrievedSuccessfully.postValue(true)
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