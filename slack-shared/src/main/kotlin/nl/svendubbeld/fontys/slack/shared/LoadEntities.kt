package nl.svendubbeld.fontys.slack.shared

import org.springframework.context.annotation.Import

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@MustBeDocumented
@Import(EntityConfiguration::class)
annotation class LoadEntities
