package ec.solmedia.spring.kotlin.ai.examples.movie.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Movie(

	@field:JsonProperty("overview")
	val overview: String? = null,

	@field:JsonProperty("original_language")
	val originalLanguage: String? = null,

	@field:JsonProperty("original_title")
	val originalTitle: String? = null,

	@field:JsonProperty("imdb_id")
	val imdbId: String? = null,

	@field:JsonProperty("title")
	val title: String? = null,

	@field:JsonProperty("origin_country")
	val originCountry: List<String?>? = null,

	@field:JsonProperty("release_date")
	val releaseDate: String? = null,

	@field:JsonProperty("production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

	@field:JsonProperty("adult")
	val adult: Boolean? = null,

	@field:JsonProperty("budget")
	val budget: Int? = null,

	@field:JsonProperty("homepage")
	val homepage: String? = null,

	@field:JsonProperty("status")
	val status: String? = null
)

data class ProductionCountriesItem(

	@field:JsonProperty("iso_3166_1")
	val iso31661: String? = null,

	@field:JsonProperty("name")
	val name: String? = null
)
