plugins {
    idea
    kotlin("jvm") version "1.9.0"
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.24.2")
    implementation("com.google.protobuf:protobuf-java-util:3.20.1")
    implementation("io.grpc:grpc-stub:1.58.0")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("io.grpc:grpc-netty:1.58.0")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
}

kotlin {
    jvmToolchain(17)
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
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.46.0"
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
