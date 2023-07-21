package surajgiri.core.data.response

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class ApiResponseDeserializer : JsonDeserializer<ApiResponse<*>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ApiResponse<*> {
        val jsonObject = json?.asJsonObject ?: throw JsonParseException("Invalid JSON format")

        return when {
            jsonObject.has("data") -> {
                val data = context?.deserialize<Any>(jsonObject.get("data"), typeOfT)
                ApiResponse.Success(data)
            }
            jsonObject.has("message") -> {
                val message = jsonObject.get("message").asString
                ApiResponse.Error(message)
            }
            else -> ApiResponse.Loading
        }
    }
}
