package surajgiri.core.data.response

import android.util.Log
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class NetworkResponseAdapter(
    private val successType: Type
) : CallAdapter<Type, Call<ApiResponse<Type>>> {

    override fun responseType() = successType

    override fun adapt(call: Call<Type>): Call<ApiResponse<Type>> {
        return NetworkResponseCall(call)
    }

    class NetworkResponseCall(
        private val delegate: Call<Type>
    ) : Call<ApiResponse<Type>> {

        override fun enqueue(callback: Callback<ApiResponse<Type>>) {
            delegate.enqueue(object : Callback<Type> {
                override fun onResponse(call: Call<Type>, response: Response<Type>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            val successResponse = ApiResponse.Success(body)
                            callback.onResponse(this@NetworkResponseCall, Response.success(successResponse))
                        } else {
                            val successResponse = ApiResponse.Error("Empty response")
                            callback.onResponse(this@NetworkResponseCall, Response.success(successResponse))
                        }
                    } else {
                        val successResponse = ApiResponse.Error("Unsuccessful response")
                        callback.onResponse(this@NetworkResponseCall, Response.success(successResponse))
                    }
                }

                override fun onFailure(call: Call<Type>, t: Throwable) {
                    Log.i("NetworkResponseCall", "onFailure: ${t.message}")
                    val successResponse = ApiResponse.Error(t.message ?: "Unknown error")
                    callback.onResponse(this@NetworkResponseCall, Response.success(successResponse))
                }
            })
        }

        override fun isExecuted() = delegate.isExecuted

        override fun clone() = NetworkResponseCall(delegate.clone())

        override fun isCanceled() = delegate.isCanceled

        override fun cancel() = delegate.cancel()

        override fun execute(): Response<ApiResponse<Type>> {
            throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
        }

        override fun request(): Request = delegate.request()

        override fun timeout(): Timeout = delegate.timeout()
    }
}
