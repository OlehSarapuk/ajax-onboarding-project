package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.nats.NatsController
import io.nats.client.Message
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class NatsListenerBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            val dispatcher = bean.connection.createDispatcher { message ->
                setIdFieldIfBeanHasIt(bean, message)
                val response = bean.handle(message)
                bean.connection.publish(message.replyTo, response.toByteArray())
            }
            dispatcher.subscribe(bean.subject)
        }
        return bean
    }

    private fun setIdFieldIfBeanHasIt(bean: NatsController<*, *>, message: Message) {
        runCatching {
            val idField = bean::class.java.getDeclaredField("id")
            idField.isAccessible = true
            idField.set(bean, message.subject.substringAfterLast("."))
        }.onFailure {  }
    }
}
