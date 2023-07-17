package com.filippo.either.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.filippo.either.common.data.FromPlurals
import com.filippo.either.common.data.FromStrings
import com.filippo.either.common.data.FromStringsFormatted
import com.filippo.either.common.data.FromText
import com.filippo.either.common.data.TextResource

@Composable
@Suppress("SpreadOperator")
fun TextResource.asString(): String = when (this) {
    is FromText -> text
    is FromStrings -> stringResource(resId)
    is FromStringsFormatted -> stringResource(resId, *formatArgs.toTypedArray())
    is FromPlurals -> pluralStringResource(resId, quantity, quantity)
}
