# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview
This is a Kotlin-based Spring Boot project demonstrating AI integration using Spring AI with Ollama. The primary functionality is a movie information API that uses LLMs to generate structured JSON responses and a vector store for document search capabilities.

## Development Commands

### Build and Run
- **Build:** `./gradlew build`
- **Run application:** `./gradlew bootRun`
- **Run tests:** `./gradlew test`
- **Start dependencies:** `docker-compose -f compose.yaml up`

### Testing the API
- **Movie API:** `curl http://localhost:8080/ai/movies?name=Interstellar`
- The API defaults to "Interstellar" if no name parameter is provided

## Architecture

### Core Components
- **Main Application:** `SpringKotlinAiExamplesApplication.kt` - Spring Boot application entry point
- **Movie Controller:** `GetAiController.kt` - REST endpoints under `/ai/*`
- **Movie Service:** `MovieService.kt` - Business logic with two LLM integration approaches:
  - `getMovieInformationWithEntity()` - Uses Spring AI's entity extraction
  - `getMovieInformationWithStructuredOutputConverter()` - Uses BeanOutputConverter with JSON schema
- **Movie Domain:** `Movie.kt` - Data classes with Jackson annotations for JSON mapping

### Configuration
- **ChatModelConfig:** Configures ChatClient with SimpleLoggerAdvisor for debugging
- **VectorStoreConfig:** Loads Ecuador.pdf into PGVector for similarity search on startup
- **Dependencies:** Ollama (port 11434) and PGVector (PostgreSQL with vector extension)

### AI Integration Patterns
- **Structured Output:** All LLM responses must be RFC8259-compliant JSON matching the Movie schema
- **Prompt Templates:** Use parameterized templates with `{movieName}` and `{format}` placeholders
- **Output Conversion:** BeanOutputConverter generates JSON schema from Kotlin data classes
- **Vector Store:** PGVector with COSINE_DISTANCE for document similarity search

## Key Configuration Files
- **application.properties:** Ollama model configuration (gemma3:latest), logging levels, PGVector settings
- **compose.yaml:** Docker services for Ollama and PGVector with required models
- **build.gradle.kts:** Spring AI dependencies, Kotlin compiler options, Java 21 toolchain

## Testing
- **Framework:** JUnit 5 with Testcontainers
- **Vector Store Tests:** `VectorStoreTest.kt` validates similarity search with Ecuador.pdf content
- **Test Configuration:** `TestcontainersConfiguration.kt` for containerized testing

## Important Constraints
- LLM responses must contain no markdown, explanations, or extra fields
- All JSON output must strictly adhere to the Movie data class schema
- Temperature is set to 0.4 for consistent responses
- Vector store table is recreated on each startup (remove-existing-vector-store-table=true)