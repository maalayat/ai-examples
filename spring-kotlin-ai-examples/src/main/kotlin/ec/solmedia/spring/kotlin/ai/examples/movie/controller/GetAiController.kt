package ec.solmedia.spring.kotlin.ai.examples.movie.controller

import ec.solmedia.spring.kotlin.ai.examples.movie.service.MovieService
import ec.solmedia.spring.kotlin.ai.examples.movie.domain.Movie
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ai")
class GetAiController(private val movieService: MovieService) {

    @GetMapping("movies")
    fun getMovieInformation(
        @RequestParam(value = "name", defaultValue = "Interstellar")
        movieName: String
    ): Movie {
        return movieService.getMovieInformation(movieName)
    }
}