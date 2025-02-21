package ec.solmedia.spring.kotlin.ai.examples.config

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatModelConfig(private val builder: ChatClient.Builder) {

    @Bean
    fun chatClient() = builder
        .defaultAdvisors(SimpleLoggerAdvisor())
        .build()
}