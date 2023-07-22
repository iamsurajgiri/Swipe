package surajgiri.core.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import surajgiri.core.Constants.BASE_URL
import surajgiri.core.data.api.ProductService
import surajgiri.core.data.repository.AddProductRepository
import surajgiri.core.data.repository.ListProductRepository


val productModule = module {
    val gson = GsonBuilder().setLenient().create()

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(ProductService::class.java) }

    single { ListProductRepository(get()) }
    single { AddProductRepository(get()) }
}
