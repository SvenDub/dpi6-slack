package nl.svendubbeld.fontys.slack.shared

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("nl.svendubbeld.fontys.slack.shared.entity")
@EnableJpaRepositories("nl.svendubbeld.fontys.slack.shared.repository")
class EntityConfiguration
