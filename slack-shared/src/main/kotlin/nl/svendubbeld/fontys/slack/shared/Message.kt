package nl.svendubbeld.fontys.slack.shared

import java.io.Serializable

data class Message constructor(var message: String) : Serializable
