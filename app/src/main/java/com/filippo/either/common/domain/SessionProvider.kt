package com.filippo.either.common.domain

import kotlinx.coroutines.flow.Flow

interface SessionProvider {
    val sessionId: Flow<String?>
}
