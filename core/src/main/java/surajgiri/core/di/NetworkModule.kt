package surajgiri.core.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import surajgiri.core.Constants.BASE_URL
import surajgiri.core.data.api.ApiService
import surajgiri.core.data.response.NetworkResponseAdapter
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class NetworkResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        return NetworkResponseAdapter(responseType)
    }
}

val networkModule = module {
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
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }
}
