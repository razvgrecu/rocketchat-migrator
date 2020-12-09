import domains.MessageHolder
import tools.EnvironmentTools
import tools.RequestTools
import tools.SerializationTools
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Paths
import java.util.regex.Pattern


fun main() {
    checkEnvironmentVariablesAreSet()

    val client = HttpClient.newHttpClient()

    val responses = (0..4).map { page ->
        val request = HttpRequest
            .newBuilder(RequestTools.buildPageableListRoomRequestURI(page, EnvironmentTools.PAGE_SIZE))
            .header(RequestTools.AUTH_TOKEN_HEADER, EnvironmentTools.AUTHENTICATION_TOKEN)
            .header(RequestTools.USER_HEADER, EnvironmentTools.USER_ID)
            .header("Content-Type", "application/json")
            .GET()
            .build()

        client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    checkStatus(responses)

    val content = responses
        .map<HttpResponse<String>, MessageHolder>(SerializationTools::fromResponse)
        .flatMap(MessageHolder::messages)
        .joinToString(separator = "\n")
        .replace(Pattern.compile("[`]{3}").toRegex(), "`")
        .replace(Pattern.compile("[<]").toRegex(), "&lt")
        .replace(Pattern.compile("[>]").toRegex(), "&gt")

    if (EnvironmentTools.OUTPUT_FILE_NAME.isBlank()) {
        println("Output environment variable [OUTPUT_FILE_NAME] was not set. Defaulting to console output.")
        println(content)
    } else {
        Files.writeString(Files.createFile(Paths.get(EnvironmentTools.OUTPUT_FILE_NAME)), content)
    }
}

private fun checkEnvironmentVariablesAreSet() = with(EnvironmentTools) {
    require(INSTANCE_NAME.isNotBlank()) { "Instance name environment property (INSTANCE_NAME) was not set." }
    require(AUTHENTICATION_TOKEN.isNotBlank()) { "Auth token environment property (AUTH_TOKEN) was not set." }
    require(USER_ID.isNotBlank()) { "User id environment property (USER_ID) was not set." }
    require(ROOM_ID.isNotBlank()) { "Room environment property (ROOM_ID) was not set." }
}

private fun checkStatus(responses: List<HttpResponse<*>>) = require(responses.all { it.statusCode() == 200 }) {
    """ Not all requests were successfull. 
        Failed Requests: ${responses.filter { it.statusCode() != 200 }.map { it.request().uri().toASCIIString() }}
    """.trimIndent()
}