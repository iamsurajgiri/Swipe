package surajgiri.core.data.repository

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import surajgiri.core.data.api.ProductService
import surajgiri.core.model.AddProductResponse
import java.io.File

class AddProductRepository(
    private val productService: ProductService
) {
    suspend fun addProduct(
        productName: String,
        productType: String,
        price: String,
        tax: String,
        image: File?
    ): Response<AddProductResponse> {
        val nameRB =
            productName.toRequestBody("text/plain".toMediaTypeOrNull())
        val typeRB =
            productType.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceRB = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val taxRB = tax.toRequestBody("text/plain".toMediaTypeOrNull())
        val requestImage = image?.asRequestBody("image/*".toMediaTypeOrNull())
        val imageRB =
            requestImage?.let { MultipartBody.Part.createFormData("files[]", image.name, it) }
        return productService.addProduct(nameRB, typeRB, priceRB, taxRB, imageRB)
    }
}
