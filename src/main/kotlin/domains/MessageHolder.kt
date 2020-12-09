package domains

import kotlinx.serialization.Serializable

@Serializable
data class MessageHolder(val messages: List<Message>)