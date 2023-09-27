package com.example.ajaxonboardingproject

import com.example.ajaxonboardingproject.application.repository.CinemaHallRepositoryOutPort
import com.example.ajaxonboardingproject.domain.CinemaHall
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class CinemaHallRepositoryOutPortTests {
    @Autowired
    private lateinit var cinemaHallRepositoryOutPort: CinemaHallRepositoryOutPort

    @BeforeEach
    fun clearCollection() {
        cinemaHallRepositoryOutPort.deleteAll().block()
    }

    @Test
    fun saveCinemaHallToDbTestOk() {
        //Given
        val expected = CinemaHall(
            id = null,
            capacity = 100,
            description = "grate one"
        )
        //When
        val actual: Mono<CinemaHall> = cinemaHallRepositoryOutPort.save(expected)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
            .verifyComplete()
    }

    @Test
    fun findCinemaHallByIdFromDBTestOk() {
        //Given
        val cinemaHall = CinemaHall(
            id = null,
            capacity = 100,
            description = "grate one"
        )
        val expected: CinemaHall = cinemaHallRepositoryOutPort.save(cinemaHall).block()!!
        //When
        val actual: Mono<CinemaHall> = cinemaHallRepositoryOutPort.findById(expected.id!!)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
            .verifyComplete()
    }

    @Test
    fun findAllCinemaHallsFromDBTestOk() {
        // Given
        val cinemaHall1 = CinemaHall(
            id = null,
            capacity = 100,
            description = "great one"
        )
        val cinemaHall2 = CinemaHall(
            id = null,
            capacity = 150,
            description = "awesome one"
        )
        val expectedCinemaHall1 = cinemaHallRepositoryOutPort.save(cinemaHall1).block()!!
        val expectedCinemaHall2 = cinemaHallRepositoryOutPort.save(cinemaHall2).block()!!
        //When
        val actual: Flux<CinemaHall> = cinemaHallRepositoryOutPort.findAll()
        //Then
        StepVerifier.create(actual)
            .expectNext(expectedCinemaHall1)
            .expectNext(expectedCinemaHall2)
            .verifyComplete()
    }
}
