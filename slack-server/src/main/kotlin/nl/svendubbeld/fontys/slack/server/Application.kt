package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.shared.Message
import nl.svendubbeld.fontys.slack.shared.RECEIVE_EXCHANGE
import nl.svendubbeld.fontys.slack.shared.RECEIVE_QUEUE
import nl.svendubbeld.fontys.slack.shared.SEND_QUEUE
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.amqp.support.converter.SimpleMessageConverter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
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
    fun receiveExchange(): TopicExchange {
        return TopicExchange(RECEIVE_EXCHANGE)
    }

    @Bean
    fun container(connectionFactory: ConnectionFactory, listener: MessageListener): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(SEND_QUEUE)
        container.setMessageListener(listener)
        return container
    }

    @Bean
    fun listener(receiver: Receiver, messageConverter: MessageConverter): MessageListener = MessageListener {
        println(it.messageProperties.receivedRoutingKey)
        val body = messageConverter.fromMessage(it) as Message
        println(body)
    }

    @Bean
    fun messageConverter(): MessageConverter = SimpleMessageConverter()
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
