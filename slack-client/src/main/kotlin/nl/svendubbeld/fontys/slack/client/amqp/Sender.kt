package nl.svendubbeld.fontys.slack.client.amqp

import nl.svendubbeld.fontys.slack.shared.Message
import nl.svendubbeld.fontys.slack.shared.SEND_EXCHANGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class Sender(val rabbitTemplate: RabbitTemplate) {

    fun sendMessage(message: Message, destination: String) {
        rabbitTemplate.convertAndSend(SEND_EXCHANGE, destination, message)
    }
}
