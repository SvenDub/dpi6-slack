package nl.svendubbeld.fontys.slack.server.entity

import javax.persistence.*

@Entity(name = "user_table")
data class User(
        var name: String,
        @Column(unique = true)
        var key: String,
        @ManyToMany
        var groups: List<Group> = emptyList(),
        @Id
        @GeneratedValue
        var id: Int? = null)
