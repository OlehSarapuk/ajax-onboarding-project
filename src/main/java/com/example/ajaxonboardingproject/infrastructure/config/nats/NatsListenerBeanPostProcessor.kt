package com.example.ajaxonboardingproject.infrastructure.config.nats

import com.example.ajaxonboardingproject.infrastructure.nats.ReactiveNatsHandler
import com.example.ajaxonboardingproject.infrastructure.nats.NatsController
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component
import reactor.core.scheduler.Scheduler

@Component
class NatsListenerBeanPostProcessor(
    private val scheduler: Scheduler
) : BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (bean is NatsController<*, *>) {
            val reactiveHandler = ReactiveNatsHandler(bean, scheduler)
            bean.connection
                .createDispatcher(reactiveHandler)
                .subscribe(bean.subject)
        }
        return bean
    }
}
