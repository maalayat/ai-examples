 
# Spring AI examples with Kotlin
---

## Run

1. You need to have docker and docker-compose installed.
2. `docker-compose -f compose.yaml up`
2. `./gradlew bootRun`
3. `curl http://localhost:8080/ai/movies\?name\=Interstellar`

## Prompt

With this code:

```kotlin
data class Movie(

	@field:JsonProperty("overview")
	val overview: String? = null,

	@field:JsonProperty("original_language")
	val originalLanguage: String? = null,

	@field:JsonProperty("original_title")
	val originalTitle: String? = null,

	@field:JsonProperty("title")
	val title: String? = null,

    //other fields
)

// build your movie converter
val outputConverter = BeanOutputConverter(object : ParameterizedTypeReference<Movie>() {})

// get format from converter
val format = outputConverter.format

// generate template using format specification
val template = """
        Generate the information of the movie {movieName}.
        {format}        
        """.trimIndent()

```

You can generate this prompt:
```
request: AdvisedRequest[chatModel=org.springframework.ai.ollama.OllamaChatModel@5e8b3ec7, userText=
Generate the information of the movie Interstellar.
Your response should be in JSON format.
Do not include any explanations, only provide a RFC8259 compliant JSON response following this format without deviation.
Do not include markdown code blocks in your response.
Remove the ```json markdown from the output.
Here is the JSON Schema instance your output must adhere to:
```{
  "$schema" : "https://json-schema.org/draft/2020-12/schema",
  "type" : "object",
  "properties" : {
    "adult" : {
      "type" : "boolean"
    },
    "budget" : {
      "type" : "integer"
    },
    "homepage" : {
      "type" : "string"
    },
    "imdb_id" : {
      "type" : "string"
    },
    "origin_country" : {
      "type" : "array",
      "items" : {
        "type" : "string"
      }
    },
    "original_language" : {
      "type" : "string"
    },
    "original_title" : {
      "type" : "string"
    },
    "overview" : {
      "type" : "string"
    },
    "production_countries" : {
      "type" : "array",
      "items" : {
        "type" : "object",
        "properties" : {
          "iso_3166_1" : {
            "type" : "string"
          },
          "name" : {
            "type" : "string"
          }
        },
        "additionalProperties" : false
      }
    },
    "release_date" : {
      "type" : "string"
    },
    "status" : {
      "type" : "string"
    },
    "title" : {
      "type" : "string"
    }
  },
  "additionalProperties" : false
}```
        , systemText=null, chatOptions=org.springframework.ai.ollama.api.OllamaOptions@825bfe42, media=[], 
        functionNames=[], functionCallbacks=[], messages=[], userParams={}, systemParams={}, 
        advisors=[org.springframework.ai.chat.client.DefaultChatClient$DefaultChatClientRequestSpec$1@5c9858a1, 
                  org.springframework.ai.chat.client.DefaultChatClient$DefaultChatClientRequestSpec$2@29c20446, 
                  SimpleLoggerAdvisor, 
                  org.springframework.ai.chat.client.DefaultChatClient$DefaultChatClientRequestSpec$1@305638d3, 
                  org.springframework.ai.chat.client.DefaultChatClient$DefaultChatClientRequestSpec$2@37ac4518], 
        advisorParams={}, adviseContext={}, toolContext={}]

```

## Result

```json
{
  "overview": "When Earth becomes uninhabitable in the future, a former NASA pilot and a group of intrepid explorers are tasked with finding a new home for mankind.",
  "original_language": "en",
  "original_title": "Interstellar",
  "imdb_id": "tt2458992",
  "title": "Interstellar",
  "origin_country": [
    "US"
  ],
  "release_date": "2014-11-05",
  "production_countries": [
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "adult": false,
  "budget": 1650000000,
  "homepage": "http://movies.disney.com/interstellar",
  "status": "Released"
}
```