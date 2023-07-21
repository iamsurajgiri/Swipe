package surajgiri.swipe.addproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import surajgiri.core.data.response.ApiResponse
import surajgiri.core.model.AddProductResponse
import surajgiri.swipe.addproduct.repository.AddProductRepository

class AddProductViewModel(
    private val addProductRepository: AddProductRepository
) : ViewModel() {

    private val _addProductResponse = MutableLiveData<ApiResponse<AddProductResponse>>()
    val addProductResponse: LiveData<ApiResponse<AddProductResponse>> = _addProductResponse

    fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: MultipartBody.Part?
    ) {
        _addProductResponse.value = ApiResponse.Loading
        viewModelScope.launch {
            try {
                val response = addProductRepository.addProduct(productName, productType, price, tax, image)
                if (response.isSuccessful) {
                    _addProductResponse.value = ApiResponse.Success(response.body()!!)
                } else {
                    _addProductResponse.value = ApiResponse.Error(response.message())
                }
            } catch (e: Exception) {
                _addProductResponse.value = ApiResponse.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
