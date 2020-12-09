package domains

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    @SerialName("u") val user: User,
    val msg: String,
    val _updatedAt: String,
    val attachments: List<Attachment> = emptyList()
) {
    override fun toString(): String = buildString {
        append("---")
        appendLine()
        append("**${user.username} wrote on $_updatedAt**")
        appendLine()
        append(msg)
        appendLine()
        if (attachments.isNotEmpty()) {
            append("Attachments:")
            appendLine()
            attachments.forEach { attachment ->
                val link = "(https://chat.cloudflight.io${attachment.link}.jpg)"
                when {
                    attachment.title.isBlank() && attachment.link.isBlank() -> append("")
                    attachment.title.isBlank() -> append("[Link]$link")
                    attachment.link.isBlank() -> append("Title: ${attachment.title}")
                    else -> append("[${attachment.title}]$link")
                }
                appendLine()
            }
        }
    }
}