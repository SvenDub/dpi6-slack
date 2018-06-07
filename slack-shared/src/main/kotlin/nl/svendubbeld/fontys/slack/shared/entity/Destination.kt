package nl.svendubbeld.fontys.slack.shared.entity

interface Destination {
    var id: Int?
    var name: String
    var key: String
    val type: String
}
