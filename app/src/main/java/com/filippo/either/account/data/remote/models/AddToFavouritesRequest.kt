package com.filippo.feature.account.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddToFavouritesRequest(
    @SerialName("media_type") val mediaType: MediaType,
    @SerialName("media_id") val mediaId: Int,
    val favorite: Boolean
) {
    @Serializable
    enum class MediaType {
        @SerialName("movie")
        MOVIE,
        @SerialName("tv")
        TV
    }
}
