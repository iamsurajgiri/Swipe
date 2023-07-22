package surajgiri.swipe.listproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import surajgiri.core.data.repository.ListProductRepository
import surajgiri.core.data.response.ProductResponse
import surajgiri.core.model.ListProductResponse

class ListProductViewModel(
    private val listProductRepository: ListProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<ProductResponse<ListProductResponse>>()
    val products: LiveData<ProductResponse<ListProductResponse>> = _products

    fun getProducts() {
        _products.value = ProductResponse.Loading
        viewModelScope.launch {
            try {
                val response = listProductRepository.getProducts()
                if (response.isSuccessful) {
                    _products.value = ProductResponse.Success(response.body()!!)
                } else {
                    _products.value = ProductResponse.Error(response.message())
                }
            } catch (e: Exception) {
                _products.value = ProductResponse.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
