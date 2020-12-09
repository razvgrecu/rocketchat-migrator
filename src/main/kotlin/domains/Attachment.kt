package domains

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(@SerialName("title_link") val link: String = "", val title: String = "")