package nl.svendubbeld.fontys.slack.server.repository

import nl.svendubbeld.fontys.slack.server.entity.Group
import org.springframework.data.repository.CrudRepository

interface GroupRepository : CrudRepository<Group, Int?> {
    fun findByKey(key: String): Group?
}
