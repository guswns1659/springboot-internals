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
        testCompileOnly ("org.projectlombok:lombok")
        testAnnotationProcessor ("org.projectlombok:lombok")

        // h2
        runtimeOnly("com.h2database:h2:1.4.200")
    }
    tasks {
        "test"(Test::class) {
            useJUnitPlatform()
        }
    }
}

configure(subprojects.filter { it.name == "springdb" }) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-jdbc")
        implementation("mysql:mysql-connector-java:8.0.25")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

configure(subprojects.filter {it.name == "springdb2"}) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
        implementation("org.springframework.boot:spring-boot-starter-web")

        // jdbcTemplate - jpa와 중복
//        implementation("org.springframework.boot:spring-boot-starter-jdbc")
        // mybatis
        implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
        // jpa
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        //Querydsl 추가
        implementation ("com.querydsl:querydsl-jpa")
        annotationProcessor ("com.querydsl:querydsl-apt:${dependencyManagement.importedProperties["querydsl.version"]}:jpa")
        annotationProcessor ("jakarta.annotation:jakarta.annotation-api")
        annotationProcessor ("jakarta.persistence:jakarta.persistence-api")

        testImplementation ("org.springframework.boot:spring-boot-starter-test")
    }

    //Querydsl 추가, 자동 생성된 Q클래스 gradle clean으로 제거
    tasks {
        "clean"(Delete::class) {
            delete(file("src/main/generated"))
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
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("redis.clients:jedis")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.kafka:spring-kafka-test")
    }
}

configure(subprojects.filter { it.name == "webmvc" }) {

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("org.springframework.boot:spring-boot-starter-batch:2.5.1")
        // For NioEventLoopGroup
        implementation("io.netty:netty-all:4.1.68.Final")
        implementation("mysql:mysql-connector-java")

        annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

configure(subprojects.filter { it.name == "webflux" }) {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        // For sync DB wrapping test
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    }
}
