package com.example.ajaxonboardingproject.config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.cglib.proxy.Enhancer
import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

const val MAX_BYTES_PAYLOAD_SIZE: Long = 100

@Component
@Suppress("SpreadOperator")
class MemoryCheckBeanPostProcessor : BeanPostProcessor {
    private val map: MutableMap<String, Class<*>> = mutableMapOf()

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean.javaClass.isAnnotationPresent(RestController::class.java)) {
            map[beanName] = bean.javaClass
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val originalBeanClass = map[beanName]
        if (originalBeanClass != null) {
            val enhancer = Enhancer()
            enhancer.setSuperclass(originalBeanClass)
            enhancer.setCallback(MethodInterceptor { _, method, args, _ ->
                val payloadSize = 0
                if (payloadSize > MAX_BYTES_PAYLOAD_SIZE) {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload byte size is too high")
                }
                method.invoke(bean, *args)
            })
            return enhancer.create()

        }
        return bean
    }
}
