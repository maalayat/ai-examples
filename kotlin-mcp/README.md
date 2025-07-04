# kotlin-mcp

A simple Kotlin server example using the Model Context Protocol (MCP) SDK.

## Features

- Implements a basic MCP server with a custom tool (`fetch-weather`)
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
The server exposes a tool called `fetch-weather` that returns a mock weather response for a given city.
