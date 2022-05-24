package com.example.examenma.ownerSectionScreen.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.*
import com.example.examenma.ownerSectionScreen.domain.Entity
import com.example.examenma.utils.networkConnectivity
import com.example.examenma.ownerSectionScreen.usecase.EntitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerSectionViewModel @Inject constructor(
    private val useCase: EntitiesUseCase
) : ViewModel() {

    private var _listOfEntities : MutableLiveData<List<Entity>> = MutableLiveData<List<Entity>>(listOf())
    val listOfEntities : LiveData<List<Entity>> = _listOfEntities
    val isRetrievedSuccessfully = MutableLiveData<Boolean>()

    val syncEntities: () -> Unit = {
        viewModelScope.launch {
            useCase.syncEntities()
        }
    }

    init{
        viewModelScope.launch {
            try{
                useCase.getAllLocalEntities().collect {
                    _listOfEntities.postValue(it)
                    isRetrievedSuccessfully.postValue(true)}

            } catch (exception : Exception){
                isRetrievedSuccessfully.postValue(false)
                Log.d("error", "Could not load local data", exception)
            }
        }
    }

    fun loadEntities(context: Context, showError: (String) -> Unit, progressBarVisibility : MutableState<Boolean>){
        viewModelScope.launch {
            val connectivity = networkConnectivity(context, syncEntities)
            if (!connectivity) {
                showError("No internet connection! Showing local data")
            } else {
                useCase.getAllEntities(showError, progressBarVisibility)
            }
        }
    }

    fun filterEntities(){
        val dosare: MutableMap<String, Entity> = mutableMapOf()
        _listOfEntities.value?.forEach{ entity ->
            entity.medieAll = (entity.medie1 * 3 + entity.medie2)/4
            if(entity.status == true)
                dosare[entity.nume] = entity
        }
        _listOfEntities.postValue(dosare.map { it.value }.sortedBy{ it.medieAll }.reversed())
    }
}