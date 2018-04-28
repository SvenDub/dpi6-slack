package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.shared.RECEIVE_QUEUE
import nl.svendubbeld.fontys.slack.shared.SEND_QUEUE
import nl.svendubbeld.fontys.slack.shared.TOPIC_EXCHANGE
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun sendQueue(): Queue {
        return Queue(SEND_QUEUE)
    }

    @Bean
    fun receiveQueue(): Queue {
        return Queue(RECEIVE_QUEUE)
    }

    @Bean
    fun exchange(): TopicExchange {
        return TopicExchange(TOPIC_EXCHANGE)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}