package nl.svendubbeld.fontys.slack.client

import com.fasterxml.jackson.annotation.JsonValue
import nl.svendubbeld.fontys.slack.shared.Message

data class LocalMessage(var message: Message, var type: MessageType)

enum class MessageType(@JsonValue val type: String) {
    SENT("sent"),
    RECEIVED("received");
}
