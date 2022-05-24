package com.example.exam2.ownerSectionScreen.repo

import androidx.annotation.WorkerThread
import androidx.compose.runtime.MutableState
import com.example.exam2.ownerSectionScreen.domain.Product
import com.example.exam2.ownerSectionScreen.service.ProductService
import com.example.exam2.utils.logd
import com.example.exam2.utils.loge
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

interface ProductsRepository {
    fun getAllLocalProducts(): Flow<List<Product>>
    fun loadAllProducts(
        showError: (String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    )

    suspend fun saveProduct(
        product: Product,
        showMessage: (Boolean, String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    )

    suspend fun saveProductLocally(
        product: Product
    )

    suspend fun syncProducts()
}

class ProductsRepositoryImpl(
    private val productDao: ProductDao,
    private val productService: ProductService
) : ProductsRepository {

    override fun getAllLocalProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override fun loadAllProducts(
        showError: (String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    ) {
        productService.getProducts()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<List<Product>>() {
                override fun onCompleted() {
                    logd("Products Service completed")
                    progressIndicatorVisibility.value = false
                }

                override fun onError(e: Throwable) {
                    loge("Error while loading the Products", e)
                    showError("Not able to retrieve the data. Displaying local data!")
                    progressIndicatorVisibility.value = false
                }

                override fun onNext(products: List<Product>) {
                    Thread(Runnable {
                        products.forEach { product ->
                            product.isUploaded = true
                        }
                        productDao.deleteAllProducts()
                        productDao.saveProducts(products)
                    }).start()
                    logd("Loaded Products")
                }

                override fun onStart() {
                    logd("Loading Products")
                    progressIndicatorVisibility.value = true
                }
            })
    }

    override suspend fun saveProduct(
        product: Product,
        showMessage: (Boolean, String) -> Unit,
        progressIndicatorVisibility: MutableState<Boolean>
    ) {
        productService.addProduct(product)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Subscriber<Product>() {
                override fun onCompleted() {
                    logd("Products Service completed")
                    progressIndicatorVisibility.value = false
                }

                override fun onError(e: Throwable) {
                    loge("Error while persisting a Product", e)
                    showMessage(false, "Not able to connect to the server, will not persist!")
                    progressIndicatorVisibility.value = false
                }

                override fun onNext(product: Product) {
                    Thread(Runnable {
                        product.isUploaded = true
                        productDao.insert(product)
                    }).start()
                    showMessage(true, "Product saved successfully!")
                    logd("Product persisted")
                }

                override fun onStart() {
                    logd("Saving Product $product")
                    progressIndicatorVisibility.value = true
                }
            })
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun saveProductLocally(product: Product) {
        productDao.insert(product)
    }

    override suspend fun syncProducts() {
        productDao.getOfflineProducts().collect { products ->
            products.forEach { product ->
                println(product)
                productService.addProduct(product)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : Subscriber<Product>() {
                        override fun onCompleted() {
                            logd("Products Service completed")
                        }

                        override fun onError(e: Throwable) {
                            loge("Error while persisting a Product", e)
                        }

                        override fun onNext(newProduct: Product) {
                            Thread(Runnable {
                                newProduct.isUploaded = true
                                productDao.delete(newProduct)
                                productDao.insert(newProduct)
                            }).start()
                            logd("Product persisted")
                        }

                        override fun onStart() {
                            logd("Saving Product $product")
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