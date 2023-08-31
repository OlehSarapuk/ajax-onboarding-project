package com.example.ajaxonboardingproject.config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.cglib.proxy.Enhancer
import org.springframework.cglib.proxy.MethodInterceptor
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

const val MAX_MEMORY_USAGE: Long = 1

@Component
@Suppress("SpreadOperator")
class MemoryCheckBeanPostProcessor : BeanPostProcessor {
    val map: MutableMap<String, Class<*>> = mutableMapOf()

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
                val before = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                val result = method.invoke(bean, *args)
                val after = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
                if ((after - before) > MAX_MEMORY_USAGE) {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Memory usage is too high")
                }
                result
            })
            return enhancer.create()

        }
        return bean
    }
}
