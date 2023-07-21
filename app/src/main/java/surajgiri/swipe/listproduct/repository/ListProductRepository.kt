package surajgiri.swipe.listproduct.repository

import retrofit2.Response
import surajgiri.core.data.api.ApiService
import surajgiri.core.model.ListProductResponse

class ListProductRepository(
    private val apiService: ApiService
) {
    suspend fun getProducts(): Response<ListProductResponse> {
        return apiService.getProducts()
    }
}
