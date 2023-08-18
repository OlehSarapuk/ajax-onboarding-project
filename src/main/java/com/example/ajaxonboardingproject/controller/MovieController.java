package com.example.ajaxonboardingproject.controller;

import com.example.ajaxonboardingproject.dto.request.MovieRequestDto;
import com.example.ajaxonboardingproject.dto.response.MovieResponseDto;
import com.example.ajaxonboardingproject.model.Movie;
import com.example.ajaxonboardingproject.service.MovieService;
import com.example.ajaxonboardingproject.service.mapper.RequestDtoMapper;
import com.example.ajaxonboardingproject.service.mapper.ResponseDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final RequestDtoMapper<MovieRequestDto, Movie> movieRequestDtoMapper;
    private final ResponseDtoMapper<MovieResponseDto, Movie> movieResponseDtoMapper;

    @PostMapping
    public MovieResponseDto add(@RequestBody @Valid MovieRequestDto requestDto) {
        Movie movie = movieService.add(movieRequestDtoMapper.mapToModel(requestDto));
        return movieResponseDtoMapper.mapToDto(movie);
    }

    @GetMapping
    public List<MovieResponseDto> getAll() {
        return movieService.getAll()
                .stream()
                .map(movieResponseDtoMapper::mapToDto)
                .toList();
    }
}
