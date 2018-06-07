package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.server.repository.GroupRepository
import nl.svendubbeld.fontys.slack.server.repository.UserRepository
import nl.svendubbeld.fontys.slack.shared.RECEIVE_EXCHANGE
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class MessageListener(
        private val messageConverter: MessageConverter,
        private val rabbitTemplate: RabbitTemplate,
        private val userRepository: UserRepository,
        private val groupRepository: GroupRepository) : MessageListener {

    private val logger = LoggerFactory.getLogger(MessageListener::class.java)

    @Transactional
    override fun onMessage(it: Message) {
        val message = messageConverter.fromMessage(it) as nl.svendubbeld.fontys.slack.shared.Message
        val destination = it.messageProperties.receivedRoutingKey

        if (destination.startsWith("user.")) {
            logger.info("Found routing key for user $destination")
            userRepository.findByKey(destination.substringAfter("user."))?.let {
                val routingKey = "user.${it.key}.user.${message.sender}"
                logger.info("Routing to $routingKey")
                rabbitTemplate.convertAndSend(RECEIVE_EXCHANGE, routingKey, message)
            }
        } else if (destination.startsWith("group.")) {
            logger.info("Found routing key for group $destination")
            groupRepository.findByKey(destination.substringAfter("group."))?.let {
                logger.info("Found group ${it.name} (${it.key})")
                it.users.forEach {
                    if (it.key != message.sender) {
                        val routingKey = "user.${it.key}.${message.destination}"
                        logger.info("Routing to $routingKey")
                        rabbitTemplate.convertAndSend(RECEIVE_EXCHANGE, routingKey, message)
                    }
                }
            }
        }
    }
}
