package nl.svendubbeld.fontys.slack.client

import com.fasterxml.jackson.annotation.JsonValue
import nl.svendubbeld.fontys.slack.shared.Message
import javax.persistence.*

@Entity
data class LocalMessage(@Embedded var message: Message, @Enumerated(EnumType.STRING) var type: MessageType, @Id @GeneratedValue var id: Int? = null)

enum class MessageType(@JsonValue val type: String) {
    SENT("sent"),
    RECEIVED("received");
}
