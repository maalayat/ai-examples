package ec.solmedia

import io.modelcontextprotocol.kotlin.sdk.*
import io.modelcontextprotocol.kotlin.sdk.server.Server
import io.modelcontextprotocol.kotlin.sdk.server.ServerOptions
import io.modelcontextprotocol.kotlin.sdk.server.StdioServerTransport
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.io.asSink
import kotlinx.io.asSource
import kotlinx.io.buffered
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject

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
        val city =
            request.arguments["city"] ?: return@addTool CallToolResult(listOf(TextContent("City argument is required")))
        CallToolResult(listOf(TextContent("Weather in $city is sunny with a temperature of 25°C.")))
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