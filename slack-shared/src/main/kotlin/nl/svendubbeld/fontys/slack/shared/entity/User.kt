package nl.svendubbeld.fontys.slack.shared.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*

@Entity(name = "user_table")
data class User(
        override var name: String,
        @Column(unique = true)
        override var key: String,
        @ManyToMany
        @JsonBackReference
        var groups: List<Group> = emptyList(),
        @Id
        @GeneratedValue
        override var id: Int? = null,
        override val type: String = "user") : Destination
