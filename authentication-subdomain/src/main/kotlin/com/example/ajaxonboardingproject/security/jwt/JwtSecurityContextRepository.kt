package com.example.ajaxonboardingproject.security.jwt

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtSecurityContextRepository(
    private val jwtTokenProvider: JwtTokenProvider
) : ServerSecurityContextRepository {

    override fun save(swe: ServerWebExchange, sc: SecurityContext): Mono<Void> {
        return Mono.error(UnsupportedOperationException("Not supported"))
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        return Mono.fromSupplier {
            jwtTokenProvider.resolveToken(exchange.request as ServerHttpRequest)
        }
            .filter { it != null && jwtTokenProvider.validateToken(it) }
            .flatMap { jwtTokenProvider.getAuthentication(it!!) }
            .map { SecurityContextImpl(it) }
    }
}
