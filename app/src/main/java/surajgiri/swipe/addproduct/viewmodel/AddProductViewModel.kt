package surajgiri.swipe.addproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import surajgiri.core.model.AddProductResponse
import surajgiri.core.data.response.ApiResponse
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
        viewModelScope.launch {
            _addProductResponse.value = ApiResponse.Loading
            val response = addProductRepository.addProduct(productName, productType, price, tax, image)
            _addProductResponse.value = response
        }
    }
}
