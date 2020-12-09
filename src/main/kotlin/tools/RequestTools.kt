package tools

import java.net.URI

object RequestTools {
    const val AUTH_TOKEN_HEADER = "X-Auth-Token"
    const val USER_HEADER = "X-User-Id"

    private const val API_ENDPOINT = "/api/v1/groups.history"
    private const val ROOM_ID_PARAMETER = "roomId"
    private const val OFFSET_PARAMETER = "offset"
    private const val SIZE_PARAMETER = "count"

    fun buildPageableListRoomRequestURI(page: Int, size: Int): URI = URI.create(buildString {
        append(EnvironmentTools.INSTANCE_NAME)
        append(API_ENDPOINT)
        append("?$ROOM_ID_PARAMETER=${EnvironmentTools.ROOM_ID}")
        append("&$OFFSET_PARAMETER=$page")
        append("&$SIZE_PARAMETER=$size")
    })
}