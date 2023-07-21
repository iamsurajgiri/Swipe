package surajgiri.core.data.response

sealed class ApiResponse<out T> {
    class Success<out T>(val data: T) : ApiResponse<T>()
    class Error(val message: String) : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
}
