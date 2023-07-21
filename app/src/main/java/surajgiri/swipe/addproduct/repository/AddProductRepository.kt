package surajgiri.swipe.addproduct.repository

import okhttp3.MultipartBody
import surajgiri.core.data.api.ApiService
import surajgiri.core.model.AddProductResponse
import surajgiri.core.data.response.ApiResponse

class AddProductRepository(
    private val apiService: ApiService
) {
    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: MultipartBody.Part?
    ): ApiResponse<AddProductResponse> {
        return apiService.addProduct(productName, productType, price, tax, image)
    }
}
