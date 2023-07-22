package surajgiri.core.data.repository

import okhttp3.MultipartBody
import retrofit2.Response
import surajgiri.core.data.api.ProductService
import surajgiri.core.model.AddProductResponse

class AddProductRepository(
    private val productService: ProductService
) {
    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: MultipartBody.Part?
    ): Response<AddProductResponse> {
        return productService.addProduct(productName, productType, price, tax, image)
    }
}
