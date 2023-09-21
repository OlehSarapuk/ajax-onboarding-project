package com.example.ajaxonboardingproject.config

import io.grpc.Server
import org.springframework.stereotype.Component

@Component
class GrpcServer(
    private val grpcServer: Server
) {

    fun shutDownServer() {
        grpcServer.shutdown()
    }
}
