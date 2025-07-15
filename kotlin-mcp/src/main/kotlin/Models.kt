package ec.solmedia

import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResult(
    val results: List<LocationResult> = emptyList(),
)

@Serializable
data class LocationResult(
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val country: String? = null,
)