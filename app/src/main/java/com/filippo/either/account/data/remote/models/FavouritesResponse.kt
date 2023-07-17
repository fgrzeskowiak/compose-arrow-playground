package com.filippo.feature.account.data.remote.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FavouritesResponse(
    @SerialName("results") val movies: List<FavouriteItem>
) {
    @Serializable
    data class FavouriteItem(val id: Int)
}
