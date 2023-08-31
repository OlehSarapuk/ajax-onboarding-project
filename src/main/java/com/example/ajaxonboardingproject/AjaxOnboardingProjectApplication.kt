package com.example.ajaxonboardingproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@Suppress("UtilityClassWithPublicConstructor")
class AjaxOnboardingProjectApplication {
    companion object {
        @JvmStatic
        @Suppress("SpreadOperator")
        fun main(args: Array<String>) {
            runApplication<AjaxOnboardingProjectApplication>(*args)
        }
    }
}
