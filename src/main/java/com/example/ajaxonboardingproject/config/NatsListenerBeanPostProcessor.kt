package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.nats.NatsController
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class NatsListenerBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is NatsController<*, *>) {
            bean.connection.subscribe(bean.subject)
            val dispatcher = bean.connection.createDispatcher { message ->
                val response = bean.handle(message)
                bean.connection.publish(message.replyTo, response.toByteArray())
            }
            dispatcher.subscribe(bean.subject)
        }
        return bean
    }
}