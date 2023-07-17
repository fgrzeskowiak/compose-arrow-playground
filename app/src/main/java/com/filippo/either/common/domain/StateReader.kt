package com.filippo.either.common.domain

import kotlinx.coroutines.flow.Flow

interface StateReader<Type> {
    val state: Flow<Type>
}
