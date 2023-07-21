package surajgiri.swipe.listproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import surajgiri.core.data.response.ApiResponse
import surajgiri.core.model.Product
import surajgiri.swipe.listproduct.repository.ListProductRepository

class ListProductViewModel(
    private val listProductRepository: ListProductRepository
) : ViewModel() {

    private val _products = MutableLiveData<ApiResponse<List<Product>>>()
    val products: LiveData<ApiResponse<List<Product>>> = _products

    fun getProducts() {
        viewModelScope.launch {
            _products.value = ApiResponse.Loading
            val response = listProductRepository.getProducts()
            _products.value = response
        }
    }
}
