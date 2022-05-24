package app.exam.ownerSectionScreen.usecase

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import app.exam.ownerSectionScreen.domain.Product
import app.exam.ownerSectionScreen.repo.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductsUseCase{
    suspend fun getAllLocalProducts():Flow<List<Product>>
    suspend fun getAllProducts(showError: (String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>)

    suspend fun saveProduct(product: Product, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>)
    suspend fun saveProductLocally(product: Product)

    suspend fun syncProducts()
}

class ProductsUseCaseImpl @Inject constructor(
    val repo: ProductsRepository
) : ProductsUseCase {

    override suspend fun getAllLocalProducts():Flow<List<Product>> {
        return repo.getAllLocalProducts()
    }

    override suspend fun getAllProducts(showError: (String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        repo.loadAllProducts(showError, progressIndicatorVisibility)
    }

    override suspend fun saveProduct(product: Product, showMessage: (Boolean, String) -> Unit, progressIndicatorVisibility : MutableState<Boolean>) {
        repo.saveProduct(product, showMessage, progressIndicatorVisibility)
    }

    override suspend fun saveProductLocally(product: Product) {
        repo.saveProductLocally(product)
    }

    override suspend fun syncProducts() {
        repo.syncProducts()
    }
}
