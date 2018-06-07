package nl.svendubbeld.fontys.slack.server.repository

import nl.svendubbeld.fontys.slack.server.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int?> {
    fun findByKey(key: String): User?
}
