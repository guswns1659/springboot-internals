plugins {
    java
    id("org.springframework.boot") version "2.5.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    dependencies {
        // lombok
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
    }
    tasks {
        "test"(Test::class) {
            useJUnitPlatform()
        }
    }
}

configure(subprojects.filter { it.name == "log4j2" }) {

    configurations {
        all {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-log4j2")
    }
}

configure(subprojects.filter { it.name == "messaging" }) {

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.kafka:spring-kafka")
        implementation("org.springframework.amqp:spring-rabbit")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("redis.clients:jedis")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.kafka:spring-kafka-test")
    }
}

configure(subprojects.filter { it.name == "webmvc" }) {

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.h2database:h2:1.4.200")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-batch:2.5.1")
        // For NioEventLoopGroup
        implementation("io.netty:netty-all:4.1.68.Final")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

configure(subprojects.filter { it.name == "webflux" }) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
    }
}
