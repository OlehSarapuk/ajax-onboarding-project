package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.controller.NatsController
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class NatsListenerBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is NatsController<*>) {
            bean.connection.subscribe(bean.subject)
            val dispatcher = bean.connection.createDispatcher { message ->
                val method = bean.javaClass.methods.first { it.name == "generateReplyForNatsRequest" }
                val response = method.invoke(bean, message) as ByteArray
                bean.connection.publish(message.replyTo, response)
            }
            dispatcher.subscribe(bean.subject)
        }
        return bean
    }
}
