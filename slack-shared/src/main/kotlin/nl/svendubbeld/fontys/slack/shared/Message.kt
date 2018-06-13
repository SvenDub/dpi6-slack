package nl.svendubbeld.fontys.slack.shared

import java.io.Serializable
import java.time.OffsetDateTime
import javax.persistence.Embeddable

@Embeddable
data class Message constructor(var content: String, var date: OffsetDateTime = OffsetDateTime.now(), var sender: String = "", var destination: String = "") : Serializable
