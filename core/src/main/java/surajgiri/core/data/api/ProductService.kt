package surajgiri.core.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import surajgiri.core.model.AddProductResponse
import surajgiri.core.model.ListProductResponse

interface ProductService {
    @GET("public/get")
    suspend fun getProducts(): Response<ListProductResponse>
    @Multipart
    @POST("public/add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<AddProductResponse>
}
