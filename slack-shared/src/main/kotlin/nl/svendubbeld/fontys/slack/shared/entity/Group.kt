package nl.svendubbeld.fontys.slack.shared.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity(name = "group_table")
data class Group(
        override var name: String,
        @Column(unique = true)
        override var key: String,
        @ManyToMany(mappedBy = "groups")
        @JsonManagedReference
        var users: List<User> = emptyList(),
        @Id
        @GeneratedValue
        override var id: Int? = null,
        override val type: String = "group") : Destination
