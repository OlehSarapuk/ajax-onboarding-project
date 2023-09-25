package com.example.ajaxonboardingproject

import com.example.ajaxonboardingproject.domain.CinemaHall
import com.example.ajaxonboardingproject.application.repository.CinemaHallRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class CinemaHallRepositoryTests {
    @Autowired
    private lateinit var cinemaHallRepository: CinemaHallRepository

    @BeforeEach
    fun clearCollection() {
        cinemaHallRepository.deleteAll().block()
    }

    @Test
    fun saveCinemaHallToDbTestOk() {
        //Given
        val expected = CinemaHall(
            capacity = 100,
            description = "grate one"
        )
        //When
        val actual: Mono<CinemaHall> = cinemaHallRepository.save(expected)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
            .verifyComplete()
    }

    @Test
    fun findCinemaHallByIdFromDBTestOk() {
        //Given
        val cinemaHall = CinemaHall(
            capacity = 100,
            description = "grate one"
        )
        val expected: CinemaHall = cinemaHallRepository.save(cinemaHall).block()!!
        //When
        val actual: Mono<CinemaHall> = cinemaHallRepository.findById(expected.id)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
            .verifyComplete()
    }

    @Test
    fun findAllCinemaHallsFromDBTestOk() {
        // Given
        val cinemaHall1 = CinemaHall(
            capacity = 100,
            description = "great one"
        )
        val cinemaHall2 = CinemaHall(
            capacity = 150,
            description = "awesome one"
        )
        val expectedCinemaHall1 = cinemaHallRepository.save(cinemaHall1).block()!!
        val expectedCinemaHall2 = cinemaHallRepository.save(cinemaHall2).block()!!
        //When
        val actual: Flux<CinemaHall> = cinemaHallRepository.findAll()
        //Then
        StepVerifier.create(actual)
            .expectNext(expectedCinemaHall1)
            .expectNext(expectedCinemaHall2)
            .verifyComplete()
    }
}