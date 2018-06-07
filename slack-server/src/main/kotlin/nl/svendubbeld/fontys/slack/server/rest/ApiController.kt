package nl.svendubbeld.fontys.slack.server.rest

import nl.svendubbeld.fontys.slack.shared.entity.Group
import nl.svendubbeld.fontys.slack.shared.entity.User
import nl.svendubbeld.fontys.slack.shared.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController(private val userRepository: UserRepository) {

    @GetMapping("/user")
    fun getUsers(): ResponseEntity<List<User>> = ResponseEntity.ok(userRepository.findAll().toList())

    @GetMapping("/user/{key}")
    fun getUser(@PathVariable key: String): ResponseEntity<User> {
        val user = userRepository.findByKey(key)

        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/user/{user}/groups")
    fun getGroups(@PathVariable user: String): ResponseEntity<List<Group>> {
        val groups = userRepository.findByKey(user)?.groups

        return if (groups != null) {
            ResponseEntity.ok(groups)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
