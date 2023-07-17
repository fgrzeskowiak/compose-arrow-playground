package com.filippo.either.common.data

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

sealed interface TextResource {
    companion object {
        fun fromText(text: String): TextResource = FromText(text)
        fun fromStringRes(@StringRes resId: Int): TextResource = FromStrings(resId)
        fun fromStringResFormatted(
            @StringRes resId: Int,
            vararg formatArgs: Any,
        ): TextResource = FromStringsFormatted(resId, formatArgs.asList())

        fun fromPluralsRes(
            @PluralsRes resId: Int,
            quantity: Int,
        ): TextResource = FromPlurals(resId, quantity)
    }
}

@JvmInline
value class FromText(val text: String) : TextResource

@JvmInline
value class FromStrings(@StringRes val resId: Int) : TextResource

data class FromStringsFormatted(
    @StringRes val resId: Int,
    val formatArgs: List<Any>
) : TextResource

data class FromPlurals(
    @PluralsRes val resId: Int,
    val quantity: Int,
) : TextResource
