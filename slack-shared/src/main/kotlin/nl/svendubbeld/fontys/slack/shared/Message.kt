package nl.svendubbeld.fontys.slack.shared

import java.io.Serializable
import java.time.OffsetDateTime

data class Message constructor(var content: String, var date: OffsetDateTime = OffsetDateTime.now()) : Serializable
