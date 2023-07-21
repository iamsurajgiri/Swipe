package surajgiri.swipe.listproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import surajgiri.core.data.response.ApiResponse
import surajgiri.core.model.ListProductResponse
import surajgiri.swipe.listproduct.repository.ListProductRepository

class ListProductViewModel(
    private val listProductRepository: ListProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<ApiResponse<ListProductResponse>>()
    val products: LiveData<ApiResponse<ListProductResponse>> = _products

    fun getProducts() {
        _products.value = ApiResponse.Loading
        viewModelScope.launch {
            try {
                val response = listProductRepository.getProducts()
                if (response.isSuccessful) {
                    _products.value = ApiResponse.Success(response.body()!!)
                } else {
                    _products.value = ApiResponse.Error(response.message())
                }
            } catch (e: Exception) {
                _products.value = ApiResponse.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
