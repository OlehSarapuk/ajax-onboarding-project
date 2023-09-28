package com.example.ajaxonboardingproject

import com.example.ajaxonboardingproject.application.repository.RedisCinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class RedisCinemaHallRepositoryOutPortTests {
    @Autowired
    private lateinit var redisCinemaHallRepositoryOutPort: RedisCinemaHallRepositoryOutPort

    @Test
    fun saveCinemaHallToDbTestOk() {
        //Given
        val expected = CinemaHall(
            id = "id1",
            capacity = 100,
            description = "grate one"
        )
        //When
        val actual: Mono<CinemaHall> = redisCinemaHallRepositoryOutPort.save(expected)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
            .verifyComplete()
    }

    @Test
    fun findCinemaHallByIdFromDBTestOk() {
        //Given
        val cinemaHall = CinemaHall(
            id = "id1",
            capacity = 100,
            description = "grate one"
        )
        redisCinemaHallRepositoryOutPort.save(cinemaHall).block()!!
        //When
        val actual: Mono<CinemaHall> = redisCinemaHallRepositoryOutPort.findById(cinemaHall.id!!)
        //Then
        StepVerifier.create(actual)
            .expectNext(cinemaHall)
            .verifyComplete()
    }
}