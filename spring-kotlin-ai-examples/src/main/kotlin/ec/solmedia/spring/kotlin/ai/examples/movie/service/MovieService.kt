package ec.solmedia.spring.kotlin.ai.examples.movie.service

import ec.solmedia.spring.kotlin.ai.examples.movie.domain.Movie
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.entity
import org.springframework.ai.converter.BeanOutputConverter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class MovieService(private val chatClient: ChatClient) {

    fun getMovieInformationWithEntity(movieName: String): Movie {
        val template = """
        Generate the information of the movie {movieName}.     
        """.trimIndent()

        return chatClient.prompt()
            .user { it.text(template).param("movieName", movieName) }
            .call()
            .entity<Movie>()
    }

    fun getMovieInformationWithStructuredOutputConverter(movieName: String): Movie {
        val converter = BeanOutputConverter(object : ParameterizedTypeReference<Movie>() {})
        val template = """
        Generate the information of the movie {movieName}.
        {format}        
        """.trimIndent()
        val params = mapOf("movieName" to movieName, "format" to converter.format)

        val response = chatClient.prompt()
            .user { it.text(template).params(params) }
            .call()
            .chatResponse()
        val content = response?.result?.output?.text

        return content?.let { converter.convert(it) } ?: Movie()
    }
}