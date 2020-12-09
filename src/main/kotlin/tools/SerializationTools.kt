package tools

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.http.HttpResponse

object SerializationTools {
    val json = Json {
        ignoreUnknownKeys = true
    }

    inline fun <reified T> fromResponse(response: HttpResponse<String>): T = json.decodeFromString(response.body())
}