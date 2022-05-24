package com.example.exam3.ownerSectionScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam2.utils.networkConnectivity
import com.example.exam3.ownerSectionScreen.usecase.RezervariUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerSectionViewModel @Inject constructor(
    private val useCase: RezervariUseCase
) : ViewModel() {

    lateinit var listOfRezervari : LiveData<List<Rezervare>>
    val isRetrievedSuccessfully = MutableLiveData<Boolean>()

    val syncRezervari: () -> Unit = {
        viewModelScope.launch {
            useCase.syncRezervari()
        }
    }

    init{
        viewModelScope.launch {
            try{
                listOfRezervari = useCase.getAllLocalRezervari().asLiveData()
                isRetrievedSuccessfully.postValue(true)
            } catch (exception : Exception){
                isRetrievedSuccessfully.postValue(false)
                Log.d("error", "Could not load local data", exception)
            }
        }
    }

    fun loadRezervari(context: Context, showError: (String) -> Unit, progressBarVisibility : MutableState<Boolean>){
        viewModelScope.launch {
            val connectivity = networkConnectivity(context, syncRezervari)
            if (!connectivity) {
                showError("No internet connection! Showing local data")
            } else {
                useCase.getAllRezervari(showError, progressBarVisibility)
            }
        }
    }
}