package nl.svendubbeld.fontys.slack.shared.repository

import nl.svendubbeld.fontys.slack.shared.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Int?> {
    fun findByKey(key: String): User?
}
