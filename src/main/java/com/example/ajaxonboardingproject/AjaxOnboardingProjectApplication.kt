package com.example.ajaxonboardingproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.web.reactive.config.EnableWebFlux

@EnableWebFlux
@EnableWebFluxSecurity
@SpringBootApplication
class AjaxOnboardingProjectApplication

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<AjaxOnboardingProjectApplication>(*args)
}
