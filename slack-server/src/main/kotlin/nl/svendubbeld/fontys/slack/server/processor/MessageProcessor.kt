package nl.svendubbeld.fontys.slack.server.processor

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
@Component
annotation class MessageProcessor

const val MESSAGE_ORDER_TEMPLATE = 5000
const val MESSAGE_ORDER_LOG = 9000
const val MESSAGE_ORDER_DEFAULT = MESSAGE_ORDER_TEMPLATE
