package nl.svendubbeld.fontys.slack.shared

import org.springframework.core.ParameterizedTypeReference

inline fun <reified T: Any> typeRef(): ParameterizedTypeReference<T> = object: ParameterizedTypeReference<T>(){}
