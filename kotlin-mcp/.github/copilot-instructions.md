# Copilot Instructions

## Project Overview
This is a Kotlin MCP (Model Context Protocol) server that implements weather data fetching via Open-Meteo APIs. The server runs as a stdio-based service for integration with AI tools.

## Architecture Patterns

### MCP Tool Implementation Pattern
All tools follow this structure in `src/main/kotlin/Mcp.kt`:
```kotlin
server.addTool(
    "tool-name",
    "Description",
    Tool.Input(properties = buildJsonObject { ... }, required = listOf(...))
) { request ->
    // Extract arguments from request.arguments["param"]?.jsonPrimitive?.content
    // Use HttpClient with ContentNegotiation for API calls
    // Return CallToolResult(listOf(TextContent(...)))
}
```

### API Client Pattern
HTTP clients are created per-tool with automatic cleanup:
- Always use `HttpClient { install(ContentNegotiation) { json(...) } }`
- Create extension functions like `HttpClient.getGeocodingData()` for reusable API calls
- Close client in `finally` block to prevent resource leaks

### Error Handling Convention
- Return `CallToolResult(listOf(TextContent("Error message")))` for user-facing errors
- Use Spanish error messages for missing data: "No se encontró información para la ciudad"
- Catch all exceptions and return descriptive error messages

## Key Development Commands

### Build & Run
- `./gradlew shadowJar` - Creates fat JAR at `build/libs/kotlin-mcp-0.1.0.jar`
- `./gradlew run` - Run server directly via Gradle
- `java -jar build/libs/kotlin-mcp-0.1.0.jar` - Run the built JAR

### Testing MCP Server
- `npx @modelcontextprotocol/inspector` - Interactive MCP server testing tool
- Server communicates via stdio transport (stdin/stdout)

## Dependency Management

### Core Dependencies (build.gradle.kts)
- `io.modelcontextprotocol:kotlin-sdk:0.5.0` - MCP protocol implementation
- `io.ktor:ktor-client-*` - HTTP client stack with JSON serialization
- `kotlinx-serialization` plugin required for `@Serializable` models

### Version Variables
Use version variables for consistency:
```kotlin
val mcpVersion = "0.5.0"
val ktorVersion = "3.1.1"
```

## Data Models (Models.kt)
- Use `@Serializable` data classes for API responses
- Include default values for optional fields: `country: String? = null`
- Use `emptyList()` defaults for collections

## Integration Points
- **Geocoding API**: `https://geocoding-api.open-meteo.com/v1/search` - Converts city names to coordinates
- **Weather API**: `https://api.open-meteo.com/v1/forecast` - Fetches weather data using lat/lon
- **MCP Transport**: Stdio-based communication for AI tool integration

## Package Structure
- `ec.solmedia` - Main package namespace
- `src/main/kotlin/Mcp.kt` - Server implementation and tool definitions
- `src/main/kotlin/Models.kt` - Serializable data classes for API responses
