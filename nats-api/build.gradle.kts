import com.google.protobuf.gradle.id

plugins {
    idea
    id("com.google.protobuf") version "0.9.4"
}

object GeneratedFileDir {
    const val java = "/main/java"
    const val base = "/src/generated"
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.20.1"
    }

    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.46.0"
        }
        id("reactor-grpc") {
            artifact = "com.salesforce.servicelibs:reactor-grpc:1.2.4"
        }
    }

    generateProtoTasks {
        all().configureEach {
            generateDescriptorSet = true
            descriptorSetOptions.includeImports = true
        }
        all().forEach {
            it.plugins {
                create("grpc")
                create("reactor-grpc")
            }
        }
    }

    generatedFilesBaseDir = "$projectDir${GeneratedFileDir.base}"
}

idea {
    module {
        sourceDirs = sourceDirs + file("${protobuf.generatedFilesBaseDir}${GeneratedFileDir.java}")
    }
}

tasks {
    clean {
        delete(protobuf.generatedFilesBaseDir)
    }
}
