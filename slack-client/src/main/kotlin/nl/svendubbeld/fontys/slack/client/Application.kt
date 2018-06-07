package nl.svendubbeld.fontys.slack.client

import nl.svendubbeld.fontys.slack.client.amqp.Receiver
import nl.svendubbeld.fontys.slack.shared.RECEIVE_EXCHANGE
import nl.svendubbeld.fontys.slack.shared.RECEIVE_QUEUE
import nl.svendubbeld.fontys.slack.shared.SEND_EXCHANGE
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application(val slackConfiguration: SlackConfiguration) {

    @Bean
    fun receiveQueue(): Queue = Queue("$RECEIVE_QUEUE.${slackConfiguration.user}")

    @Bean
    fun sendExchange(): TopicExchange = TopicExchange(SEND_EXCHANGE)

    @Bean
    fun receiveExchange(): TopicExchange = TopicExchange(RECEIVE_EXCHANGE)

    @Bean
    fun binding(receiveQueue: Queue, receiveExchange: TopicExchange): Binding {
        return BindingBuilder.bind(receiveQueue).to(receiveExchange).with("user.${slackConfiguration.user}.#")
    }

    @Bean
    fun container(connectionFactory: ConnectionFactory, listenerAdapter: MessageListenerAdapter):SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer()
        container.connectionFactory = connectionFactory
        container.setQueueNames(receiveQueue().name)
        container.setMessageListener(listenerAdapter)
        return container
    }

    @Bean
    fun listenerAdapter(receiver: Receiver): MessageListenerAdapter {
        return MessageListenerAdapter(receiver, "receiveMessage")
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
