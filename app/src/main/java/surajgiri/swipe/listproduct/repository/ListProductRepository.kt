package surajgiri.swipe.listproduct.repository

import surajgiri.core.data.api.ApiService
import surajgiri.core.data.response.ApiResponse
import surajgiri.core.model.Product

class ListProductRepository(
    private val apiService: ApiService
) {
    suspend fun getProducts(): ApiResponse<List<Product>> {
        return apiService.getProducts()
    }
}
