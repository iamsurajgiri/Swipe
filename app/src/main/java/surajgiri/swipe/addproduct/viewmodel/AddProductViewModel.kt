package surajgiri.swipe.addproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import surajgiri.core.data.repository.AddProductRepository
import surajgiri.core.data.response.ProductResponse
import surajgiri.core.model.AddProductResponse
import java.io.File

class AddProductViewModel(
    private val addProductRepository: AddProductRepository
) : ViewModel() {

    private val _addProductResponse = MutableLiveData<ProductResponse<AddProductResponse>>()
    val addProductResponse: LiveData<ProductResponse<AddProductResponse>> = _addProductResponse

    fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: File?
    ) {
        _addProductResponse.value = ProductResponse.Loading
        viewModelScope.launch {
            try {
                val response = addProductRepository.addProduct(productName, productType, price, tax, image)
                if (response.isSuccessful) {
                    _addProductResponse.value = ProductResponse.Success(response.body()!!)
                } else {
                    _addProductResponse.value = ProductResponse.Error(response.message())
                }
            } catch (e: Exception) {
                _addProductResponse.value = ProductResponse.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
