package com.example.ajaxonboardingproject.config

import io.grpc.BindableService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers

@Configuration
class AppConfig(
    @Value("\${nats.connection.url}")
    private val natsUrl: String,
    @Value("\${spring.grpc.port}")
    private val grpcPort: Int
) {
    @Autowired
    @Lazy
    private lateinit var bindableServices: List<BindableService>

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun natsConnection(): Connection = Nats.connect(natsUrl)

    @Bean(destroyMethod = "shutdown")
    fun getGrpcServer(): Server {
        val grpcServerBuilder = ServerBuilder
            .forPort(grpcPort)
            .apply {
                bindableServices.forEach { addService(it) }
            }
        return grpcServerBuilder.build().start()
    }

    @Bean
    fun scheduler(): Scheduler = Schedulers.boundedElastic()
}
