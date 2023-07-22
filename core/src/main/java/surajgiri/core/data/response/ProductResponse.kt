package surajgiri.core.data.response

sealed class ProductResponse<out T> {

    class Success<out T>(val data: T) : ProductResponse<T>()
    class Error(val message: String) : ProductResponse<Nothing>()
    object Loading : ProductResponse<Nothing>()
}
