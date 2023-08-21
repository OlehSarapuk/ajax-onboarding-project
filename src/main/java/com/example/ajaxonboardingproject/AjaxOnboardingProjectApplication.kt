package com.example.ajaxonboardingproject

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AjaxOnboardingProjectApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AjaxOnboardingProjectApplication::class.java, *args)
        }
    }
}