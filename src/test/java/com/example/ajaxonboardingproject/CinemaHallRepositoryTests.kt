package com.example.ajaxonboardingproject

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
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
        cinemaHallRepository.deleteAll().subscribe()
    }

    @Test
    fun saveCinemaHallToDbTestOk() {
        //Given
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        //When
        val actual: Mono<CinemaHall> = cinemaHallRepository.save(cinemaHall)
        //Then
        val expected = actual.flatMap {
            cinemaHallRepository.findById(it.id)
                .switchIfEmpty(Mono.error(NoSuchElementException("can't get cinema hall by id ${it.id}")))
        }
        StepVerifier.create(actual)
            .expectNext(expected.block()!!)
            .verifyComplete()
    }

    @Test
    fun findCinemaHallByIdFromDBTestOk() {
        //Given
        val cinemaHall = CinemaHall(capacity = 100, description = "grate one")
        val expected: Mono<CinemaHall> = cinemaHallRepository.save(cinemaHall)
        //When
        val actual: Mono<CinemaHall> = expected.flatMap {
            cinemaHallRepository.findById(it.id)
                .switchIfEmpty(Mono.error(NoSuchElementException("can't get cinema hall by id ${it.id}")))
        }
        //Then
        StepVerifier.create(actual)
            .expectNext(expected.block()!!)
            .verifyComplete()
    }

    @Test
    fun findAllCinemaHallsFromDBTestOk() {
        // Given
        val cinemaHall1 = CinemaHall(capacity = 100, description = "great one")
        val cinemaHall2 = CinemaHall(capacity = 150, description = "awesome one")
        val expectedCinemaHall1 = cinemaHallRepository.save(cinemaHall1)
        val expectedCinemaHall2 = cinemaHallRepository.save(cinemaHall2)
        //When
        val actual: Flux<CinemaHall> = cinemaHallRepository.findAll()
        //Then
        StepVerifier.create(actual)
            .expectNext(expectedCinemaHall2.block()!!)
            .expectNext(expectedCinemaHall2.block()!!)
            .verifyComplete()
    }
}
