# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Development Commands

### Build and Testing
- `./gradlew build` - Builds the project and runs tests
- `./gradlew test` - Runs unit tests using JUnit Platform
- `./gradlew shadowJar` - Creates a fat JAR with all dependencies at `build/libs/kotlin-mcp-0.1.0.jar`

### Running the MCP Server
- `java -jar build/libs/kotlin-mcp-0.1.0.jar` - Run the built JAR
- `./gradlew run` - Run directly via Gradle
- `npx @modelcontextprotocol/inspector` - Test the MCP server interactively

## Architecture Overview

This is a Kotlin MCP (Model Context Protocol) server that implements a weather data fetching tool using real APIs.

### Core Components

**Main Server (`Mcp.kt:39-108`)**
- Uses the MCP Kotlin SDK to create a stdio-based server
- Implements server capabilities with tools support
- Handles connection lifecycle and cleanup

**Weather Tool Implementation (`Mcp.kt:46-93`)**
- `fetch-weather` tool that accepts a city name parameter
- Two-step process: geocoding via Open-Meteo API, then weather data fetch
- Uses Ktor HTTP client with JSON content negotiation
- Returns raw weather JSON response or error messages

**Data Models (`Models.kt`)**
- `GeocodingResult` and `LocationResult` for parsing geocoding API responses
- Uses Kotlinx Serialization with `@Serializable` annotations

### Key Dependencies
- `io.modelcontextprotocol:kotlin-sdk:0.5.0` - MCP protocol implementation
- `io.ktor:ktor-client-*` - HTTP client for API calls
- `kotlinx-serialization-json` - JSON serialization

### API Integration
- **Geocoding**: `https://geocoding-api.open-meteo.com/v1/search` - Converts city names to coordinates
- **Weather**: `https://api.open-meteo.com/v1/forecast` - Fetches current weather data

The server communicates via stdio transport, making it suitable for integration with MCP-compatible tools and AI systems.