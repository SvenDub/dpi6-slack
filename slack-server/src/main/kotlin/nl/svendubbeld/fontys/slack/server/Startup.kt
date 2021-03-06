package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.shared.entity.Group
import nl.svendubbeld.fontys.slack.shared.entity.User
import nl.svendubbeld.fontys.slack.shared.repository.GroupRepository
import nl.svendubbeld.fontys.slack.shared.repository.UserRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class Startup(private val userRepository: UserRepository, private val groupRepository: GroupRepository) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val groups = groupRepository.saveAll(listOf(
                Group("General", "general"),
                Group("Other", "other")
        ))

        userRepository.save(User("Sven Dubbeld", "svendub", groups.toList()))
        userRepository.save(User("Daan Tuller", "daantul", listOf(groups.first())))
        userRepository.save(User("Tobi van Bronswijk", "sternold", groups.toList()))
    }
}
