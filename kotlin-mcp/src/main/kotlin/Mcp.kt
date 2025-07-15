package ec.solmedia

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import io.modelcontextprotocol.kotlin.sdk.*
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered
import kotlinx.serialization.json.*

suspend fun HttpClient.getGeocodingData(city: String): GeocodingResult {
    return get("https://geocoding-api.open-meteo.com/v1/search") {
        parameter("name", city)
        parameter("count", 10)
        parameter("language", "en")
        parameter("format", "json")
    }.body<GeocodingResult>()
}

suspend fun HttpClient.getWeatherData(latitude: Double, longitude: Double): String {
    return get("https://api.open-meteo.com/v1/forecast") {
        parameter("latitude", latitude)
        parameter("longitude", longitude)
        parameter("hourly", "temperature_2m")
        parameter("current", "temperature_2m,precipitation,rain,is_day,snowfall")
    }.bodyAsText()
}

fun main() {
    val server = Server(
        Implementation("Demo", "1.0"),
        ServerOptions(
            ServerCapabilities(tools = ServerCapabilities.Tools(true))
        )
    )

    server.addTool(
        "fetch-weather",
        "Fetches the current weather for a given location",
        Tool.Input(
            properties = buildJsonObject {
                putJsonObject("city") {
                    put("type", "string")
                    put("description", "The name of the city to fetch the weather for")
                }
            },
            required = listOf("city")
        )
    ) { request ->
        val city = request.arguments["city"]?.jsonPrimitive?.content
            ?: return@addTool CallToolResult(listOf(TextContent("City argument is required")))

        val httpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }

        try {
            val geocodingData = httpClient.getGeocodingData(city)
            if (geocodingData.results.isEmpty()) {
                return@addTool CallToolResult(listOf(TextContent("No se encontró información para la ciudad: $city")))
            }

            val location = geocodingData.results[0]
            val latitude = location.latitude
            val longitude = location.longitude
            val weatherResponse = httpClient.getWeatherData(latitude, longitude)

            CallToolResult(listOf(TextContent(weatherResponse)))

        } catch (e: Exception) {
            CallToolResult(listOf(TextContent("Error fetching weather data: ${e.message}")))
        } finally {
            try {
                httpClient.close()
            } catch (e: Exception) {
                CallToolResult(listOf(TextContent("Error fetching weather data: ${e.message}")))
            }
        }
    }

    val transport = StdioServerTransport(
        System.`in`.asSource().buffered(),
        System.out.asSink().buffered()
    )

    runBlocking {
        server.connect(transport)
        val done = Job()
        server.onClose {
            done.complete()
        }
        done.join()
    }
}