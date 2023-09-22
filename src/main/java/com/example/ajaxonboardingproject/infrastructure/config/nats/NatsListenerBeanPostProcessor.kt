package com.example.ajaxonboardingproject.infrastructure.config.nats

import com.example.ajaxonboardingproject.infrastructure.nats.NatsController
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class NatsListenerBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            val dispatcher = bean.connection.createDispatcher { message ->
                val response = bean.handle(message)
                bean.connection.publish(message.replyTo, response.block()!!.toByteArray())
            }
            dispatcher.subscribe(bean.subject)
        }
        return bean
    }
}
