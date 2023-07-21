package surajgiri.swipe.addproduct.repository

import okhttp3.MultipartBody
import retrofit2.Response
import surajgiri.core.data.api.ApiService
import surajgiri.core.model.AddProductResponse

class AddProductRepository(
    private val apiService: ApiService
) {
    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: MultipartBody.Part?
    ): Response<AddProductResponse> {
        return apiService.addProduct(productName, productType, price, tax, image)
    }
}
