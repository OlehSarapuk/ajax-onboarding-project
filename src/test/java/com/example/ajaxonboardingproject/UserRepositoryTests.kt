package com.example.ajaxonboardingproject

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.Role
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@SpringBootTest
class UserRepositoryTests {
    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun clearCollection() {
        userRepository.deleteAll().subscribe()
    }

    @Test
    fun saveUserToDbTestOk() {
        //Given
        val user = User(
            email = "bob@gmail.com",
            password = "bob1234",
            roles = mutableSetOf(Role.USER),
            shoppingCart = ShoppingCart()
        )
        //When
        val actual: Mono<User> = userRepository.save(user)
        //Then
        val expected = actual.flatMap {
            userRepository.findById(it.id)
                .switchIfEmpty(Mono.error(NoSuchElementException("can't get user by id ${it.id}")))
        }
        StepVerifier.create(actual)
            .expectNext(expected.block()!!)
            .verifyComplete()
    }

    @Test
    fun findUserByIdFromDBTestOk() {
        //Given
        val user = User(
            email = "bob@gmail.com",
            password = "bob1234",
            roles = mutableSetOf(Role.USER),
            shoppingCart = ShoppingCart()
        )
        val expected: Mono<User> = userRepository.save(user)
        //When
        val actual: Mono<User> = expected.flatMap {
            userRepository.findById(it.id)
                .switchIfEmpty(Mono.error(NoSuchElementException("can't get user by id ${it.id}")))
        }
        //Then
        StepVerifier.create(actual)
            .expectNext(expected.block()!!)
            .verifyComplete()
    }

    @Test
    fun findUserByEmailFromDbTestOk() {
        //Given
        val user = User(
            email = "bob@gmail.com",
            password = "bob1234",
            roles = mutableSetOf(Role.USER),
            shoppingCart = ShoppingCart()
        )
        val expected: Mono<User> = userRepository.save(user)
        //When
        val actual: Mono<User> = expected.flatMap {
            userRepository.findByEmail(it.email)
                .switchIfEmpty(Mono.error(NoSuchElementException("can't get user by email ${it.email}")))
        }
        //Then
        StepVerifier.create(actual)
            .expectNext(expected.block()!!)
            .verifyComplete()
    }

    @Test
    fun findShoppingCartByUserIdFromDBTestOk() {
        //Given
        val shoppingCart = ShoppingCart(
            mutableListOf(
            Ticket(MovieSession(
                Movie("movie", "awesome"),
                CinemaHall(100, "awesome"),
                LocalDateTime.of(2020, 3, 12, 10 , 5))))
        )
        val user = User(
            email = "bob@gmail.com",
            password = "bob1234",
            roles = mutableSetOf(Role.USER),
            shoppingCart = shoppingCart
        )
        val userMono: Mono<User> = userRepository.save(user)
        //When
        val actual: Mono<ShoppingCart> = userMono.flatMap {
            userRepository.findShoppingCartByUserId(it.id)
                .switchIfEmpty(Mono.error(NoSuchElementException("can't get shopping cart by user id ${it.id}")))
        }
        //Then
        StepVerifier.create(actual)
            .expectNextMatches {
                Assertions.assertTrue(it.tickets[0].movieSession.movie == shoppingCart.tickets[0].movieSession.movie)
                Assertions.assertTrue(it.tickets[0].movieSession.cinemaHall == shoppingCart.tickets[0].movieSession.cinemaHall)
                Assertions.assertTrue(it.tickets[0].movieSession.showTime == shoppingCart.tickets[0].movieSession.showTime)
                true
            }
            .verifyComplete()
    }
}