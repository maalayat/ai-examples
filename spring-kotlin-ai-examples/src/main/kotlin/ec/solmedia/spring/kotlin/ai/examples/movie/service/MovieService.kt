package ec.solmedia.spring.kotlin.ai.examples.movie.service

import ec.solmedia.spring.kotlin.ai.examples.movie.domain.Movie
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.ai.chat.prompt.PromptTemplate
import org.springframework.ai.converter.BeanOutputConverter
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service

@Service
class MovieService(private val chatClient: ChatClient) {

    fun getMovieInformation(movieName: String): Movie {
        val outputConverter =
            BeanOutputConverter(object : ParameterizedTypeReference<Movie>() {})
        val format = outputConverter.format
        val template = """
        Generate the information of the movie {movieName}.
        {format}        
        """.trimIndent()
        val model = mapOf("movieName" to movieName, "format" to format)
        val promptTemplate = PromptTemplate(template, model)
        val prompt = Prompt(promptTemplate.createMessage())
        val response = chatClient.prompt(prompt)
            .call()
            .chatResponse()
        val content = response?.result?.output?.text

        return content?.let { outputConverter.convert(it) } ?: Movie()
    }
}