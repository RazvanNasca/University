package com.example.exam2.clientSectionScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.example.exam2.ownerSectionScreen.usecase.ProductsUseCase
import com.example.exam2.ownerSectionScreen.domain.Product
import com.example.exam2.utils.networkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientSectionViewModel @Inject constructor(
    private val useCase: ProductsUseCase
) : ViewModel() {

    private var _listOfProducts : MutableLiveData<List<Product>> = MutableLiveData<List<Product>>(listOf())
    val listOfProducts : LiveData<List<Product>> = _listOfProducts
    val isRetrievedSuccessfully = MutableLiveData<Boolean>()

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

    fun loadProducts(context: Context, showMessage: (String) -> Unit, progressBarVisibility : MutableState<Boolean>){
        viewModelScope.launch {
            val connectivity = networkConnectivity(context)
            if (!connectivity) {
                showMessage("No internet connection! Showing local data")
            } else {
                useCase.getAllProducts(showMessage, progressBarVisibility)
            }
        }
    }

    fun filterProducts(){
        val types: MutableMap<String, Product> = mutableMapOf()
        _listOfProducts.value?.forEach{ product ->
            if(types[product.tip] ==null){
                types[product.tip] = product
            } else if(types[product.tip]!!.pret>product.pret){
                types[product.tip] = product
            }
        }
        _listOfProducts.postValue(types.map { it.value }.sortedBy { it.id })
    }
}