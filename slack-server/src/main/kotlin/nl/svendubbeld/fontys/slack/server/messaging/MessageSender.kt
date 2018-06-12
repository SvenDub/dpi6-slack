package nl.svendubbeld.fontys.slack.server.messaging

import nl.svendubbeld.fontys.slack.shared.Message
import nl.svendubbeld.fontys.slack.shared.RECEIVE_EXCHANGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class MessageSender(private val rabbitTemplate: RabbitTemplate) {

    fun sendMessage(routingKey: String, message: Message) {
        rabbitTemplate.convertAndSend(RECEIVE_EXCHANGE, routingKey, message)
    }
}
