# kotlin-mcp

A simple Kotlin server example using the Model Context Protocol (MCP) SDK.

## Features

- Implements a basic MCP server with a custom tool (`fetch-weather`)
- Fetches real weather data for a given city using the Open-Meteo API
- Uses geocoding to resolve city names to coordinates
- Uses [io.modelcontextprotocol:kotlin-sdk](https://github.com/modelcontextprotocol/kotlin-sdk)
- Built with Gradle and Kotlin JVM

## Getting Started

### Prerequisites

- JDK 21 or higher
- [Gradle](https://gradle.org/) (or use the provided Gradle Wrapper)

### Clone the repository

```sh
git clone git@github.com:maalayat/ai-examples.git
cd ai-examples/kotlin-mcp
```
### Build the project

```sh
./gradlew build
```
### Generate the JAR file

```sh  
./gradlew shadowJar
```

### Test the MCP server
```sh
npx @modelcontextprotocol/inspector
```

## Example
The server exposes a tool called `fetch-weather` that returns the current weather for a given city using real data from Open-Meteo.
