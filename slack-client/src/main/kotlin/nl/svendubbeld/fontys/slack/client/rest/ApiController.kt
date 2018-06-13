package nl.svendubbeld.fontys.slack.client.rest

import nl.svendubbeld.fontys.slack.client.LocalMessage
import nl.svendubbeld.fontys.slack.client.SlackConfiguration
import nl.svendubbeld.fontys.slack.client.repository.LocalMessageRepository
import nl.svendubbeld.fontys.slack.shared.entity.Destination
import nl.svendubbeld.fontys.slack.shared.entity.Group
import nl.svendubbeld.fontys.slack.shared.entity.User
import nl.svendubbeld.fontys.slack.shared.typeRef
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api")
class ApiController(private val slackConfiguration: SlackConfiguration, private var repository: LocalMessageRepository) {

    @GetMapping("/destinations")
    fun getGroups(): ResponseEntity<List<Destination>> {
        val restTemplate = RestTemplate()
        val groupsResponse = restTemplate.exchange("http://localhost:8080/api/user/{user}/groups", HttpMethod.GET, null, typeRef<List<Group>>(), slackConfiguration.user)
        val usersResponse = restTemplate.exchange("http://localhost:8080/api/user", HttpMethod.GET, null, typeRef<List<User>>())

        return ResponseEntity.ok(listOfNotNull(groupsResponse.body, usersResponse.body).flatten())
    }

    @GetMapping("/messages")
    fun getMessages(): ResponseEntity<Iterable<LocalMessage>> {
        return ResponseEntity.ok(repository.findAll())
    }

}
