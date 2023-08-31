package com.example.ajaxonboardingproject.service.impl

import com.example.ajaxonboardingproject.model.CinemaHall
import com.example.ajaxonboardingproject.repository.CinemaHallRepository
import com.example.ajaxonboardingproject.service.CinemaHallService
import org.springframework.stereotype.Service

@Service
class CinemaHallServiceImpl(private val cinemaHallRepository: CinemaHallRepository) : CinemaHallService {
    override fun add(cinemaHall: CinemaHall): CinemaHall {
        return cinemaHallRepository.save(cinemaHall)
    }

    override fun get(id: String): CinemaHall {
        return cinemaHallRepository.findById(id).orElseThrow {
            NoSuchElementException("Can't get cinema hall by id $id")
        }
    }

    override fun getAll(): List<CinemaHall> {
        return cinemaHallRepository.findAll()
    }
}
