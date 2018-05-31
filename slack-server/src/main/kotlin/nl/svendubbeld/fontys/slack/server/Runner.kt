package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.shared.Message
import nl.svendubbeld.fontys.slack.shared.RECEIVE_EXCHANGE
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Runner(val rabbitTemplate: RabbitTemplate) {

    private var i = 0

    @Scheduled(fixedRate = 10000)
    fun run() {
        println("Sending message...")
        rabbitTemplate.convertAndSend(RECEIVE_EXCHANGE, "user.svendub", Message("Hey there SvenDub $i!"))
        rabbitTemplate.convertAndSend(RECEIVE_EXCHANGE, "user.other", Message("Hey there Other $i!"))
        i++
    }
}
