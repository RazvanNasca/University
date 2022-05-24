package com.example.exam3.ownerSectionScreen.repo

import androidx.annotation.WorkerThread
import androidx.compose.runtime.MutableState
import com.example.exam3.ownerSectionScreen.domain.Rezervare
import com.example.exam3.ownerSectionScreen.service.RezervareService
import com.example.exam2.utils.logd
import com.example.exam2.utils.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface RezervariRepository {
    fun getAllLocalRezervari(): Flow<List<Rezervare>>
    fun loadAllRezervari(
        showError: (String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    )

    suspend fun saveRezervare(
        rezervare: Rezervare,
        showMessage: (Boolean, String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    )

    suspend fun saveRezervareLocally(
        rezervare: Rezervare
    )

    suspend fun syncRezervari()
}

class RezervariRepositoryImpl(
    private val rezervareDao: RezervareDao,
    private val rezervareService: RezervareService
) : RezervariRepository {

    override fun getAllLocalRezervari(): Flow<List<Rezervare>> {
        return rezervareDao.getAllRezervari()
    }

    override fun loadAllRezervari(
        showError: (String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    ) {
        rezervareService.getRezervari()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<Rezervare>>() {
                override fun onCompleted() {
                    logd("Rezervari Service completed")
                    progressIndicatorVisibility.value = false
                }

                override fun onError(e: Throwable) {
                    loge("Error while loading the Products", e)
                    showError("Not able to retrieve the data. Displaying local data!")
                    progressIndicatorVisibility.value = false
                }

                override fun onNext(rezervari: List<Rezervare>) {
                    Thread(Runnable {
                        rezervari.forEach { rezervare ->
                            rezervare.isUploaded = true
                        }
                        rezervareDao.deleteAllRezervari()
                        rezervareDao.saveRezervari(rezervari)
                    }).start()
                    logd("Loaded Rezervari")
                }

                override fun onStart() {
                    logd("Loading Rezervari")
                    progressIndicatorVisibility.value = true
                }
            })
    }

    override suspend fun saveRezervare(
        rezervare: Rezervare,
        showMessage: (Boolean, String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    ) {
        rezervareService.createRezervare(rezervare)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Rezervare>() {
                override fun onCompleted() {
                    logd("Rezervari Service completed")
                    progressIndicatorVisibility.value = false
                }

                override fun onError(e: Throwable) {
                    loge("Error while persisting a Rezervare", e)
                    showMessage(false, "Not able to connect to the server, will not persist!")
                    progressIndicatorVisibility.value = false
                }

                override fun onNext(rezervare: Rezervare) {
                    Thread(Runnable {
                        rezervare.isUploaded = true
                        rezervareDao.insert(rezervare)
                    }).start()
                    showMessage(true, "Rezervare saved successfully!")
                    logd("Rezervare persisted")
                }

                override fun onStart() {
                    logd("Saving Rezervare $rezervare")
                    progressIndicatorVisibility.value = true
                }
            })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun saveRezervareLocally(rezervare: Rezervare) {
        rezervareDao.insert(rezervare)
    }

    override suspend fun syncRezervari() {
        rezervareDao.getOfflineRezervari().collect { rezervari -> rezervari.forEach{rezervare ->
            println(rezervare)
            rezervareService.addRezervare(rezervare)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Rezervare>() {
                    override fun onCompleted() {
                        logd("Rezervari Service completed")
                    }

                    override fun onError(e: Throwable) {
                        loge("Error while persisting a Rezervare", e)
                    }

                    override fun onNext(newRezervare: Rezervare) {
                        Thread(Runnable {
                            newRezervare.isUploaded = true
                            rezervareDao.delete(newRezervare)
                            rezervareDao.insert(newRezervare)
                        }).start()
                        logd("Rezervare persisted")
                    }

                    override fun onStart() {
                        logd("Saving Rezervare $rezervare")
                    }
                })
        } }

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