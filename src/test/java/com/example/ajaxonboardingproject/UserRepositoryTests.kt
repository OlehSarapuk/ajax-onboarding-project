package com.example.ajaxonboardingproject

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.model.Movie
import com.example.ajaxonboardingproject.model.MovieSession
import com.example.ajaxonboardingproject.model.Role
import com.example.ajaxonboardingproject.model.ShoppingCart
import com.example.ajaxonboardingproject.model.Ticket
import com.example.ajaxonboardingproject.model.User
import com.example.ajaxonboardingproject.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@SpringBootTest
class UserRepositoryTests {
    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun clearCollection() {
        userRepository.deleteAll().block()
    }

    @Test
    fun saveUserToDbTestOk() {
        //Given
        val expected = User(
            email = "bob@gmail.com",
            password = "bob1234",
            roles = mutableSetOf(Role.USER),
            shoppingCart = ShoppingCart()
        )
        //When
        val actual: Mono<User> = userRepository.save(expected)
        //Then
        StepVerifier.create(actual)
            .assertNext {
                assertThat(expected.email).isEqualTo(it.email)
                assertThat(expected.password).isEqualTo(it.password)
                assertThat(expected.roles).isEqualTo(it.roles)
                assertThat(expected.shoppingCart).isEqualTo(it.shoppingCart)
            }
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
        val expected: User = userRepository.save(user).block()!!
        //When
        val actual: Mono<User> = userRepository.findById(expected.id)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
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
        val expected: User = userRepository.save(user).block()!!
        //When
        val actual: Mono<User> = userRepository.findByEmail(expected.email)
        //Then
        StepVerifier.create(actual)
            .expectNext(expected)
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
        val userMono: User = userRepository.save(user).block()!!
        //When
        val actual: Mono<ShoppingCart> = userRepository.findShoppingCartByUserId(userMono.id)
        //Then
        StepVerifier.create(actual)
            .assertNext {
                assertThat(it.tickets[0].movieSession.movie)
                    .isEqualTo(shoppingCart.tickets[0].movieSession.movie)
                assertThat(it.tickets[0].movieSession.cinemaHall)
                    .isEqualTo(shoppingCart.tickets[0].movieSession.cinemaHall)
                assertThat(it.tickets[0].movieSession.showTime)
                    .isEqualTo(shoppingCart.tickets[0].movieSession.showTime)
            }
            .verifyComplete()
    }
}
