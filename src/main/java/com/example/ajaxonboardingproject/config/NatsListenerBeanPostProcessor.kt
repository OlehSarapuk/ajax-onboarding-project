package com.example.ajaxonboardingproject.config

import com.example.ajaxonboardingproject.nats.NatsController
import com.example.ajaxonboardingproject.nats.ReactiveNatsHandler
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers

@Component
class NatsListenerBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            val reactiveHandler = ReactiveNatsHandler(bean, Schedulers.boundedElastic())
            bean.connection
                .createDispatcher(reactiveHandler)
                .subscribe(bean.subject)
        }
        return bean
    }
}
