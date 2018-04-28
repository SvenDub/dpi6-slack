package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.shared.Message
import nl.svendubbeld.fontys.slack.shared.TOPIC_EXCHANGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class Runner(val rabbitTemplate: RabbitTemplate) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Sending message...")
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, "group.general", Message("Hey there!"))
    }
}
