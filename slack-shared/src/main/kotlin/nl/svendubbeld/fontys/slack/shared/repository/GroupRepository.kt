package nl.svendubbeld.fontys.slack.shared.repository

import nl.svendubbeld.fontys.slack.shared.entity.Group
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : CrudRepository<Group, Int?> {
    fun findByKey(key: String): Group?
}
