package com.example.ajaxonboardingproject.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : GenericFilterBean() {
    override fun doFilter(servletRequest: ServletRequest,
                          servletResponse: ServletResponse,
                          filterChain: FilterChain) {
        val token : String? = jwtTokenProvider.resolveToken(servletRequest as HttpServletRequest)
        if (token != null && jwtTokenProvider.validateToken(token)) {
            val authentication : Authentication = jwtTokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }
}