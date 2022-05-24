package com.example.examenma.ownerSectionScreen.repo

import androidx.annotation.WorkerThread
import androidx.compose.runtime.MutableState
import com.example.examenma.ownerSectionScreen.domain.Entity
import com.example.examenma.ownerSectionScreen.service.EntityService
import com.example.examenma.utils.logd
import com.example.examenma.utils.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface EntitiesRepository {
    fun getAllLocalEntities(): Flow<List<Entity>>
    fun loadAllEntities(
        showError: (String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    )

    suspend fun saveEntity(
        entity: Entity,
        showMessage: (Boolean, String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    )

    suspend fun saveEntityLocally(entity: Entity)

    suspend fun syncEntities()
}

class EntitiesRepositoryImpl(private val entityDao: EntityDao, private val entityService: EntityService) : EntitiesRepository {

    override fun getAllLocalEntities(): Flow<List<Entity>> {
        return entityDao.getAllProducts()
    }

    override fun loadAllEntities(
        showError: (String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    ) {
        entityService.getEntities()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<Entity>>() {
                override fun onCompleted() {
                    logd("Entity Service completed")
                    progressIndicatorVisibility.value = false
                }

                override fun onError(e: Throwable) {
                    loge("Error while loading the Entity", e)
                    showError("Not able to retrieve the data. Displaying local data!")
                    progressIndicatorVisibility.value = false
                }

                override fun onNext(entities: List<Entity>) {
                    Thread(Runnable {
                        entities.forEach { entity ->
                            entity.isUploaded = true
                        }
                        entityDao.deleteAllEntities()
                        entityDao.saveEntities(entities)
                    }).start()
                    logd("Loaded Entities")
                }

                override fun onStart() {
                    logd("Loading Entities")
                    progressIndicatorVisibility.value = true
                }
            })
    }

    override suspend fun saveEntity(
        entity: Entity,
        showMessage: (Boolean, String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    ) {
        entityService.addEntity(entity)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Entity>() {
                override fun onCompleted() {
                    logd("Entities Service completed")
                    progressIndicatorVisibility.value = false
                }

                override fun onError(e: Throwable) {
                    loge("Error while persisting an Entity", e)
                    showMessage(false, "Not able to connect to the server, will not persist!")
                    progressIndicatorVisibility.value = false
                }

                override fun onNext(entity: Entity) {
                    Thread(Runnable {
                        entity.isUploaded = true
                        entityDao.insert(entity)
                    }).start()
                    showMessage(true, "Entity saved successfully!")
                    logd("Entity persisted")
                }

                override fun onStart() {
                    logd("Saving Entity $entity")
                    progressIndicatorVisibility.value = true
                }
            })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun saveEntityLocally(entity: Entity) {
        entityDao.insert(entity)
    }

    override suspend fun syncEntities() {
        entityDao.getOfflineEntities().collect { entities ->
            entities.forEach { entity ->
                println(entity)
                entityService.addEntity(entity)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Entity>() {
                        override fun onCompleted() {
                            logd("Entities Service completed")
                        }

                        override fun onError(e: Throwable) {
                            loge("Error while persisting an Entity", e)
                        }

                        override fun onNext(newEntity: Entity) {
                            Thread(Runnable {
                                newEntity.isUploaded = true
                                entityDao.delete(newEntity)
                                entityDao.insert(newEntity)
                            }).start()
                            logd("Entity persisted")
                        }

                        override fun onStart() {
                            logd("Saving Entity $entity")
                        }
                    })
            }
        }
    }

//    override suspend fun deleteProduct(productId: Long, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
//        try{
//            logd("Deleting product with id $productId")
//            progressIndicatorVisibility.value = true
//            val response = productService.deleteProduct(productId);
//            if(response.isSuccessful) {
//                Thread(Runnable { productDao.delete(productId) }).start()
//                showMessage(true, "Product deleted successfully!")
//                logd("Product deleted")
//                logd("Product Service completed")
//                progressIndicatorVisibility.value = false
//            }
//        } catch (exception: Exception){
//            showMessage(false, "Not able to connect to the server, will not delete!")
//            loge("Error while deleting a product", exception)
//            progressIndicatorVisibility.value = false
//        }
//    }
}