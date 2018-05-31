package nl.svendubbeld.fontys.slack.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("slack")
class SlackConfiguration {
    lateinit var user: String
}
