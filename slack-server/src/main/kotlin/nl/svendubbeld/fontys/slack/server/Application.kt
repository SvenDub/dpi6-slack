package nl.svendubbeld.fontys.slack.server

import nl.svendubbeld.fontys.slack.shared.Message
import nl.svendubbeld.fontys.slack.shared.RECEIVE_EXCHANGE
import nl.svendubbeld.fontys.slack.shared.SEND_EXCHANGE
import nl.svendubbeld.fontys.slack.shared.SEND_QUEUE
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
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
    fun sendQueue(): Queue = Queue(SEND_QUEUE)

    @Bean
    fun sendExchange(): TopicExchange = TopicExchange(SEND_EXCHANGE)

    @Bean
    fun receiveExchange(): TopicExchange = TopicExchange(RECEIVE_EXCHANGE)

    @Bean
    fun binding(sendQueue: Queue, sendExchange: TopicExchange): Binding {
        return BindingBuilder.bind(sendQueue).to(sendExchange).with("#")
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
    fun listener(messageConverter: MessageConverter, rabbitTemplate: RabbitTemplate): MessageListener = MessageListener {
        val message = messageConverter.fromMessage(it) as Message
        val destination = it.messageProperties.receivedRoutingKey

        rabbitTemplate.convertAndSend(RECEIVE_EXCHANGE, destination, message)
    }

    @Bean
    fun messageConverter(): MessageConverter = SimpleMessageConverter()
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
