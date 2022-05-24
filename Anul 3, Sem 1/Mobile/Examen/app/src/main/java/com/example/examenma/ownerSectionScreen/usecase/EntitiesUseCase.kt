package com.example.examenma.ownerSectionScreen.usecase

import androidx.compose.runtime.MutableState
import com.example.examenma.ownerSectionScreen.domain.Entity
import com.example.examenma.ownerSectionScreen.repo.EntitiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EntitiesUseCase{
    suspend fun getAllLocalEntities():Flow<List<Entity>>
    suspend fun getAllEntities(showError: (String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>)

    suspend fun saveEntity(entity: Entity, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>)
    suspend fun saveEntityLocally(entity: Entity)

    suspend fun syncEntities()
}

class EntitiesUseCaseImpl @Inject constructor(
    val repo: EntitiesRepository
) : EntitiesUseCase {

    override suspend fun getAllLocalEntities():Flow<List<Entity>> {
        return repo.getAllLocalEntities()
    }

    override suspend fun getAllEntities(showError: (String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        repo.loadAllEntities(showError, progressIndicatorVisibility)
    }

    override suspend fun saveEntity(entity: Entity, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        repo.saveEntity(entity, showMessage, progressIndicatorVisibility)
    }

    override suspend fun saveEntityLocally(entity: Entity) {
        repo.saveEntityLocally(entity)
    }

    override suspend fun syncEntities() {
        repo.syncEntities()
    }
}
