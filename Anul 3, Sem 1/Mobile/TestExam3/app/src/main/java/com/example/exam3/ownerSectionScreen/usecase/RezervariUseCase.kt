package com.example.exam3.ownerSectionScreen.usecase

import androidx.compose.runtime.MutableState
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam3.ownerSectionScreen.repo.RezervariRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RezervariUseCase{
    suspend fun getAllLocalRezervari():Flow<List<Rezervare>>
    suspend fun getAllRezervari(showError: (String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>)

    suspend fun saveRezervare(rezervare: Rezervare, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>)
    suspend fun saveRezervareLocally(rezervare: Rezervare)

    suspend fun syncRezervari()
}

class RezervariUseCaseImpl @Inject constructor(
    val repo: RezervariRepository
) : RezervariUseCase {

    override suspend fun getAllLocalRezervari():Flow<List<Rezervare>> {
        return repo.getAllLocalRezervari()
    }

    override suspend fun getAllRezervari(showError: (String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        repo.loadAllRezervari(showError, progressIndicatorVisibility)
    }

    override suspend fun saveRezervare(rezervare: Rezervare, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        repo.saveRezervare(rezervare, showMessage, progressIndicatorVisibility)
    }

    override suspend fun saveRezervareLocally(rezervare: Rezervare) {
        repo.saveRezervareLocally(rezervare)
    }

    override suspend fun syncRezervari() {
        repo.syncRezervari()
    }
}
