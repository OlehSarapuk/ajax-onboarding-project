package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.nats.NatsController
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class NatsListenerBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            val dispatcher = bean.connection.createDispatcher { message ->
                val response = bean.handle(Mono.just(message))
                bean.connection.publish(message.replyTo, response.block()!!.toByteArray())
            }
            dispatcher.subscribe(bean.subject)
        }
        return bean
    }
}
