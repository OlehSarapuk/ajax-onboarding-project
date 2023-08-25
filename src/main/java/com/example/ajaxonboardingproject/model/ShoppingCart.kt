package com.example.ajaxonboardingproject.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document("shopping_carts")
data class ShoppingCart(
    var tickets: MutableList<Ticket>,
) {
    @Id
    lateinit var id: String
}
