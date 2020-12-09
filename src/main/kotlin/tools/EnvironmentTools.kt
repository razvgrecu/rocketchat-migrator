package tools

object EnvironmentTools {
    val INSTANCE_NAME: String = System.getenv("INSTANCE_NAME").orEmpty()
    val USER_ID: String = System.getenv("USER_ID").orEmpty()
    val AUTHENTICATION_TOKEN: String = System.getenv("AUTH_TOKEN").orEmpty()
    val ROOM_ID: String = System.getenv("ROOM_ID").orEmpty()
    val OUTPUT_FILE_NAME: String = System.getenv("OUTPUT_FILE_NAME").orEmpty()
    const val PAGE_SIZE = 500
}