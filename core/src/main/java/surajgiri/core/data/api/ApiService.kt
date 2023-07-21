package surajgiri.core.data.api

import okhttp3.MultipartBody
import retrofit2.http.*
import surajgiri.core.model.AddProductResponse
import surajgiri.core.data.response.ApiResponse
import surajgiri.core.model.Product

interface ApiService {
    @GET("public/get")
    suspend fun getProducts(): ApiResponse<List<Product>>

    @Multipart
    @POST("public/add")
    suspend fun addProduct(
        @Part("product_name") productName: String,
        @Part("product_type") productType: String,
        @Part("price") price: String,
        @Part("tax") tax: String,
        @Part image: MultipartBody.Part?
    ): ApiResponse<AddProductResponse>
}
