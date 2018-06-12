package nl.svendubbeld.fontys.slack.server.messaging

import nl.svendubbeld.fontys.slack.shared.repository.GroupRepository
import nl.svendubbeld.fontys.slack.shared.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Component

@Component
class MessageRouter(
        private val messageConverter: MessageConverter,
        private val userRepository: UserRepository,
        private val groupRepository: GroupRepository,
        private val messageSender: MessageSender) {

    private val logger = LoggerFactory.getLogger(MessageRouter::class.java)

    fun routeMessage(it: Message) {
        val message = messageConverter.fromMessage(it) as nl.svendubbeld.fontys.slack.shared.Message
        val destination = it.messageProperties.receivedRoutingKey

        if (destination.startsWith("user.")) {
            logger.info("Found routing key for user $destination")
            userRepository.findByKey(destination.substringAfter("user."))?.let {
                val routingKey = "user.${it.key}.user.${message.sender}"
                logger.info("Routing to $routingKey")

                messageSender.sendMessage(routingKey, message)
            }
        } else if (destination.startsWith("group.")) {
            logger.info("Found routing key for group $destination")
            groupRepository.findByKey(destination.substringAfter("group."))?.let {
                logger.info("Found group ${it.name} (${it.key})")
                it.users.forEach {
                    if (it.key != message.sender) {
                        val routingKey = "user.${it.key}.${message.destination}"
                        logger.info("Routing to $routingKey")

                        messageSender.sendMessage(routingKey, message)
                    }
                }
            }
        }
    }
}
