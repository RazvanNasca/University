package com.example.exam3.clientSectionScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.example.exam3.ownerSectionScreen.usecase.RezervariUseCase
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam2.utils.networkConnectivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientSectionViewModel @Inject constructor(
    private val useCase: RezervariUseCase
) : ViewModel() {

    private var _listOfProducts : MutableLiveData<List<Rezervare>> = MutableLiveData<List<Rezervare>>(listOf())
    val listOfProducts : LiveData<List<Rezervare>> = _listOfProducts
    val isRetrievedSuccessfully = MutableLiveData<Boolean>()

    init{
        viewModelScope.launch {
            try{
                useCase.getAllLocalRezervari().collect {
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
                useCase.getAllRezervari(showMessage, progressBarVisibility)
            }
        }
    }

    fun filterProducts(){

    }
}