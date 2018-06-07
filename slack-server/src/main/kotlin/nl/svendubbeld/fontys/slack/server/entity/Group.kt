package nl.svendubbeld.fontys.slack.server.entity

import javax.persistence.*

@Entity(name = "group_table")
data class Group(
        var name: String,
        @Column(unique = true)
        var key: String,
        @ManyToMany(mappedBy = "groups")
        var users: List<User> = emptyList(),
        @Id
        @GeneratedValue
        var id: Int? = null)
