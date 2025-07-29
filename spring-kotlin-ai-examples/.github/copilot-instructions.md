# Copilot Instructions for spring-kotlin-ai-examples

## Project Overview
- This is a Kotlin-based Spring Boot project demonstrating AI integration, especially for movie data extraction and formatting.
- The main entrypoint is a REST API at `/ai/movies?name=...` that returns movie information in a strict JSON format.
- The project leverages Spring AI and Ollama for LLM-based chat/model interactions.

## Architecture & Key Patterns
- **Domain Model:**
  - `Movie` data class (see `src/main/kotlin/ec/solmedia/spring/.../Movie.kt`) defines the expected JSON structure for movie data.
  - Output format is enforced using a `BeanOutputConverter` and a JSON Schema (see README for example).
- **Prompt Engineering:**
  - Prompts are dynamically generated to instruct the LLM to return only RFC8259-compliant JSON, with no markdown or explanations.
  - The prompt template and schema are shown in the README; follow this pattern for new endpoints or data types.
- **Service Boundaries:**
  - API endpoints are under `/ai/*` and handled by Spring controllers (see `src/main/kotlin/ec/solmedia/spring/`).
  - LLM integration is via Spring AI's `OllamaChatModel` and related advisors.

## Developer Workflows
- **Build:** `./gradlew build`
- **Run:**
  - Start dependencies: `docker-compose -f compose.yaml up`
  - Run app: `./gradlew bootRun`
- **Test:** `./gradlew test`
- **API Test:** `curl http://localhost:8080/ai/movies?name=Interstellar`

## Conventions & Integration
- **Strict JSON Output:** All LLM responses must match the JSON schema for the domain model. No markdown, explanations, or extra fields.
- **Prompt Templates:** Use the `outputConverter.format` to generate the required format for prompts.
- **External Dependencies:**
  - Docker Compose is required for service dependencies (see `compose.yaml`).
  - Spring AI and Ollama are the main AI integration points.
- **File Locations:**
  - Main code: `src/main/kotlin/ec/solmedia/spring/`
  - Resources: `src/main/resources/`
  - Tests: `src/test/kotlin/ec/solmedia/spring/`

## Example Prompt Pattern
```
Generate the information of the movie {movieName}.
{format}
```
Where `{format}` is generated from the output converter and includes the JSON schema.

---
For more details, see the README.md. Update this file if new endpoints, models, or conventions are added.
