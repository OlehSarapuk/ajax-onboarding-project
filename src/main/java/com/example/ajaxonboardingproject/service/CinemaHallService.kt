package com.example.ajaxonboardingproject.service

import com.example.ajaxonboardingproject.model.CinemaHall

interface CinemaHallService {
    fun add(cinemaHall : CinemaHall) : CinemaHall

    fun get(id : Long) : CinemaHall

    fun getAll() : List<CinemaHall>
}
