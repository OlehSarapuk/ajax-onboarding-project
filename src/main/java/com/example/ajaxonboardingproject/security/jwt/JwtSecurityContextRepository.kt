package com.example.ajaxonboardingproject.security.jwt

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository

@Component
class JwtSecurityContextRepository(
    private val jwtTokenProvider: JwtTokenProvider
) : ServerSecurityContextRepository {

    override fun save(swe: ServerWebExchange, sc: SecurityContext): Mono<Void> {
        return Mono.error(UnsupportedOperationException("Not supported"))
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val token: String? = jwtTokenProvider.resolveToken(exchange.request as ServerHttpRequest)
        return if (token != null && jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.getAuthentication(token)
                .map { SecurityContextImpl(it) }
        } else {
            Mono.empty()
        }
    }
}
