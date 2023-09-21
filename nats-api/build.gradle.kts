import com.google.protobuf.gradle.id

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
    implementation("io.projectreactor:reactor-core:3.5.10")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("io.grpc:grpc-netty:1.58.0")
    implementation("io.grpc:grpc-stub:1.58.0")
    implementation("com.salesforce.servicelibs:rxgrpc-stub:0.9.0")
    implementation("com.salesforce.servicelibs:reactor-grpc:1.2.4")
    implementation("com.salesforce.servicelibs:reactive-grpc-common:1.2.4")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:1.2.4")
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
