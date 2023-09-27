package com.example.ajaxonboardingproject.security.jwt

import com.example.ajaxonboardingproject.exception.InvalidJwtAuthenticationException
import com.example.ajaxonboardingproject.model.Role
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.token.secret_key}")
    var secret: String,
    @Value("\${security.jwt.token.expire_length}")
    val validityTime: Long,
    val userDetailsService: ReactiveUserDetailsService
) {
    @PostConstruct
    fun initialize() {
        secret = Base64.getEncoder().encodeToString(secret.toByteArray())
    }

    fun createToken(
        login: String,
        roles: Set<Role>
    ): String {
        val claims: Claims = Jwts.claims().setSubject(login)
        claims["roles"] = roles
        val now = Date()
        val expiration = Date(now.time + validityTime)
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    fun getAuthentication(token: String): Mono<Authentication> {
        return userDetailsService
            .findByUsername(getUserNameFromToken(token))
            .map { UsernamePasswordAuthenticationToken(it, "", it.authorities) }
    }

    fun getUserNameFromToken(token: String): String {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
            .subject
    }

    fun resolveToken(serverRequest: ServerHttpRequest): String? {
        val token = serverRequest.headers.getFirst("Authorization")
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(startIndex = 7)
        }
        return null
    }

    fun validateToken(token: String): Boolean {
        return runCatching {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            claims.body.expiration.after(Date())
        }.getOrElse { throw InvalidJwtAuthenticationException("Expired or invalid JWT token", it) }
    }
}
