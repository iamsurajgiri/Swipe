package surajgiri.core.data.repository

import retrofit2.Response
import surajgiri.core.data.api.ProductService
import surajgiri.core.model.ListProductResponse

class ListProductRepository(
    private val productService: ProductService
) {
    suspend fun getProducts(): Response<ListProductResponse> {
        return productService.getProducts()
    }
}
